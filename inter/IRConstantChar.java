package inter;

public class IRConstantChar extends IRConstant {
        char value;
        public IRConstantChar(char c) {
            value = c;
        }

        public String toString() {
            return "" + value;
        }
}
