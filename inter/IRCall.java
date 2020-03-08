package inter;

import java.util.ArrayList;

public class IRCall extends IRStatement {
    public Temporary dest; //null if void call
    public IRFunction function;
    public ArrayList<Temporary> args;

    public IRCall(Temporary dest, IRFunction function) {
        this.dest = dest;
        this.function = function;
        this.args = new ArrayList<Temporary>();
    }

    public IRCall(IRFunction f) {
        this(null,f);
    }

    public String toString() {
        String buff = "  ";
        if(dest != null) buff += dest + " := CALL ";
        buff += function.id + "(";
        if(args.size() > 0) buff += args.get(0);
        for(int i=1; i < args.size(); i++) {
            buff += " " + args.get(i);
        }
        buff += ")";
        return buff;
    }
}
