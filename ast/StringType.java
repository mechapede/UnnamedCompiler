package ast;

public class StringType extends Type{

    public Object accept(Visitor v){
         return v.visit(this);
    }

}
