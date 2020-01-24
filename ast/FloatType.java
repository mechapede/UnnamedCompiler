package ast;

public class FloatType extends Type{

    public Object accept(Visitor v){
         return v.visit(this);
    }

}
