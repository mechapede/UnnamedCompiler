package ast;

public class PrintLnStatement extends Statement{
    Expression expression;

    public PrintLnStatement(int tokenline, int tokenchar,Expression expression){
        super(tokenline,tokenchar);
        this.expression = expression;
    }

    public Object accept(Visitor v){
         return v.visit(this);
    }

}
