package abc.eaj.ast;

import polyglot.types.Context;

import abc.aspectj.ast.PointcutDecl;

import abc.eaj.ast.EAJNodeFactory;
import abc.eaj.visit.GlobalPointcuts;

public interface GlobalPointcutDecl extends PointcutDecl
{
    public void registerGlobalPointcut(GlobalPointcuts visitor,
                                       Context context,
                                       EAJNodeFactory nf);
}
