package ast;

public class IfStatement extends Statement {
        Expression cond;
        Block block;
        Block elseblock;

        public IfStatement() {
            cond = null;
            block = null;
            elseblock = null;
        }

        public void setCond(Expression cond) {
            this.cond = cond;
        }

        public void setBlock(Block block) {
            this.block = block;
        }

        public void setElseBlock(Block elseblock) {
            this.elseblock = elseblock;
        }

        public Object accept(Visitor v) {
            return v.visit(this);
        }

}
