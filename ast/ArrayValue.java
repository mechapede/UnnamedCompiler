package ast;

public class ArrayValue extends Expression{
    Identifier identifier;
    Expression index;

    public ArrayValue(Identifier identifier,Expression index){
        this.identifier = identifier;
        this.index = index;
    }

    public Object accept(Visitor v){
         return v.visit(this);
    }

}
