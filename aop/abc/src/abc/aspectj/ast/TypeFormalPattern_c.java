package abc.aspectj.ast;

import polyglot.ast.*;

import polyglot.types.*;
import polyglot.util.*;
import polyglot.visit.*;
import java.util.*;

import polyglot.ext.jl.ast.Node_c;

public class TypeFormalPattern_c extends Node_c 
                                 implements TypeFormalPattern
{

    TypePatternExpr expr;

    public TypeFormalPattern_c(Position pos,
			       TypePatternExpr expr) {
        super(pos);
        this.expr = expr;
    }
    
	/** Reconstruct the type pattern. */
    protected TypeFormalPattern_c reconstruct(TypePatternExpr expr) {
		if (expr != this.expr) {
			 TypeFormalPattern_c n = (TypeFormalPattern_c) copy();
			 n.expr = expr;
			 return n;
		}
		return this;
	}

	/** Visit the children of the type pattern. */
	public Node visitChildren(NodeVisitor v) {
		 TypePatternExpr expr = (TypePatternExpr) visitChild(this.expr, v);
		 return reconstruct(expr);
	}

    public void prettyPrint(CodeWriter w, PrettyPrinter pp) {
	print(expr,w,pp);
    }

}
