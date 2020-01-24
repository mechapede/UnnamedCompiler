package ast;

public class FormalParameter{
    Type type;
    Identifier identifier;

    public FormalParameter(){
        type = null;
        identifier = null;
    }

    public void setType(Type type){
        this.type = type;
    }

    public void setIdentifier(Identifier identifier){
        this.identifier = identifier;
    }

    public Object accept(Visitor v){
         return v.visit(this);
    }
}
