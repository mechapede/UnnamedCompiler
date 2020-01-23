package ast;

public class FunctionDeclaration{
    Type type;
    Identifier id;
    FormalParameterList pl;

    public FunctionDeclaration(){
        this.type = null;
        this.id = null;
        this.pl = null;
    }

    public void setType(Type type){
        this.type = type;
    }

    public void setIdentifier(Identifier id){
        this.id = id;
    }

    public void setParameters(FormalParameterList pl){
        this.pl = pl;
    }

}