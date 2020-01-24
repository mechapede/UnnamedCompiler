package ast;

public class VoidType extends Type{

    public Object accept(Visitor v){
         return v.visit(this);
    }

}
