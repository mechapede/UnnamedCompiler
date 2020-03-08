package ast;

public class ArrayType extends Type {
    public Type type;
    public IntergerLiteral size;

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

    public Object accept(Visitor v) {
        return v.visit(this);
    }

    public boolean equals(ArrayType other) {
        return this.getClass() == other.getClass() &&
               this.size == other.size &&
               this.type.equals(other.type);
    }

    public String toString() {
        return this.type.toString() + "[]";
    }

}
