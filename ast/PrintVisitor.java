package ast;

public class PrintVisitor extends Visitor{
    int indent;

    public PrintVisitor(){
        indent = 0;
    }

    public Object visit(Program p){
        for(int i = 0; i < p.getFunctionCount(); i++){
            Function f = p.getFunction(i);
            f.accept(this);
        }
        return null;
    }

    public Object visit(Function p){
        indent += 1;
        p.decl.accept(this); //TODO: use getter method
        //indent -= 1;
        return null;
    }
    public Object visit(FunctionDeclaration fd){
        fd.id.accept(this);
        return null;
    }

    public Object visit(FormalParameterList fpl){
        return null;
    }

    public Object visit(FormalParameter fp){
        return null;
    }

    public Object visit(FunctionBody fb){
        return null;
    }

    public Object visit(VariableDeclaration vd){
        return null;
    }

    public Object visit(IfStatement i){
        return null;
    }

    public Object visit(WhileStatement ws){
        return null;
    }

    public Object visit(PrintStatement ps){
        return null;
    }

    public Object visit(PrintLnStatement ps){
        return null;
    }

    public Object visit(ReturnStatement r){
        return null;
    }

    public Object visit(AssignmentStatement as){
        return null;
    }

    public Object visit(ArrayAssignment as){
        return null;
    }

    public Object visit(Block b){
        return null;
    }

    public Object visit(ExpressionList el){
        return null;
    }

    public Object visit(EqualityExpression ee){
        return null;
    }

    public Object visit(LessThanExpression ls){
        return null;
    }

    public Object visit(AddExpression ae){
        return null;
    }

    public Object visit(SubtractExpression se){
        return null;
    }

    public Object visit(MultiExpression me){
        return null;
    }

    public Object visit(FunctionCall fc){
        return null;
    }

    public Object visit(ArrayValue av){
        return null;
    }

    public Object visit(IdentifierValue iv){
        return null;
    }

    public Object visit(StringLiteral sl){
        return null;
    }

    public Object visit(IntergerLiteral il){
        return null;
    }

    public Object visit(CharacterLiteral cl){
        return null;
    }

    public Object visit(FloatLiteral fl){
        return null;
    }

    public Object visit(BooleanLiteral bl){
        return null;
    }

    public Object visit(ArrayType it){
        return null;
    }

    public Object visit(IntergerType it){
        return null;
    }

    public Object visit(FloatType ft){
        return null;
    }

    public Object visit(CharType ct){
        return null;
    }

    public Object visit(StringType st){
        return null;
    }

    public Object visit(BooleanType bt){
        return null;
    }

    public Object visit(VoidType vt){
        return null;
    }

    public Object visit(Identifier i){
        String goo = "";
        for( int j =0; j < indent; j++){
            goo += "    ";
        }
        System.out.println(goo + "Funcname: " + i.id);
        return null;
    }
}
