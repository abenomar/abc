package tests;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;
import AST.ClassDecl;
import AST.MethodDecl;
import AST.Program;
import AST.RawCU;
import AST.RefactoringException;
import AST.SimpleSet;
import AST.TypeDecl;

public class ExtractInterfaceTests extends TestCase {
	private void testSucc(String className, String[] signatures, String pkg, String iface, Program in, Program out) {
		assertNotNull(in);
		assertNotNull(out);
		
		TypeDecl td = in.findType(className);
		assertTrue(td instanceof ClassDecl);
		
		Collection<MethodDecl> mds = new ArrayList<MethodDecl>();
		for(String sig : signatures) {
			SimpleSet s = td.localMethodsSignature(sig);
			assertTrue(s instanceof MethodDecl);
			mds.add((MethodDecl)s);
		}
		
		assertNotNull(iface);
		
		try {
			((ClassDecl)td).doExtractInterface(pkg, iface, mds);
			assertEquals(out.toString(), in.toString());
		} catch(RefactoringException rfe) {
			assertEquals(out.toString(), rfe.getMessage());
		}
	}

	private void testFail(String className, String[] signatures, String pkg, String iface, Program in) {
		assertNotNull(in);
		
		TypeDecl td = in.findType(className);
		assertTrue(td instanceof ClassDecl);
		
		Collection<MethodDecl> mds = new ArrayList<MethodDecl>();
		for(String sig : signatures) {
			SimpleSet s = td.localMethodsSignature(sig);
			assertTrue(s instanceof MethodDecl);
			mds.add((MethodDecl)s);
		}
		
		assertNotNull(iface);
		
		try {
			((ClassDecl)td).doExtractInterface(pkg, iface, mds);
			assertEquals("<failure>", in.toString());
		} catch(RefactoringException rfe) {
		}
	}

	public void test1() {
		testSucc("X.C", new String[]{"foo(X.B)"}, null, "I",
				Program.fromClasses(
				"class X {" +
				"  private class B { }" +
				"  static class C {" +
				"    public void foo(B b){}" +
				"    public void bar(){}" +
				"  }" +
				"}",
				"interface Y {" +
				"  public class B { }" +
				"}",
				"class Z extends X implements Y {" +
				"  Z(X.C p, X.C q){ q.bar(); }" +
				"  Z(X.C r, Object s){" +
				"    r.bar();" +
				"    Z z = new Z(r,r);" +
				"  }" +
				"  B f;" +
				"}"),
				Program.fromClasses(
				"interface I {" +
				"  abstract public void foo(X.B b);" +
				"}",
				"class X {" +
				"  class B { }" +
				"  static class C implements I {" +
				"    public void foo(B b){}" +
				"    public void bar(){}" +
				"  }" +
				"}",
				"interface Y {" +
				"  public class B { }" +
				"}",
				"class Z extends X implements Y {" +
				"  Z(I p, X.C q){ q.bar(); }" +
				"  Z(X.C r, Object s){" +
				"    r.bar();" +
				"    Z z = new Z((I)r, (C)r);" +
				"  }" +
				"  Y.B f;" +
				"}"));
	}
	
	public void test2() {
		testSucc("p.X.C", new String[]{"foo(p.X.B)"}, "q", "I",
				Program.fromCompilationUnits(
				new RawCU("X.java",
				"package p;" +
				"" +
				"class X {" +
				"  private class B { }" +
				"  static class C {" +
				"    public void foo(B b){}" +
				"    public void bar(){}" +
				"  }" +
				"}" +
				"interface Y {" +
				"  public class B { }" +
				"}" +
				"class Z extends X implements Y {" +
				"  Z(X.C p, X.C q){ q.bar(); }" +
				"  Z(X.C r, Object s){" +
				"    r.bar();" +
				"    Z z = new Z(r,r);" +
				"  }" +
				"  B f;" +
				"}")),
				Program.fromCompilationUnits(
				new RawCU("I.java",
				"package q;" +
				"public interface I {" +
				"  abstract public void foo(p.X.B b);" +
				"}"),
				new RawCU("X.java",
				"package p;" +
				"public class X {" +
				"  public class B { }" +
				"  static class C implements q.I {" +
				"    public void foo(B b){}" +
				"    public void bar(){}" +
				"  }" +
				"}" +
				"interface Y {" +
				"  public class B { }" +
				"}" +
				"class Z extends X implements Y {" +
				"  Z(q.I p, X.C q){ q.bar(); }" +
				"  Z(X.C r, Object s){" +
				"    r.bar();" +
				"    Z z = new Z((q.I)r, (C)r);" +
				"  }" +
				"  Y.B f;" +
				"}")));
	}
	
	public void test3() {
		testFail("A", new String[]{"m()"}, "p", "I", Program.fromCompilationUnits(
				new RawCU("A.java",
						  "package p;" +
						  "class A {" +
						  "  public static void m() { }" +
						  "}")));
	}
	
	/* rtt test 2010_10_19 12_36: methods:firePropertyChange, firePropertyChange, addPropertyChangeListener, removePropertyChangeListener, addPropertyChangeListener, firePropertyChange, hasListeners, removePropertyChangeListener, firePropertyChange, , interface package:RTT_NEW_PACKAGE, class:org.w3c.tools.jdbc.JdbcPropertyChangeSupport, interface name:RTT_NEW_INTERFACE, */
	public void test4() {
		testSucc("A", new String[]{"m()"}, "", "I", 
			Program.fromCompilationUnits(
					new RawCU("A.java", "class A { public synchronized void m() {} } "),
					new RawCU("B.java", "class B { A a; void n(){a.m();}}")),
			Program.fromCompilationUnits(
					new RawCU("A.java","class A implements I { public synchronized void m() {} } "),
					new RawCU("B.java","class B { I a; void n(){a.m();}}"),
					new RawCU("I.java","interface I {abstract public void m();}")));
	}
	
	public void test5() {
		testSucc("A", new String[]{}, "p", "I", 
			Program.fromCompilationUnits(
				new RawCU("A.java",
						  "package p;" +
						  "class A {" +
						  "  static A m() {return null;}" +
						  "  void n() {new A().m().n();}" +
						  "}"),
				new RawCU("B.java",
						  "package p;" +
						  "class B extends A {" +
						  "   static A m() {return null;}" + 
						  "}")),
			Program.fromCompilationUnits(
				new RawCU("A.java",
						  "package p;" +
						  "class A implements I {" +
						  "  static A m() {return null;}" +
						  "  void n() {new A().m().n();}" +
						  "}"),
				new RawCU("B.java",
						  "package p;" +
						  "class B extends A {" +
						  "   static A m() {return null;}" + 
						  "}"),
				new RawCU("I.java",
						  "package p;"+
						  "interface I {}")));
    }
}
