package abc.weaving.aspectinfo;

import java.util.*;
import polyglot.util.Position;
import soot.*;
import soot.jimple.*;
import soot.util.*;
import abc.weaving.matching.ShadowMatch;
import abc.weaving.residues.*;
import abc.weaving.weaver.WeavingContext;
import abc.soot.util.LocalGeneratorEx;

public class PerCflowSetup extends PerSetupAdvice {

    private boolean isBelow;

    public PerCflowSetup(Aspect aspct,Pointcut pc,
			   boolean isBelow,Position pos) {
	super(new BeforeAfterAdvice(pos),aspct,pc,pos);
	this.isBelow=isBelow;
    }

    public boolean isBelow() {
	return isBelow;
    }

    public static class PerCflowSetupWeavingContext 
	extends WeavingContext
	implements BeforeAfterAdvice.ChoosePhase {

	public boolean doBefore;
	public void setBefore() { doBefore=true; }
	public void setAfter() { doBefore=false; }

    }

    public WeavingContext makeWeavingContext() {
	return new PerCflowSetupWeavingContext();
    }



    public Chain makeAdviceExecutionStmts
	 (LocalGeneratorEx localgen,WeavingContext wc) {

	PerCflowSetupWeavingContext cswc=(PerCflowSetupWeavingContext) wc;

	if(cswc.doBefore) {

	    Chain c = new HashChain();
	    Type object=Scene.v().getSootClass("java.lang.Object").getType();
	    
	    SootClass aspectClass=getAspect().getInstanceClass().getSootClass();

	    SootMethod push=aspectClass.getMethod("abc$perCflowPush",new ArrayList());

	    c.addLast(Jimple.v().newInvokeStmt
		      (Jimple.v().newStaticInvokeExpr(push)));

	    return c;
	} else {
	    Chain c=new HashChain();
	    SootClass stackClass=Scene.v()
		.getSootClass("org.aspectj.runtime.internal.CFlowStack");
	    SootClass aspectClass=getAspect().getInstanceClass().getSootClass();

	    SootMethod pop=stackClass.getMethod("pop",new ArrayList());
	    SootField perCflowStackField=aspectClass.getFieldByName("abc$perCflowStack");

	    Local perCflowStackLoc=localgen.generateLocal(stackClass.getType(),"perCflowStack");
	    c.addLast(Jimple.v().newAssignStmt
		      (perCflowStackLoc,Jimple.v().newStaticFieldRef(perCflowStackField)));
	    c.addLast(Jimple.v().newInvokeStmt
		      (Jimple.v().newVirtualInvokeExpr(perCflowStackLoc,pop)));
	    return c;
	}
    }

    public Residue postResidue(ShadowMatch sm) {
	return AlwaysMatch.v();
    }


    public void debugInfo(String prefix,StringBuffer sb) {
	sb.append(prefix+" from aspect: "+getAspect().getName()+"\n");
	sb.append(prefix+" type: "+spec+"\n");
	sb.append(prefix+" pointcut: "+pc+"\n");
	sb.append(prefix+" special: percflow"+(isBelow?"below":"")+" instantiation\n");
    }
}
