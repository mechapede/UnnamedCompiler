package inter;

public class IRConstantInt extends IRConstant {
        int value;
        public IRConstantInt(int i) {
            value = i;
        }

        public String toString() {
            return "" + value;
        }

}
