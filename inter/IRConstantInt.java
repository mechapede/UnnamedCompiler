package inter;

public class IRConstantInt extends IRConstant {
    public int value;
    public IRConstantInt(int i) {
        value = i;
    }

    public String toString() {
        return "" + value;
    }

}
