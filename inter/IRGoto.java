package inter;

public class IRGoto extends IRStatement {
        public IRLabel label;

        public IRGoto(IRLabel label) {
            this.label = label;
        }

        public String toString() {
            return "  GOTO " + label.name;
        }

        public Object accept(IRVisitor v) {
            return v.visit(this);
        }
}
