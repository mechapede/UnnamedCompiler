package ast;

public class BooleanType extends Type {

        public BooleanType(int tokenline, int tokenchar) {
            super(tokenline, tokenchar);
        }

        public Object accept(Visitor v) {
            return v.visit(this);
        }

}
