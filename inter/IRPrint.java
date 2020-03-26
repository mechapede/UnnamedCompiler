package inter;

public class IRPrint extends IRStatement {
  public Temporary contents;

  public IRPrint(Temporary contents) {
    this.contents = contents;
  }

  public String toString() {
    return "  PRINT" + contents.type + " " + contents;
  }

  public Object accept(IRVisitor v) {
    return v.visit(this);
  }
}
