package inter;

import java.util.ArrayList;

public class TempFactory {
        public ArrayList<Temporary> temps; 
        int label_count = 0; //no need to save labels

        public TempFactory() {
            temps = new ArrayList<Temporary>();
        }

        public Temporary getArg(Temporary.Type t) {
            return getArg(t,null);
        }

        public Temporary getArg(Temporary.Type t, String name) {
            Temporary a = new Temporary(temps.size(),Temporary.Use.PARAMETER,t,name);
            temps.add(a);
            return a;
        }

        public Temporary getTemp(Temporary.Type t) {
            return getTemp(t,null);
        }

        public Temporary getTemp(Temporary.Type type, String name) {
            Temporary t = new Temporary(temps.size(),Temporary.Use.LOCAL,type,name);
            temps.add(t);
            return t;
        }

        public IRLabel getLabel() {
            IRLabel l = new IRLabel("L" + label_count);
            label_count++;
            return l;
        }
}
