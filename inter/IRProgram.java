package inter;

import java.util.ArrayList;

public class IRProgram {
    private ArrayList<IRFunction> functions; 
    private String name;
    
    public IRProgram() {
        name = "main";
        functions = new ArrayList<IRFunction>();
    }

    public void addFunction(IRFunction f) {
        functions.add(f);
    }
    
    public void setName(String name){
        this.name = name;
    }

    public String toString() {
        String buff = "PROG " + name + "\n";
        for(int i=0; i<functions.size(); i++) {
            buff += functions.get(i) +"\n";
        }
        return buff;
    }
}
