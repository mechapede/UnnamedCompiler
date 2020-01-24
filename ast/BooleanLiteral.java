package ast;

public class BooleanLiteral extends Expression{
    boolean val;

    public BooleanLiteral(boolean val){
        this.val = val;
    }

    public Object accept(Visitor v){
         return v.visit(this);
    }

}
