package ast;

public class AssignmentStatement extends Statement {
        Identifier name;
        Expression value;

        public AssignmentStatement() {
            name = null;
            value = null;
        }

        public void setName(Identifier name) {
            this.name = name;
        }

        public void setValue(Expression value) {
            this.value = value;
        }

        public Object accept(Visitor v) {
            return v.visit(this);
        }

}
