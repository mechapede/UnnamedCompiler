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
    
    public String toString(){
        String buff = "";
        buff += "FUNC " + id;
        buff += " (";
        for(int i =0; i < vars.args.size(); i++){
            buff += Temporary.TypeConvert(vars.args.get(i).type);
        }
        buff += ")" + Temporary.TypeConvert(ret) +"\n";
        buff += "{";
        for(int i =0; i < vars.args.size(); i++){
            buff += Temporary.TypeConvert(vars.args.get(i).type);
        }
        for(int i =0; i < vars.temps.size(); i++){
            buff += Temporary.TypeConvert(vars.args.get(i).type);
        }
        for(int i =0; i < statements.size(); i++){
            buff += statements.get(i) + "\n";
        }
        buff += "}";
        return buff;
    }
}
