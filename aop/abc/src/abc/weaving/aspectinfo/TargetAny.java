package abc.weaving.aspectinfo;

import soot.*;
import polyglot.util.Position;
import abc.weaving.matching.*;
import abc.weaving.residues.*;

/** Handler for <code>target</code> condition pointcut with a universal pattern argument. */
public class TargetAny extends DynamicValuePointcut {

    public TargetAny(Position pos) {
	super(pos);
    }

    public final Residue matchesAt(WeavingEnv we,
				   SootClass cls,
				   SootMethod method,
				   ShadowMatch sm) {
	ContextValue cv=sm.getTargetContextValue();
	if(cv==null) return null;
	return matchesAt(we,cv);
    }

    protected Residue matchesAt(WeavingEnv we,ContextValue cv) {
	return AlwaysMatch.v;
    }

    public String toString() {
	return "target(*)";
    }

}
