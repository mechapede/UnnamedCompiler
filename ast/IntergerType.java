package ast;

public class IntergerType extends Type{

    public Object accept(Visitor v){
         return v.visit(this);
    }

}
