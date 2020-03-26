package inter;

public class IRUnaryOp extends IRStatement {
  public enum Op {
    NEGATE {
      public String toString() {
        return "!";
      }
    }
  }

  public Temporary result;
  public Temporary input;
  public Op operation;

  public IRUnaryOp(Temporary result, Temporary input, Op operation) {
    this.result = result;
    this.input = input;
    this.operation = operation;
  }

  public String toString() {
    return "  " + result + " := " + input.type + operation + " " + input;
  }

  public Object accept(IRVisitor v) {
    return v.visit(this);
  }
}
