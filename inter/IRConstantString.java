package inter;

public class IRConstantString extends IRConstant {
        String value;
        public IRConstantString(String s) {
            value = s;
        }

        public String toString() {
            return "\"" + value + "\"";
        }
}
