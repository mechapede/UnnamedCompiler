package inter;

public class IRArrayValue extends IRStatement {
  public Temporary array;
  public Temporary index;
  public Temporary dest;

  public IRArrayValue(Temporary array, Temporary index, Temporary dest) {
    this.array = array;
    this.index = index;
    this.dest = dest;
  }

  public String toString() {
    return "  " + dest + " := " + array + "[" + index + "]";
  }

  public Object accept(IRVisitor v) {
    return v.visit(this);
  }
}
