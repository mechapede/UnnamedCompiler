package ast;

public class Expression extends Statement {

    public Expression(){
        super();
    }

    public Expression(int tokenline, int tokenchar){
        super(tokenline,tokenchar);
    }

    public Object accept(Visitor v){
         return null;
    }

}
