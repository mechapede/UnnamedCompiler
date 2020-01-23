package ast;

public class ArrayType extends Type{
    Type type;
    int size;

    public ArrayType(){
        type = null;
        size = 0;
    }

    public ArrayType(Type type, int size){
        type = type;
        size = size;
    }

    public void setType(Type type){
        this.type = type;
    }

    public void setSize(int size){
        this.size = size;
    }

}
