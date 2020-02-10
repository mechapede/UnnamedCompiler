package ast;

public class PrintLnStatement extends Statement {
        Expression expression;

        public PrintLnStatement(Expression expression, int tokenline, int tokenchar) {
            super(tokenline,tokenchar);
            this.expression = expression;
        }

        public Object accept(Visitor v) {
            return v.visit(this);
        }

}
