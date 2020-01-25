package ast;

public class MultiExpression extends Expression {
    Expression e1;
    Expression e2;

    public MultiExpression(int tokenline, int tokenchar,Expression e1, Expression e2){
        super(tokenline, tokenchar);
        this.e1 = e1;
        this.e2 = e2;
    }

    public Object accept(Visitor v){
         return v.visit(this);
    }

}
