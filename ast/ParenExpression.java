package ast;

public class ParenExpression{

    public Object accept(Visitor v){
         return v.visit(this);
    }

}
