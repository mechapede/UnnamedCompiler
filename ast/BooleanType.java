package ast;

public class BooleanType extends Type{
    
    public Object accept(Visitor v){
         return v.visit(this);
    }

}
