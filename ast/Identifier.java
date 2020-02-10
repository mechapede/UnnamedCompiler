package ast;

public class Identifier extends TreeNode implements Comparable<Identifier> {
        String id;

        public Identifier(String id) {
            this.id = id;
        }

        public Identifier(String id, int tokenline, int tokenchar) {
            super(tokenline, tokenchar);
            this.id = id;
        }

        public Object accept(Visitor v) {
            return v.visit(this);
        }

        public boolean equals(Identifier other) {
            return this.id.equals(other.id);
        }

        public int compareTo(Identifier other) {
            return this.id.compareTo(other.id);
        }

}
