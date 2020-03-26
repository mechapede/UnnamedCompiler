package ast;

public class IfStatement extends Statement {
  public Expression cond;
  public Block block;
  public Block elseblock;

  public IfStatement() {
    cond = null;
    block = null;
    elseblock = null;
  }

  public Object accept(Visitor v) {
    return v.visit(this);
  }

}
