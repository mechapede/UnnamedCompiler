package inter;

public class IRAssignment extends IRStatement{
    Temporary dest;
    Temporary input;
    public IRAssignment(Temporary dest, Temporary input){
        this.dest = dest;
        this.input = input;
    }
    
    public String toString(){
        return "  " +  dest +  " := " + input;
    }
}
