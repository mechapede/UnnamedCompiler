package inter;

public class IRPrintln extends IRStatement {
  public Temporary contents;

  public IRPrintln(Temporary contents) {
    this.contents = contents;
  }

  public String toString() {
    return "  PRINTLN" + contents.type + " " + contents;
  }

  public Object accept(IRVisitor v) {
    return v.visit(this);
  }
}
