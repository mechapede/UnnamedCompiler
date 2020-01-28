package ast;

public class ExpressionStatement extends Statement{
    Expression expression;

    public ExpressionStatement(int tokenline, int tokenchar,Expression expression){
        super(tokenline,tokenchar);
        this.expression = expression;
    }

    public Object accept(Visitor v){
         return v.visit(this);
    }
}
