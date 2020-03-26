package inter;

public interface IRVisitor{
    public abstract Object visit(IRProgram p);
    public abstract Object visit(IRFunction f);
    public abstract Object visit(IRCall ca);
    public abstract Object visit(IRReturn r);
    public abstract Object visit(IRArrayCreate ac);
    public abstract Object visit(IRArrayValue av);
    public abstract Object visit(IRArrayAssignment aa);
    public abstract Object visit(IRAssignment as);
    public abstract Object visit(IRConstantAssignment ca);
    public abstract Object visit(IRConstantBool b);
    public abstract Object visit(IRConstantChar c);
    public abstract Object visit(IRConstantFloat f);
    public abstract Object visit(IRConstantInt i);
    public abstract Object visit(IRConstantString s);
    public abstract Object visit(IRIfGoto ifg);
    public abstract Object visit(IRGoto f);
    public abstract Object visit(IRLabel l);
    public abstract Object visit(IRUnaryOp u);
    public abstract Object visit(IRBinaryOp b);
    public abstract Object visit(IRPrint p);
    public abstract Object visit(IRPrintln pl);
}
