package ast;

public class WhileStatement extends Statement{
    Expression expression;
    Block block;

    public WhileStatement(){
        expression = null;
        block = null;
    }

    public void setExpression(Expression expression){
        this.expression = expression;
    }

    public void setBlock(Block block){
        this.block = block;
    }
}
