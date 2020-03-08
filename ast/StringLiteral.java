package ast;

public class StringLiteral extends Expression {
    public String value;

    public StringLiteral(String value, int tokenline, int tokenchar) {
        super(tokenline,tokenchar);
        this.value = value;
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }

}
