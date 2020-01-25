package ast;

public class Identifier extends TreeNode{
    String id;

    public Identifier(int tokenline, int tokenchar, String id){
        super(tokenline, tokenchar);
        this.id = id;
    }

    public Object accept(Visitor v){
         return v.visit(this);
    }

}
