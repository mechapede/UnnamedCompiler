package ast;

public class IntergerType extends Type{

    public IntergerType(int tokenline, int tokenchar){
        super(tokenline, tokenchar);
    }

    public Object accept(Visitor v){
         return v.visit(this);
    }

}
