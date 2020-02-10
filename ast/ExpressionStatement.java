package ast;

public class ExpressionStatement extends Statement {
        Expression expression;

        public ExpressionStatement(Expression expression, int tokenline, int tokenchar) {
            super(tokenline,tokenchar);
            this.expression = expression;
        }

        public Object accept(Visitor v) {
            return v.visit(this);
        }
}
