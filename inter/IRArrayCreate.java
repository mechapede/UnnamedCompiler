package inter;

public class IRArrayCreate extends IRStatement {
    public int size;
    public Temporary dest;

    public IRArrayCreate(Temporary dest,int size) {
        this.dest = dest;
        this.size = size;
    }

    public String toString() {
        return "  " + dest + " := " +  "NEWARRAY " + dest.type.subType() + " " + size + "";
    }
}
