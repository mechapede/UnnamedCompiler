package ast;

public class StringLiteral extends Expression{
    String var;

    public StringLiteral(String var){
        this.var = var;
    }

    public Object accept(Visitor v){
         return v.visit(this);
    }
    
}
