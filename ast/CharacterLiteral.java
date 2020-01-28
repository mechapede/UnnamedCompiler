package ast;

public class CharacterLiteral extends Expression{
    char val;

    public CharacterLiteral(int tokenline,int tokenchar,char val){
        super(tokenline,tokenchar);
        this.val = val;
    }

    public Object accept(Visitor v){
         return v.visit(this);
    }

}
