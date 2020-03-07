package inter;

public class IRArrayValue extends IRStatement{
    Temporary array;
    Temporary index;
    Temporary dest;
    public IRArrayValue(Temporary array, Temporary index, Temporary dest){
        this.array = array;
        this.index = index;
        this.dest = dest;
    }
    
    public String toString(){
        return "  " + dest + " := " + array + "[" + index + "]";
    }
    
}
