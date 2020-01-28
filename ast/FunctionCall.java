package ast;

public class FunctionCall extends Expression{
    Identifier name;
    ExpressionList args;

    public FunctionCall(int tokenline, int tokenchar,Identifier name, ExpressionList args){
        super(tokenline,tokenchar);
        this.name = name;
        this.args = args;
    }

    public Object accept(Visitor v){
         return v.visit(this);
    }

}
