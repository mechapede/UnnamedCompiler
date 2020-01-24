package ast;

public class FloatLiteral extends Expression{
    float val;

    public FloatLiteral(float val){
        this.val = val;
    }

    public Object accept(Visitor v){
         return v.visit(this);
    }

}
