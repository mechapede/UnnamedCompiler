package inter;

import java.util.ArrayList;

public class IRFunction{
    public String id;
    public Temporary.Type ret;
    public ArrayList<IRStatement> statements;
    public TempFactory vars;
    
    public IRFunction(String id){
        this.id = id;
        statements = new ArrayList<IRStatement>();
        vars = new TempFactory();
    }
    
    public void addStatement(IRStatement s){
        statements.add(s);
    }
}
