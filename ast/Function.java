package ast;

public class Function extends TreeNode{
    FunctionDeclaration decl;
    FunctionBody body;

    public Function(){
        this.decl = null;
        this.body = null;
    }

    public void setDeclaration(FunctionDeclaration decl){
        this.decl = decl;
    }

    public void setBody(FunctionBody body){
        this.body = body;
    }

    public Object accept(Visitor v){
        return v.visit(this);
    }

}
