package ast;

public class SubtractExpression extends Expression {
    public Expression e1;
    public Expression e2;

    public SubtractExpression(Expression e1, Expression e2, int tokenline, int tokenchar) {
        super(tokenline,tokenchar);
        this.e1 = e1;
        this.e2 = e2;
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }

}
