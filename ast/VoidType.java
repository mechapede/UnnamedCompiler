package ast;

public class VoidType extends Type {

  public VoidType(int tokenline, int tokenchar) {
    super(tokenline, tokenchar);
  }

  public Object accept(Visitor v) {
    return v.visit(this);
  }

  public String toString() {
    return "void";
  }

}
