package ast;

public class ArrayType extends Type{
    Type type;
    IntergerLiteral size;

    public ArrayType(){
        type = null;
        size = null;
    }

    public ArrayType(Type type, IntergerLiteral size){
        type = type;
        size = size;
    }

    public void setType(Type type){
        this.type = type;
    }

    public void setSize(IntergerLiteral size){
        this.size = size;
    }

    public Object accept(Visitor v){
         return v.visit(this);
    }

}
