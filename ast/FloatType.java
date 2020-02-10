package ast;

public class FloatType extends Type {

        public FloatType(int tokenline, int tokenchar) {
            super(tokenline, tokenchar);
        }

        public Object accept(Visitor v) {
            return v.visit(this);
        }

}
