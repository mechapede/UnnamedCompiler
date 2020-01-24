package ast;

public class PrintStatement extends Statement{
    Expression expression;

    public PrintStatement(Expression expression){
        this.expression = expression;
    }

    public Object accept(Visitor v){
         return v.visit(this);
    }

}
