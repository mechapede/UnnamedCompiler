package ast;

import java.util.ArrayList;

public class ExpressionList {
        private ArrayList<Expression> expressions;

        public ExpressionList() {
            expressions = new ArrayList<Expression>();
        }

        public void addExpression(Expression e) {
            expressions.add(e);
        }

        public Expression getExpression(int index) {
            return expressions.get(index);
        }

        public int getExpressionCount() {
            return expressions.size();
        }

        public Object accept(Visitor v) {
            return v.visit(this);
        }

}
