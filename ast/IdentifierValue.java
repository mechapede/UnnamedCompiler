package ast;

public class IdentifierValue extends Expression {
    public Identifier id;

    public IdentifierValue(Identifier id,int tokenline, int tokenchar) {
        super(tokenline,tokenchar);
        this.id = id;
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }

}
