package ast;

public class ArrayValue extends Expression {
        Identifier name;
        Expression index;

        public ArrayValue(Identifier name, Expression index, int tokenline, int tokenchar) {
            super(tokenline,tokenchar);
            this.name = name;
            this.index = index;
        }

        public Object accept(Visitor v) {
            return v.visit(this);
        }

}
