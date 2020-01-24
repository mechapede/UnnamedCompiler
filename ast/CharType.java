package ast;

public class CharType extends Type{

    public Object accept(Visitor v){
         return v.visit(this);
    }

}
