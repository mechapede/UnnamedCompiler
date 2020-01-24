package ast;

public class CharacterLiteral extends Expression{
    char val;

    public CharacterLiteral(char val){
        this.val = val;
    }

    public Object accept(Visitor v){
         return v.visit(this);
    }

}
