package ast;

public class AssignmentStatement extends Statement{
    Identifier identifier;
    Expression expression;

    public AssignmentStatement(){
        identifier = null;
        expression = null;
    }

    public void setIdentifier(Identifier identifier){
        this.identifier = identifier;
    }

    public void setExpression(Expression expression){
        this.expression = expression;
    }

    public Object accept(Visitor v){
         return v.visit(this);
    }

}
