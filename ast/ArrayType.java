package ast;

public class ArrayType extends Type {
        Type type;
        IntergerLiteral size;

        public ArrayType() {
            this.type = null;
            this.size = null;
        }

        public ArrayType(Type type, IntergerLiteral size) {
            this.type = type;
            this.size = size;
        }

        public ArrayType(Type type, IntergerLiteral size, int tokenline, int tokenchar) {
            super(tokenline,tokenchar);
            this.type = type;
            this.size = size;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public void setSize(IntergerLiteral size) {
            this.size = size;
        }

        public Object accept(Visitor v) {
            return v.visit(this);
        }

        public boolean equals(ArrayType other) {
            return this.getClass() == other.getClass() &&
                   this.size == other.size &&
                   this.type.equals(other.type);
        }

}
