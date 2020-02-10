package ast;

public class FunctionDeclaration extends TreeNode {
        Type type;
        Identifier id;
        FormalParameterList pl;

        public FunctionDeclaration() {
            this.type = null;
            this.id = null;
            this.pl = null;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public void setIdentifier(Identifier id) {
            this.id = id;
        }

        public void setParameters(FormalParameterList pl) {
            this.pl = pl;
        }

        public Object accept(Visitor v) {
            return v.visit(this);
        }

}
