package inter;

import java.util.ArrayList;

public class IRFunction extends IRNode {
  public String id;
  public Temporary.Type ret;
  public ArrayList<IRStatement> statements;
  public TempFactory vars;

  public IRFunction(String id, Temporary.Type ret) {
    this.id = id;
    this.ret = ret;
    statements = new ArrayList<IRStatement>();
    vars = new TempFactory();
  }

  public void addStatement(IRStatement s) {
    statements.add(s);
  }

  public int getStatementCount() {
    return statements.size();
  }

  public IRStatement getStatement(int index) {
    return statements.get(index);
  }

  public String toString() {
    String buff = "";
    buff += "FUNC " + id;
    buff += " (";
    int i = 0;
    while(i < vars.temps.size()) {
      Temporary t = vars.temps.get(i);
      if(t.use != Temporary.Use.PARAMETER) break;
      buff += t.type;
      i++;
    }
    buff += ")" + ret +"\n";
    buff += "{\n";
    i = 0;
    while(i < vars.temps.size()) {
      Temporary t = vars.temps.get(i);
      if(t.name != null) {
        buff += "  " + "TEMP " + t.index + ":" + t.type + "  [" + t.use + "(\"" + t.name + "\")];\n";
      } else {
        buff += "  " + "TEMP " + t.index + ":" + t.type + ";\n";
      }
      i++;
    }
    for(i =0; i < statements.size(); i++) {
      buff += statements.get(i) + ";\n";
    }
    buff += "}\n";
    return buff;
  }

  public Object accept(IRVisitor v) {
    return v.visit(this);
  }
}
