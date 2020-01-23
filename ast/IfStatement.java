package ast;

public class IfStatement extends Statement{
    Expression expression;
    Block block;
    Block elseblock;

    public IfStatement(){
        expression = null;
        block = null;
        elseblock = null;
    }

    public void setExpression(Expression expression){
        this.expression = expression;
    }

    public void setBlock(Block block){
        this.block = block;
    }

    public void setElseBlock(Block elseblock){
        this.elseblock = elseblock;
    }

}
