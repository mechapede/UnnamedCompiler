package inter;

import java.util.ArrayList;

public class IRProgram{
    public ArrayList<IRFunction> functions;
    
    public IRProgram(){
        functions = new ArrayList<IRFunction>();
    }
    
    public void addFunction(IRFunction f){
        functions.add(f);
    }
}
