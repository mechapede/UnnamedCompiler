package ast;

public class ArrayValue extends Expression{
    Identifier identifier;
    Expression index;

    public ArrayValue(int tokenline, int tokenchar,Identifier identifier,Expression index){
        super(tokenline,tokenchar);
        this.identifier = identifier;
        this.index = index;
    }

    public Object accept(Visitor v){
         return v.visit(this);
    }

}
