package ast;

public class Statement extends TreeNode{

    public Statement(){
        super();
    }

    public Statement(int tokenline, int tokenchar){
        super(tokenline,tokenchar);
    }

    public Object accept(Visitor v){
         return null;
    }
}
