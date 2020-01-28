package ast;

public class IntergerLiteral extends Expression{
    int val;

    public IntergerLiteral(int tokenline,int tokenchar,int val){
        super(tokenline,tokenchar);
        this.val = val;
    }

    public Object accept(Visitor v){
         return v.visit(this);
    }

}
