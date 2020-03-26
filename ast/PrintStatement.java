package ast;

public class PrintStatement extends Statement {
  public Expression expression;

  public PrintStatement(Expression expression, int tokenline, int tokenchar) {
    super(tokenline,tokenchar);
    this.expression = expression;
  }

  public Object accept(Visitor v) {
    return v.visit(this);
  }

}
