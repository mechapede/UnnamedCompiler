package ast;

public class ArrayType extends Type{
    Type type;
    IntergerLiteral size;

    public ArrayType(){
        this.type = null;
        this.size = null;
    }

    public ArrayType(Type type, IntergerLiteral size){
        this.type = type;
        this.size = size;
    }

    public ArrayType(int tokenline, int tokenchar,Type type, IntergerLiteral size){
        super(tokenline,tokenchar);
        this.type = type;
        this.size = size;
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
