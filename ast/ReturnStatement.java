package ast;

public class ReturnStatement extends Statement{
    Expression expression;

    public ReturnStatement(int tokenline, int tokenchar,Expression expression){
        super(tokenline,tokenchar);
        this.expression = expression;
    }

    public Object accept(Visitor v){
         return v.visit(this);
    }

}
