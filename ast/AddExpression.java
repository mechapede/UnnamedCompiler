package ast;

public class AddExpression extends Expression{
    Expression e1;
    Expression e2;

    public AddExpression(Expression e1, Expression e2){
        this.e1 = e1;
        this.e2 = e2;
    }

    public Object accept(Visitor v){
         return v.visit(this);
    }
}
