package ast;

public class ExpressionStatement extends Statement{
    Expression expression;

    public ExpressionStatement(Expression expression){
        //TODO: line numbers
        this.expression = expression;
    }

    public Object accept(Visitor v){
         return v.visit(this);
    }
}
