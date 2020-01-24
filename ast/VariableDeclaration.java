package ast;

public class VariableDeclaration{
    Type type;
    Identifier identifier;

    public VariableDeclaration(){
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
