package inter;

public class IRConstantBool extends IRConstant {
  public boolean value;
  public IRConstantBool(boolean b) {
    value = b;
  }

  public String toString() {
    return  value? "TRUE":"FALSE";
  }

  public Object accept(IRVisitor v) {
    return v.visit(this);
  }
}
