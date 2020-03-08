package ast;

public class ArrayAssignment extends Statement {
    public Identifier id;
    public Expression index;
    public Expression value;

    public ArrayAssignment() {
        id = null;
        index = null;
        value = null;
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }

}
