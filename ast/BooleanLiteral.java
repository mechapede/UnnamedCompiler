package ast;

public class BooleanLiteral extends Expression {
    public boolean value;

    public BooleanLiteral(boolean value, int tokenline, int tokenchar) {
        super(tokenline,tokenchar);
        this.value = value;
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }

}
