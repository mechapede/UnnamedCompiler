package ast;

public class FunctionCall extends Expression {
  public Identifier name;
  public ExpressionList args;

  public FunctionCall(Identifier name, ExpressionList args, int tokenline, int tokenchar) {
    super(tokenline,tokenchar);
    this.name = name;
    this.args = args;
  }

  public Object accept(Visitor v) {
    return v.visit(this);
  }

}
