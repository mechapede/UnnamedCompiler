/* Contains the function defintions for all visitors for the callbacks */
package ast;

public abstract class Visitor {
    public abstract Object visit(Program p);
    public abstract Object visit(Function p);
    public abstract Object visit(FunctionDeclaration fd);
    public abstract Object visit(FormalParameterList fpl);
    public abstract Object visit(FormalParameter fp);
    public abstract Object visit(FunctionBody fb);
    public abstract Object visit(VariableDeclaration vd);
    public abstract Object visit(ExpressionStatement el);
    public abstract Object visit(IfStatement i);
    public abstract Object visit(WhileStatement ws);
    public abstract Object visit(PrintStatement ps);
    public abstract Object visit(PrintLnStatement ps);
    public abstract Object visit(ReturnStatement r);
    public abstract Object visit(AssignmentStatement as);
    public abstract Object visit(ArrayAssignment as);
    public abstract Object visit(Block b);
    public abstract Object visit(ExpressionList el);
    public abstract Object visit(EqualityExpression ee);
    public abstract Object visit(LessThanExpression ls);
    public abstract Object visit(AddExpression ae);
    public abstract Object visit(SubtractExpression se);
    public abstract Object visit(MultiExpression me);
    public abstract Object visit(FunctionCall fc);
    public abstract Object visit(ArrayValue av);
    public abstract Object visit(IdentifierValue iv);
    public abstract Object visit(StringLiteral sl);
    public abstract Object visit(IntergerLiteral il);
    public abstract Object visit(CharacterLiteral cl);
    public abstract Object visit(FloatLiteral fl);
    public abstract Object visit(BooleanLiteral bl);
    public abstract Object visit(ArrayType it);
    public abstract Object visit(IntergerType it);
    public abstract Object visit(FloatType ft);
    public abstract Object visit(CharType ct);
    public abstract Object visit(StringType st);
    public abstract Object visit(BooleanType bt);
    public abstract Object visit(VoidType vt);
    public abstract Object visit(Identifier i);
}
