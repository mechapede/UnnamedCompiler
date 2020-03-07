package inter;

public class IRLabel extends IRStatement{
        String name;
        public IRLabel(String name) {
            this.name = name;
        }
        
        public String toString(){
            return name + ":";
        }
}
