package ast;

public class Declaration{

    public Object accept(Visitor v){
         return v.visit(this);
    }

}
