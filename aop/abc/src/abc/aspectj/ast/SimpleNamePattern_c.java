package arc.aspectj.ast;

import arc.aspectj.visit.*;

import polyglot.ast.*;

import polyglot.types.*;
import polyglot.util.*;
import polyglot.visit.*;

import java.util.*;
import java.util.regex.*;

public class SimpleNamePattern_c extends NamePattern_c 
                                 implements SimpleNamePattern
{
    String pat;

    public SimpleNamePattern_c(Position pos,String pat) {
        super(pos);
        this.pat = pat;
    }

    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
	w.write(pat);
    }

    public Set/*<PCNode>*/ match(PCNode context, Set/*<PCNode>*/ classes, Set/*<PCNode>*/ packages) {
	return context.matchScope(PCStructure.compilePattern(pat), classes, packages);
    }

    public boolean universal() {
	return pat.equals("*");
    }
}
