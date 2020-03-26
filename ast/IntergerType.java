package ast;

public class IntergerType extends FloatType {

  public IntergerType(int tokenline, int tokenchar) {
    super(tokenline, tokenchar);
  }

  public Object accept(Visitor v) {
    return v.visit(this);
  }

  public String toString() {
    return "int";
  }
}
