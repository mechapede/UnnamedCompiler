package ast;

public class CharType extends Type {

        public CharType(int tokenline, int tokenchar) {
            super(tokenline, tokenchar);
        }

        public Object accept(Visitor v) {
            return v.visit(this);
        }

}
