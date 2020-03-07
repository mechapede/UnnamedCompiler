package inter;

public class IRConstantBool extends IRConstant {
        boolean value;
        public IRConstantBool(boolean b) {
            value = b;
        }

        public String toString() {
            return "" + value;
        }
}
