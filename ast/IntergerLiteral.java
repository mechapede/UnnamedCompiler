package ast;

public class IntergerLiteral extends Expression {
  public int value;

  public IntergerLiteral(int value, int tokenline, int tokenchar) {
    super(tokenline,tokenchar);
    this.value = value;
  }

  public Object accept(Visitor v) {
    return v.visit(this);
  }

}
