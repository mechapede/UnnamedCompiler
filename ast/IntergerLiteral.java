package ast;

public class IntergerLiteral extends Expression{
    int val;

    public IntergerLiteral(int val){
        this.val = val;
    }

    public Object accept(Visitor v){
         return v.visit(this);
    }

}
