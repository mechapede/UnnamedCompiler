package ast;

public class WhileStatement extends Statement {
        Expression cond;
        Block block;

        public WhileStatement() {
            cond = null;
            block = null;
        }

        public void setCond(Expression cond) {
            this.cond = cond;
        }

        public void setBlock(Block block) {
            this.block = block;
        }

        public Object accept(Visitor v) {
            return v.visit(this);
        }
}
