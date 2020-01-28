package ast;

public class StringLiteral extends Expression{
    String val;

    public StringLiteral(int tokenline,int tokenchar,String val){
        super(tokenline,tokenchar);
        this.val = val;
    }

    public Object accept(Visitor v){
         return v.visit(this);
    }

}
