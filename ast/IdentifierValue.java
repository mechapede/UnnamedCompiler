package ast;

public class IdentifierValue extends Expression {
        Identifier name;

        public IdentifierValue(Identifier name,int tokenline, int tokenchar) {
            super(tokenline,tokenchar);
            this.name = name;
        }

        public Object accept(Visitor v) {
            return v.visit(this);
        }

}
