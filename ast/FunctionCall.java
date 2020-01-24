package ast;

public class FunctionCall extends Expression{
    Identifier name;
    ExpressionList args;

    public FunctionCall(Identifier name, ExpressionList args){
        this.name = name;
        this.args = args;
    }

    public Object accept(Visitor v){
         return v.visit(this);
    }

}
