package ast;

public class AssignmentStatement extends Statement {
    public Identifier id;
    public Expression value;

    public AssignmentStatement() {
        id = null;
        value = null;
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }

}
