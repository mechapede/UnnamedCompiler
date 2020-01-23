package ast;

public class ArrayAssignment extends Statement{
    Identifier identifier;
    Expression index;
    Expression expression;

    public ArrayAssignment(){
        identifier = null;
        expression = null;
        index = null;
    }

    public void setIdentifier(Identifier identifier){
        this.identifier = identifier;
    }

    public void setExpression(Expression expression){
        this.expression = expression;
    }

    public void setIndexExpression(Expression index){
        this.index = index;
    }

}
