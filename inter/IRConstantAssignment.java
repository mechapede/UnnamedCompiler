package inter;

public class IRConstantAssignment extends IRStatement {
    public Temporary dest;
    public IRConstant val;

    public IRConstantAssignment(Temporary dest, IRConstant val) {
        this.dest = dest;
        this.val = val;
    }

    public String toString() {
        return "  " +  dest + " := " + val;
    }
}
