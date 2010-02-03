package tests.eclipse.ExtractTemp;

import junit.framework.TestCase;
import tests.CompileHelper;
import AST.ASTNode;
import AST.AbstractDot;
import AST.Access;
import AST.CompilationUnit;
import AST.Expr;
import AST.Program;
import AST.RefactoringException;

public class ExtractTempTests extends TestCase {

	private static final Class clazz= ExtractTempTests.class;
	private static final String TEST_PATH_PREFIX = "tests/eclipse/ExtractTemp/";

	public ExtractTempTests(String name) {
		super(name);
	}

	private boolean old_printCUNames;
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		old_printCUNames = CompilationUnit.printCUNames;
		CompilationUnit.printCUNames = false;
	}
	@Override
	protected void tearDown() throws Exception {
		CompilationUnit.printCUNames = old_printCUNames;
		super.tearDown();
	}

	private String getSimpleTestFileName(boolean canExtract, boolean input){
		String fileName = "A_" + getName();
		if (canExtract)
			fileName += input ? "_in": "_out";
		return fileName + ".java";
	}

	private String getTestFileName(boolean canExtract, boolean input){
		String fileName= TEST_PATH_PREFIX;
		fileName += canExtract ? "canExtract/": "cannotExtract/";
		return fileName + getSimpleTestFileName(canExtract, input);
	}

	private Expr findExpr(ASTNode p, int startLine, int startColumn, int endLine, int endColumn) {
		if(p == null)
			return null;
		if(p instanceof Expr) {
			int start = p.getStart(), end = p.getEnd();
			if(startLine == ASTNode.getLine(start) &&
					startColumn == ASTNode.getColumn(start) &&
					endLine == ASTNode.getLine(end) &&
					endColumn == ASTNode.getColumn(end)+1)
				return (Expr)p;
		}
		for(int i=0;i<p.getNumChild();++i) {
			Expr res = findExpr(p.getChild(i), startLine, startColumn, endLine, endColumn);
			if(res != null)
				return res;
		}
		if(p instanceof AbstractDot) {
			AbstractDot pdot = (AbstractDot)p;
			if(pdot.getRight() instanceof AbstractDot) {
				Expr l = pdot.getLeft();
				AbstractDot r = (AbstractDot)pdot.getRight();
				Access rl = (Access)r.getLeft(), rr = r.getRight();
				AbstractDot l2 = new AbstractDot(l, rl);
				l2.setStart(l.getStart());
				l2.setEnd(rl.getEnd());
				pdot.setLeft(l2);
				pdot.setRight(rr);
				Expr res = findExpr(p, startLine, startColumn, endLine, endColumn);
				if(res != null)
					return res;
				pdot.setLeft(l);
				r.setLeft(rl);
				r.setRight(rr);
				pdot.setRight(r);
			}
		}
		return null;
	}

	private void helper1(int startLine, int startColumn, int endLine, int endColumn, boolean replaceAll, boolean makeFinal, String tempName, String guessedTempName) {
		// this is how Eclipse wants it...
		if(startLine == endLine && startColumn == endColumn)
			return;

		Program in = CompileHelper.compile(getTestFileName(true, true));
		assertNotNull("invalid program", in);

		Expr e = findExpr(in, startLine, startColumn, endLine, endColumn);
		assertNotNull("expression not found", e);

		try {
			e.doExtract(tempName, makeFinal);
		} catch(RefactoringException rfe) {
			fail(rfe.getMessage());
		}

		Program out = CompileHelper.compile(getTestFileName(true, false));
		assertNotNull(out);

		assertEquals(out.toString(), in.toString());
	}

	private void warningHelper1(int startLine, int startColumn, int endLine, int endColumn, boolean replaceAll, boolean makeFinal, String tempName, String guessedTempName) {
		helper1(startLine, startColumn, endLine, endColumn, replaceAll, makeFinal, tempName, guessedTempName);
	}

	private void failHelper1(int startLine, int startColumn, int endLine, int endColumn, boolean replaceAll, boolean makeFinal, String tempName) {
		Program in = CompileHelper.compile(getTestFileName(false, true));
		assertNotNull("invalid program", in);

		Expr e = findExpr(in, startLine, startColumn, endLine, endColumn);
		assertNotNull("expression not found", e);

		try {
			e.doExtract(tempName, makeFinal);
			assertEquals("<failure>", in.toString());
		} catch(RefactoringException rfe) { }
	}

	//--- TESTS

	public void test0() throws Exception{
		helper1(4, 16, 4, 17, false, false, "temp", "j");
	}

	public void test1() throws Exception{
		helper1(4, 16, 4, 17, true, false, "temp", "j");
	}

	public void test2() throws Exception{
		helper1(4, 16, 4, 17, true, true, "temp", "j");
	}

	public void test3() throws Exception{
		helper1(4, 16, 4, 17, false, true, "temp", "j");
	}

	public void test4() throws Exception{
		helper1(4, 16, 4, 21, false, false, "temp", "j");
	}

	public void test5() throws Exception{
		helper1(4, 16, 4, 21, true, false, "temp", "j");
	}

	public void test6() throws Exception{
		helper1(4, 16, 4, 21, true, true, "temp", "j");
	}

	public void test7() throws Exception{
		helper1(4, 16, 4, 21, false, true, "temp", "j");
	}

	public void test8() throws Exception{
		helper1(5, 20, 5, 25, true, false, "temp", "j");
	}

	public void test9() throws Exception{
		helper1(5, 20, 5, 25, false, false, "temp", "j");
	}

	public void test10() throws Exception{
		helper1(5, 20, 5, 25, true, false, "temp", "i");
	}

	public void test11() throws Exception{
		helper1(5, 20, 5, 25, true, false, "temp", "i");
	}

	public void test12() throws Exception{
		helper1(5, 17, 5, 22, true, false, "temp", "i");
	}

	public void test13() throws Exception{
		// disabled: import
		//helper1(7, 16, 7, 42, true, false, "temp", "iterator");
	}

	public void test14() throws Exception{
		helper1(6, 15, 6, 20, false, false, "temp", "y2");
	}

	public void test15() throws Exception{
		helper1(7, 23, 7, 28, false, false, "temp", "y2");
	}

	public void test16() throws Exception{
		helper1(7, 23, 7, 28, false, false, "temp", "y2");
	}

	public void test17() throws Exception{
		helper1(5, 20, 5, 25, true, false, "temp", "j");
	}

	public void test18() throws Exception{
		helper1(6, 20, 6, 25, true, false, "temp", "i");
	}

	public void test19() throws Exception{
		helper1(5, 20, 5, 23, true, false, "temp", "f");
	}


	public void test21() throws Exception{
		helper1(5, 16, 5, 17, false, false, "temp", "f2");
	}


	public void test23() throws Exception{
		helper1(7, 17, 7, 20, false, false, "temp", "b");
	}


	public void test25() throws Exception{
		helper1(4, 17, 4, 22, true, false, "temp", "i");
	}

	public void test26() throws Exception{
		helper1(5, 19, 5, 23, true, false, "temp", "i");
	}

	public void test27() throws Exception{
		helper1(4, 16, 4, 19, true, false, "temp", "j");
	}

	public void test28() throws Exception{
		helper1(4, 16, 4, 31, true, false, "temp", "b");
	}

	public void test29() throws Exception{
		helper1(4, 19, 4, 22, true, false, "temp", "string");
	}

	public void test30() throws Exception{
		helper1(5, 16, 5, 20, true, false, "temp", "ff2");
	}

	public void test31() throws Exception{
		helper1(5, 16, 5, 20, true, false, "temp", "j");
	}

	public void test32() throws Exception{
		// disabled: removing parentheses
		//helper1(4, 16, 4, 22, true, false, "temp", "j");
	}

	public void test33() throws Exception{
		helper1(4, 19, 4, 33, true, false, "temp", "object");
	}

	public void test34() throws Exception{
		helper1(4, 19, 4, 46, true, false, "temp", "arrayList");
	}

	public void test35() throws Exception{
		helper1(8, 19, 8, 28, true, false, "temp", "lists");
	}

	public void test36() throws Exception{
		helper1(11, 15, 11, 25, true, false, "temp", "foo");
	}

	public void test37() throws Exception{
		helper1(8, 20, 8, 25, true, false, "temp", "j");
	}

	public void test38() throws Exception{
		helper1(5, 28, 5, 32, true, false, "temp1", "temp2");
	}

	public void test39() throws Exception{
		// disabled: removing parentheses
		//helper1(4, 14, 4, 26, true, false, "temp", "object");
	}

	public void test40() throws Exception{
		// disabled: lone expression deleted
		//helper1(4, 9, 4, 16, true, false, "temp", "a");
	}

	public void test41() throws Exception{
		helper1(4, 16, 4, 43, true, false, "temp", "length");
	}

	public void test42() throws Exception{
		helper1(5, 16, 5, 35, true, false, "temp", "length");
	}

	public void test43() throws Exception{
		helper1(5, 20, 5, 36, true, false, "temp", "fred");
	}

	public void test44() throws Exception{
		helper1(5, 20, 5, 28, true, false, "temp", "fred");
	}

	public void test45() throws Exception{
		helper1(4, 16, 4, 19, true, false, "temp", "f");
	}

	public void test46() throws Exception{
		helper1(4, 9, 4, 12, true, false, "temp", "f");
	}

	public void test47() throws Exception{
		helper1(5, 9, 5, 12, true, false, "temp", "r");
	}

	public void test48() throws Exception{
		helper1(4, 16, 4, 32, true, false, "temp", "string");
	}

	public void test49() throws Exception{
		helper1(5, 15, 5, 19, true, false, "temp", "flag2");
	}

	public void test50() throws Exception{
		helper1(5, 15, 5, 19, true, false, "temp", "flag2");
	}

	public void test51() throws Exception{
		helper1(5, 15, 5, 18, true, false, "temp", "i");
	}

	public void test52() throws Exception{
		helper1(15, 47, 15, 60, true, false, "valueOnIndexI", "object");
	}

	public void test53() throws Exception{
		helper1(6, 17, 6, 22, true, false, "temp", "i");
	}

	public void test54() throws Exception{
		helper1(6, 37, 6, 43, true, false, "temp", "i");
	}

	public void test55() throws Exception{
		// disabled: associativity
		//helper1(6, 19, 6, 24, true, false, "temp", "i");
	}

	public void test56() throws Exception{
		// disabled: associativity
		//helper1(6, 24, 6, 29, true, false, "temp", "i");
	}

	public void test57() throws Exception{
		helper1(8, 30, 8, 54, true, false, "newVariable", "string");
	}

	public void test58() throws Exception{
		helper1(7, 14, 7, 30, true, false, "temp", "equals");
	}

	public void test59() throws Exception{
		helper1(7, 17, 7, 18, true, false, "temp", "s2");
	}

	public void test60() throws Exception{
		helper1(7, 17, 7, 18, true, false, "temp", "s2");
	}

	public void test61() throws Exception{
		helper1(7, 17, 7, 18, true, false, "temp", "s2");
	}

	public void test62() throws Exception{
		helper1(10, 17, 10, 28, true, false, "temp", "string");
	}

	public void test63() throws Exception{
		helper1(9, 20, 9, 23, true, false, "temp", "string");
	}

	public void test64() throws Exception{
		helper1(10, 17, 10, 28, true, false, "temp", "string");
	}

	public void test65() throws Exception{
		helper1(6, 19, 6, 22, true, false, "temp", "bar2");
	}

	public void test66() throws Exception{
		helper1(7, 32, 7, 33, true, false, "temp", "e2");
	}

	public void test67() throws Exception{
		helper1(6, 16, 6, 21, true, false, "temp", "integer");
	}

	public void test68() throws Exception{
		helper1(6, 14, 6, 21, true, false, "temp", "x");
	}

	public void test69() throws Exception{
		helper1(5, 24, 5, 26, true, false, "temp", "string2");
	}

	public void test70() throws Exception{
		helper1(7, 28, 7, 42, true, true, "temp", "length");
	}

	public void test71() throws Exception{
		helper1(8, 24, 8, 34, true, false, "temp", "string");
	}

	public void test72() throws Exception{
		helper1(8, 32, 8, 33, true, false, "temp", "i2");
	}

	public void test73() throws Exception{
		warningHelper1(6, 39, 6, 40, true, false, "temp", "i2");
	}

	public void test74() throws Exception{
		helper1(7, 36, 7, 49, true, false, "temp", "string");
	}

	public void test75() throws Exception{
		helper1(7, 36, 7, 39, true, false, "temp", "j");
	}

	public void test76() throws Exception{
		helper1(7, 48, 7, 49, true, false, "temp", "k2");
	}

	public void test77() throws Exception {
		helper1(10, 13, 10, 17, true, false, "temp", "f");
	}

	public void test78() throws Exception {
		helper1(5, 21, 5, 27, true, false, "o2", "o");
	}

	public void test79() throws Exception {
		// disabled: associativity
		//helper1(10, 40, 10, 60, true, false, "strong", "string");
	}

	public void test80() throws Exception {
		helper1(5, 37, 5, 45, true, false, "name", "string");
	}

	public void test81() throws Exception {
		helper1(7, 15, 7, 20, true, false, "k", "const2");
	}

	public void test82() throws Exception {
		// disabled: tests weird selection
		//helper1(5, 1, 6, 1, true, false, "one", "integer");
	}

	public void test83() throws Exception{
		helper1(7, 17, 7, 27, false, false, "temp", "test");
	}

	public void test84() throws Exception{
		helper1(5, 16, 5, 17, false, false, "temp", "j");
	}

	public void test85() throws Exception{
		helper1(11, 22, 11, 32, true, true, "temp", "test2");
	}

	public void test86() throws Exception{
		helper1(15, 22, 15, 37, true, true, "name", "a");
	}

	public void test87() throws Exception{
		helper1(16, 17, 16, 27, true, true, "a2", "a2");
	}

	public void test88() throws Exception{
		helper1(14, 14, 14, 19, true, false, "foo", "foo");
	}

	public void test89() throws Exception{
		/*		IPackageFragment a= getRoot().createPackageFragment("a", true,	null);
		ICompilationUnit aA= a.createCompilationUnit("A.java", "package a; public class A {}", true, null);
		aA.save(null, true);

		IPackageFragment b= getRoot().createPackageFragment("b", true,	null);
		ICompilationUnit bA= b.createCompilationUnit("A.java", "package b; public class A {}", true, null);
		bA.save(null, true);

		helper1(15, 7, 15, 15, true, false, "foo", "method");*/
		fail();
	}

	public void test90() throws Exception {
		helper1(8, 19, 8, 28, true, false, "temp", "number");
	}

	public void test91() throws Exception {
		helper1(8, 19, 8, 28, true, false, "temp", "integer");
	}

	public void test92() throws Exception { 		
		helper1(9, 32, 9, 44, true, false, "asList", "asList");
	}

	public void test93() throws Exception { 	
		helper1(6, 28, 6, 34, true, false, "bla", "string");
	}

	public void test94() throws Exception {
		// disabled: lone expression deleted
		//helper1(6, 9, 6, 24, false, false, "temp", "string");
	}

	public void test95() throws Exception {
		helper1(5, 23, 5, 33, true, false, "temp", "b");
	}

	public void test96() throws Exception {
		helper1(6, 32, 6, 37, true, false, "isquared", "j");
	}

	public void test97() throws Exception {
		helper1(10, 32, 10, 47, true, false, "temp", "nextElement");
	}

	public void test98() throws Exception {
		helper1(8, 32, 8, 44, true, true, "temp", "string");
	}

	public void test99() throws Exception {
		helper1(7, 32, 7, 36, true, false, "temp", "a");
	}

	public void test100() throws Exception {
		helper1(5, 28, 5, 40, true, false, "temp", "object");
	}

	public void test101() throws Exception {
		// disabled: tests lone expression
		helper1(9, 13, 9, 24, true, false, "temp", "object");
	}

	public void test102() throws Exception {
		helper1(9, 24, 9, 29, true, false, "temp", "j");
	}

	public void test103() throws Exception {
		helper1(7, 21, 7, 33, true, false, "temp", "valueOf");
	}


	public void testZeroLengthSelection0() throws Exception {
		helper1(4, 18, 4, 18, true, false, "temp", "j");
	}

	public void testFail0() throws Exception{
		failHelper1(5, 16, 5, 17, false, false, "temp");
	}

	public void testFail1() throws Exception{
		// disabled: check for invalid selection
		//failHelper1(4, 9, 5, 13, false, false, "temp");
	}

	public void testFail2() throws Exception{
		// disabled: check for invalid selection
		//failHelper1(4, 9, 4, 20, false, false, "temp");
	}

	public void testFail3() throws Exception{
		// disabled: check for invalid selection
		//failHelper1(4, 9, 4, 20, false, false, "temp");
	}

	public void testFail4() throws Exception{
		failHelper1(5, 9, 5, 12, false, false, "temp");
	}

	public void testFail5() throws Exception{
		failHelper1(3, 12, 3, 15, false, false, "temp");
	}

	public void testFail6() throws Exception{
		failHelper1(4, 14, 4, 19, false, false, "temp");
	}

	public void testFail7() throws Exception{
		failHelper1(4, 15, 4, 20, false, false, "temp");
	}

	public void testFail9() throws Exception{
		failHelper1(4, 19, 4, 23, false, false, "temp");
	}

	public void testFail10() throws Exception{
		failHelper1(4, 33, 4, 39, false, false, "temp");
	}

	public void testFail11() throws Exception{
		failHelper1(4, 18, 4, 19, false, false, "temp");
	}

	public void testFail18() throws Exception{
		failHelper1(4, 27, 4, 28, false, false, "temp");
	}

	public void testFail19() throws Exception{
		// disabled: we can do this
		//failHelper1(6, 16, 6, 18, false, false, "temp");
	}

	public void testFail20() throws Exception{
		// disabled: checks for invalid selection
		//failHelper1(3, 9, 3, 41, false, false, "temp");
	}

	public void testFail22() throws Exception{
		failHelper1(5, 9, 5, 12, false, false, "temp");
	}

	public void testFail23() throws Exception{
		// disabled: checks for invalid selection
		//failHelper1(4, 13, 4, 14, false, false, "temp");
	}

	public void testFail24() throws Exception{
		// disabled: checks for invalid selection
		//failHelper1(4, 13, 4, 14, false, false, "temp");
	}

	public void testFail25() throws Exception{
		// disabled: checks for invalid selection
		//failHelper1(4, 16, 4, 18, false, false, "temp");
	}

	public void testFail26() throws Exception{
		// disabled: checks for invalid selection
		//failHelper1(4, 15, 4, 20, false, false, "temp");
	}

	public void testFail27() throws Exception{
		failHelper1(7, 13, 7, 24, true, false, "temp");
	}

	public void testFail28() throws Exception{
		failHelper1(7, 17, 7, 28, true, false, "temp");
	}

	public void testFail29() throws Exception {
		failHelper1(5, 32, 5, 35, true, false, "temp");
	}

	public void testFail30() throws Exception {
		failHelper1(5, 25, 5, 30, true, false, "temp");
	}

	public void testFail31() throws Exception {
		failHelper1(5, 31, 5, 32, true, false, "temp");
	}

	public void testFail32() throws Exception {
		failHelper1(6, 35, 6, 36, true, false, "temp");
	}

	public void testFail33() throws Exception {
		failHelper1(6, 17, 6, 21, true, false, "temp");
	}

	public void testFail34() throws Exception {
		failHelper1(9, 20, 9, 24, true, false, "temp");
	}

	public void testFail35() throws Exception {
		failHelper1(6, 30, 6, 35, true, false, "temp");
	}

	public void testFail36() throws Exception {
		failHelper1(6, 33, 6, 38, true, false, "temp");
	}

	public void testFail37() throws Exception {
		failHelper1(5, 40, 5, 51, true, false, "temp");
	}

	public void testFail38() throws Exception {
		failHelper1(4, 45, 4, 50, true, false, "temp");
	}
}
