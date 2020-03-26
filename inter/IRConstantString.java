package inter;

public class IRConstantString extends IRConstant {
  public String value;
  public IRConstantString(String s) {
    value = s;
  }

  public String toString() {
    return "\"" + value + "\"";
  }

  public String jsmVal() {
    return "\"" + value + "\"";
  }

  public Object accept(IRVisitor v) {
    return v.visit(this);
  }

}
