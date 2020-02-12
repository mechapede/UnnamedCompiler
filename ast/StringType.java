package ast;

public class StringType extends Type{

    public StringType(int tokenline, int tokenchar){
        super(tokenline, tokenchar);
    }

    public Object accept(Visitor v){
         return v.visit(this);
    }

    public String toString(){
        return "string";
    }
}
