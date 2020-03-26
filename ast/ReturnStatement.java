package ast;

public class ReturnStatement extends Statement {
  public Expression expression;

  public ReturnStatement() {
    super();
    this.expression = null;
  }

  public ReturnStatement(Expression expression, int tokenline, int tokenchar) {
    super(tokenline,tokenchar);
    this.expression = expression;
  }

  public Object accept(Visitor v) {
    return v.visit(this);
  }

}
