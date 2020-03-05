package inter;

public class IRArrayAssignment extends IRStatement{
    Temporary dest;
    Temporary input;
    Temporary index;
    public IRArrayAssignment(Temporary dest, Temporary input, Temporary index){
        this.dest = dest;
        this.input = input;
        this.index = index;
    }
}
