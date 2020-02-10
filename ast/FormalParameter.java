package ast;

public class FormalParameter extends TreeNode {
        Type type;
        Identifier name;

        public FormalParameter() {
            type = null;
            name = null;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public void setName(Identifier name) {
            this.name = name;
        }

        public Object accept(Visitor v) {
            return v.visit(this);
        }
}
