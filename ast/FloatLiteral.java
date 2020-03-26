package ast;

public class FloatLiteral extends Expression {
  public float value;

  public FloatLiteral(float value, int tokenline, int tokenchar) {
    super(tokenline,tokenchar);
    this.value = value;
  }

  public Object accept(Visitor v) {
    return v.visit(this);
  }

  public String toString() {
    return "float";
  }
}
