package ast;

public class ArrayValue extends Expression{
    Identifier identifier;
    Expression id;

    public ArrayValue(Identifier identifier,Expression id){
        this.identifier = identifier;
        this.id = id;
    }

    public Object accept(Visitor v){
         return v.visit(this);
    }

}