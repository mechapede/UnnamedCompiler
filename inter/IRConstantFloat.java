package inter;

public class IRConstantFloat extends IRConstant {
        float value;
        public IRConstantFloat(float f) {
            value = f;
        }

        public String toString() {
            return "" + value;
        }
}
