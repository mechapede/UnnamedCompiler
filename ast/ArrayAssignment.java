package ast;

public class ArrayAssignment extends Statement {
        Identifier name;
        Expression index;
        Expression value;

        public ArrayAssignment() {
            name = null;
            index = null;
            value = null;
        }

        public void setName(Identifier name) {
            this.name = name;
        }

        public void setValue(Expression value) {
            this.value = value;
        }

        public void setIndex(Expression index) {
            this.index = index;
        }

        public Object accept(Visitor v) {
            return v.visit(this);
        }

}
