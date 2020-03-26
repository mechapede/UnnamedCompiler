package ast;

public class WhileStatement extends Statement {
  public Expression cond;
  public Block block;

  public WhileStatement() {
    cond = null;
    block = null;
  }

  public Object accept(Visitor v) {
    return v.visit(this);
  }
}
