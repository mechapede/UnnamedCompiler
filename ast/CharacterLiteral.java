package ast;

public class CharacterLiteral extends Expression {
        char value;

        public CharacterLiteral(char value, int tokenline, int tokenchar) {
            super(tokenline,tokenchar);
            this.value = value;
        }

        public Object accept(Visitor v) {
            return v.visit(this);
        }

}
