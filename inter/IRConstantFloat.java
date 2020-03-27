package inter;

public class IRConstantFloat extends IRConstant {
  public float value;
  public IRConstantFloat(float f) {
    value = f;
  }

  public String toString() {
    return "" + value;
  }

  public Object accept(IRVisitor v) {
    return v.visit(this);
  }

}
