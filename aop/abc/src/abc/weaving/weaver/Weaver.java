package abc.weaving.weaver;
import soot.*;
import soot.util.*;
import soot.jimple.*;
import java.util.*;

public class Weaver {
    public void weave() {
        for( Iterator clIt = Scene.v().getApplicationClasses().iterator(); clIt.hasNext(); ) {
            final SootClass cl = (SootClass) clIt.next();
            for( Iterator methodIt = cl.getMethods().iterator(); methodIt.hasNext(); ) {
                final SootMethod method = (SootMethod) methodIt.next();
                if( method.isAbstract() ) continue;
                if( method.isNative() ) continue;
                method.retrieveActiveBody();
            }
        }
        G.v().out.println( "The application classes are (hi):" );

        for( Iterator clIt = Scene.v().getApplicationClasses().iterator(); clIt.hasNext(); ) {

            final SootClass cl = (SootClass) clIt.next();
            G.v().out.println( "hello "+cl.toString() );
            if( isAspect(cl) ) {
                System.out.println( "it's an aspect");
                fillInAspect( cl ); 
            } else {
                System.out.println( "it's not an aspect");
                weaveInAspects( cl );
            }
        }
        G.v().out.println( "finished application classes" );
    }
    public void fillInAspect( SootClass cl ) {
        System.out.println( "filling in aspect "+cl );

        SootField instance = new SootField( "ajc$perSingletonInstance", cl.getType(), Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL );
        cl.addField( instance );

        generateAspectOfBody(cl);
        generateClinitBody(cl);
    }

    private void generateAspectOfBody( SootClass cl ) {
        SootMethod aspectOf = cl.getMethodByName( "aspectOf" );

        Body b = Jimple.v().newBody(aspectOf);
        aspectOf.setActiveBody(b);


        SootClass nabe = Scene.v().getSootClass("org.aspectj.lang.NoAspectBoundException");
        //Local rthis = Jimple.v().newLocal("rthis", cl.getType());
        Local r0 = Jimple.v().newLocal("r0", cl.getType());
        Local r1 = Jimple.v().newLocal("r1", nabe.getType() );

        b.getLocals().add(r0);
        b.getLocals().add(r1);

        StaticFieldRef ref = Jimple.v().newStaticFieldRef(cl.getFieldByName("ajc$perSingletonInstance"));

        Chain units = b.getUnits(); 

        //units.addLast( Jimple.v().newIdentityStmt( rthis, newThisRef(cl)));
        units.addLast( Jimple.v().newAssignStmt( r0, ref));
        Stmt newExceptStmt = Jimple.v().newAssignStmt( r1, Jimple.v().newNewExpr( nabe.getType() ) );
        units.addLast( Jimple.v().newIfStmt( Jimple.v().newEqExpr( r0, NullConstant.v() ), newExceptStmt ));
        units.addLast( Jimple.v().newReturnStmt( r0 ) );
        units.addLast( newExceptStmt );
        units.addLast( Jimple.v().newInvokeStmt( Jimple.v().newSpecialInvokeExpr( r1, nabe.getMethod( "<init>", new ArrayList() ) ) ) ); 
        units.addLast( Jimple.v().newThrowStmt( r1 ) );
    }

    private void generateClinitBody( SootClass cl ) {
        SootMethod postClinit = new SootMethod( "ajc$postClinit", new ArrayList(), VoidType.v(), Modifier.PRIVATE | Modifier.STATIC );
        cl.addMethod( postClinit );
        Body b = Jimple.v().newBody(postClinit);
        postClinit.setActiveBody(b);

        Local r0 = Jimple.v().newLocal("r0", cl.getType());
        b.getLocals().add(r0);

        Chain units = b.getUnits();
        units.addLast( Jimple.v().newAssignStmt( r0, Jimple.v().newNewExpr( cl.getType() ) ) );
        units.addLast( Jimple.v().newInvokeStmt( Jimple.v().newSpecialInvokeExpr( r0, cl.getMethod( "<init>", new ArrayList() ) ) ) );
        StaticFieldRef ref = Jimple.v().newStaticFieldRef(cl.getFieldByName("ajc$perSingletonInstance"));
        units.addLast( Jimple.v().newAssignStmt( ref, r0 ) );
        units.addLast( Jimple.v().newReturnVoidStmt() ); 

        SootMethod clinit;

        if( !cl.declaresMethod( "void <clinit>()" ) ) {
            clinit = new SootMethod( "<clinit>", new ArrayList(), VoidType.v(), Modifier.STATIC );
            cl.addMethod( clinit );
            b = Jimple.v().newBody(clinit);
            clinit.setActiveBody(b);
            b.getUnits().addLast( Jimple.v().newReturnVoidStmt() );
        }
        clinit = cl.getMethod("void <clinit>()");

        units = clinit.getActiveBody().getUnits();
        Iterator it = units.snapshotIterator();
        while( it.hasNext() ) {
            Stmt s = (Stmt) it.next();
            if( s instanceof ReturnVoidStmt ) {
                units.insertBefore( 
                        Jimple.v().newInvokeStmt( Jimple.v().newStaticInvokeExpr( postClinit ) ),
                        s );
            }
        }
    }

    public void weaveInAspects( SootClass cl ) {
        for( Iterator methodIt = cl.getMethods().iterator(); methodIt.hasNext(); ) {
            final SootMethod method = (SootMethod) methodIt.next();
            if( method.isAbstract() ) continue;
            if( method.isNative() ) continue;

            Body b = method.getActiveBody();
            Chain units = b.getUnits();
            Iterator it = units.snapshotIterator();
            while( it.hasNext() ) {
                Stmt stmt = (Stmt) it.next();
                if( !(stmt instanceof AssignStmt) ) continue;
                AssignStmt as = (AssignStmt) stmt;
                Value lhs = as.getLeftOp();
                if( !(lhs instanceof FieldRef) ) continue;

                for( Iterator aspectIt = Scene.v().getApplicationClasses().iterator(); aspectIt.hasNext(); ) {

                    final SootClass aspect = (SootClass) aspectIt.next();
                    if( !isAspect( aspect ) ) continue;
                    Local l = Jimple.v().newLocal( localName(), aspect.getType() );
                    b.getLocals().add(l);
                    units.insertBefore( Jimple.v().newAssignStmt( l, Jimple.v().newStaticInvokeExpr( aspect.getMethod("aspectOf", new ArrayList()))), as);
                    units.insertBefore( 
                            Jimple.v().newInvokeStmt( Jimple.v().newVirtualInvokeExpr( l, aspect.getMethod("before$1", new ArrayList() ) ) ), as ); 
                }
            }
        }
    }
    private static boolean isAspect( SootClass cl ) {
        if( cl.getName().equals( "Aspect" ) ) return true;
        return false;
    }
    static int localNum = 1;
    private static String localName() {
        localNum++;
        return "aspect$"+localNum;
    }
}
