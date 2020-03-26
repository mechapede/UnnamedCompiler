package inter;

public abstract class IRNode{
    public abstract Object accept(IRVisitor v);
}
