package ast;

public class BooleanLiteral extends Expression{
    boolean val;

    public BooleanLiteral(int tokenline,int tokenchar,boolean val){
        super(tokenline,tokenchar);
        this.val = val;
    }

    public Object accept(Visitor v){
         return v.visit(this);
    }

}
