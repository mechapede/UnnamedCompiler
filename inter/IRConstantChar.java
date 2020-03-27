package inter;

public class IRConstantChar extends IRConstant {
  public char value;
  public IRConstantChar(char c) {
    value = c;
  }

  public String toString() {
    return "'" + value + "'";
  }

  public Object accept(IRVisitor v) {
    return v.visit(this);
  }

}
