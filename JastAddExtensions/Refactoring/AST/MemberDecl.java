
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.FileNotFoundException;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import sun.text.normalizer.UTF16;import changes.*;import main.FileRange;


public abstract class MemberDecl extends BodyDecl implements Cloneable {
    public void flushCache() {
        super.flushCache();
    }
    public Object clone() throws CloneNotSupportedException {
        MemberDecl node = (MemberDecl)super.clone();
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
    // Declared in Modifiers.jrag at line 181

   
  public void checkModifiers() {
    if(!isSynthetic()) {
      super.checkModifiers();
      if(isStatic() && hostType().isInnerClass() && !isConstant())
        error("*** Inner classes may not declare static members, unless they are compile-time constant fields");
    }
  }

    // Declared in java.ast at line 3
    // Declared in java.ast line 73

    public MemberDecl() {
        super();


    }

    // Declared in java.ast at line 9


  protected int numChildren() {
    return 0;
  }

    // Declared in java.ast at line 12

  public boolean mayHaveRewrite() { return false; }

    // Declared in Modifiers.jrag at line 207
    public abstract boolean isStatic();
    // Declared in ConstantExpression.jrag at line 453
    public boolean isConstant() {
        boolean isConstant_value = isConstant_compute();
        return isConstant_value;
    }

    private boolean isConstant_compute() {  return  false;  }

    // Declared in Modifiers.jrag at line 203
    public boolean isSynthetic() {
        boolean isSynthetic_value = isSynthetic_compute();
        return isSynthetic_value;
    }

    private boolean isSynthetic_compute() {  return  false;  }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
