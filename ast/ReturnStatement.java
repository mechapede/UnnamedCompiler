package ast;

public class ReturnStatement extends Statement {
        Expression expression;

        public ReturnStatement(Expression expression, int tokenline, int tokenchar) {
            super(tokenline,tokenchar);
            this.expression = expression;
        }

        public Object accept(Visitor v) {
            return v.visit(this);
        }

}
