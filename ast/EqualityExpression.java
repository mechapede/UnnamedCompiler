package ast;

public class EqualityExpression extends Expression{
    Expression e1;
    Expression e2;

    public EqualityExpression(Expression e1, Expression e2){
        this.e1 = e1;
        this.e2 = e2;
    }
}
