package inter;

import java.util.ArrayList;

public class TempFactory{
    public ArrayList<Temporary> args; //allocation decided by order
    public ArrayList<Temporary> temps;
    
    public TempFactory(){
        args = new ArrayList<Temporary>();
        temps = new ArrayList<Temporary>();
    }

    public Temporary getArg(Temporary.Type t){
        return getArg(t,null);
    }

    public Temporary getArg(Temporary.Type t, String name){
        Temporary a = new Temporary(Temporary.Use.PARAMETER,t,name);
        args.add(a);
        return a;
    }
    
    public Temporary getTemp(Temporary.Type t){
        return getTemp(t,null);
    }
    
    public Temporary getTemp(Temporary.Type type, String name){
        Temporary t = new Temporary(Temporary.Use.LOCAL,type,name);
        temps.add(t);
        return t;
    }
    
    //TODO: labels
}
