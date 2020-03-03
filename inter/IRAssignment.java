package inter;

public class IRAssignment extends IRStatement{
    Temporary location;
    IRConstant c;
    public IRAssignment(Temporary location, IRConstant c){
        this.location = location;
        this.c = c;
    }
}
