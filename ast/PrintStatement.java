package ast;

public class PrintStatement extends Statement{
    Expression expression;

    public PrintStatement(int tokenline, int tokenchar,Expression expression){
        super(tokenline,tokenchar);
        this.expression = expression;
    }

    public Object accept(Visitor v){
         return v.visit(this);
    }

}
