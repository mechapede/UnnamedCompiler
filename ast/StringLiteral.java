package ast;

public class StringLiteral extends Expression{
    String val;

    public StringLiteral(String var){
        this.val = val;
    }

    public Object accept(Visitor v){
         return v.visit(this);
    }

}
