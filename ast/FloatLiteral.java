package ast;

public class FloatLiteral extends Expression{
    float val;

    public FloatLiteral(int tokenline,int tokenchar,float val){
        super(tokenline,tokenchar);
        this.val = val;
    }

    public Object accept(Visitor v){
         return v.visit(this);
    }

}
