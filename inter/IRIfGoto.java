package inter;

public class IRIfGoto extends IRStatement {
  Temporary cond;
  IRLabel label;

  public IRIfGoto(Temporary cond, IRLabel label) {
    this.cond = cond;
    this.label = label;
  }

  public String toString() {
    return "  IF " + cond + " GOTO " + label.name;
  }

  public Object accept(IRVisitor v) {
    return v.visit(this);
  }
}
