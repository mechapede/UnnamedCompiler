package inter;

public class IRArrayAssignment extends IRStatement {
  public Temporary dest;
  public Temporary input;
  public Temporary index;
  public IRArrayAssignment(Temporary dest, Temporary input, Temporary index) {
    this.dest = dest;
    this.input = input;
    this.index = index;
  }

  public String toString() {
    return "  " + dest + "[" + index + "]" +  " := " + input;
  }

  public Object accept(IRVisitor v) {
    return v.visit(this);
  }
}
