package inter;

import java.util.ArrayList;

public class IRProgram {
    private ArrayList<IRFunction> functions; 

    public IRProgram() {
        functions = new ArrayList<IRFunction>();
    }

    public void addFunction(IRFunction f) {
        functions.add(f);
    }

    public String toString() {
        String buff = "";
        for(int i=0; i<functions.size(); i++) {
            buff += functions.get(i) +"\n";
        }
        return buff;
    }
}
