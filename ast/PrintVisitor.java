package ast;

public class PrintVisitor extends Visitor{
    int indent;
    String out;

    public PrintVisitor(){
        indent = 0;
        out = "";
    }

    public String getOut(){
        return out;
    }

    private String getIndent(){
        String spacing = "";
        for(int i = 0; i < indent; i++){
            spacing += "    ";
        }
        return spacing;
    }

    public Object visit(Program p){
        if( p.getFunctionCount() > 0 ){
            p.getFunction(0).accept(this);
        }
        for(int i = 1; i < p.getFunctionCount(); i++){
            out += "\n";
            p.getFunction(i).accept(this);
        }
        return null;
    }

    public Object visit(Function p){
        p.decl.accept(this);
        indent += 1;
        out += "\n{\n";
        p.body.accept(this);
        out += "}\n";
        indent -= 1;
        return null;
    }
    public Object visit(FunctionDeclaration fd){
        fd.type.accept(this);
        out += " ";
        fd.id.accept(this);
        out += " (";
        if( fd.pl != null){ //for no arg case
            fd.pl.accept(this);
        }
        out += ")";
        return null;
    }

    public Object visit(FormalParameterList fpl){
        if(fpl.getParameterCount() > 0){
            fpl.getParameter(0).accept(this);
        }
        for(int i = 1; i < fpl.getParameterCount(); i++){
            out += ", ";
            fpl.getParameter(i).accept(this);
        }
        return null;
    }

    public Object visit(FormalParameter fp){
        fp.type.accept(this);
        out += " ";
        fp.identifier.accept(this);
        return null;
    }

    public Object visit(FunctionBody fb){
        for(int i = 0; i < fb.getDeclarationCount(); i++){
            out += getIndent();
            fb.getDeclaration(i).accept(this);
            out += "\n";
        }
        for(int i = 0; i < fb.getStatementCount(); i++){
            out += getIndent();
            fb.getStatement(i).accept(this);
            out += "\n";
        }
        //System.out.print(out);
        return null;
    }

    public Object visit(VariableDeclaration vd){
        vd.type.accept(this);
        out += " ";
        vd.identifier.accept(this);
        out += ";";
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
        out += "return ";
        r.expression.accept(this);
        out += ";";
        return null;
    }

    public Object visit(AssignmentStatement as){
        as.identifier.accept(this);
        out += "=";
        as.expression.accept(this);
        out += ";";
        return null;
    }

    public Object visit(ArrayAssignment as){
        as.identifier.accept(this);
        out += "[";
        as.index.accept(this);
        out += "]=";
        as.expression.accept(this);
        out += ";";
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
        out += iv.identifier.accept(this);
        return null;
    }

    public Object visit(StringLiteral sl){
        out += "\"" + sl.val + "\"";
        return null;
    }

    public Object visit(IntergerLiteral il){
        out += il.val+"";
        return null;
    }

    public Object visit(CharacterLiteral cl){
        out += "'" + cl.val + "'";
        return null;
    }

    public Object visit(FloatLiteral fl){
        out += fl.val+"";
        return null;
    }

    public Object visit(BooleanLiteral bl){
        out += bl.val+"";
        return null;
    }

    public Object visit(ArrayType at){
        at.type.accept(this);
        out += "[";
        at.size.accept(this);
        out += "]";
        return null;
    }

    public Object visit(IntergerType it){
        out += "int";
        return null;
    }

    public Object visit(FloatType ft){
        out += "float";
        return null;
    }

    public Object visit(CharType ct){
        out += "char";
        return null;
    }

    public Object visit(StringType st){
        out += "string";
        return null;
    }

    public Object visit(BooleanType bt){
        out += "boolean";
        return null;
    }

    public Object visit(VoidType vt){
        out += "void";
        return null;
    }

    public Object visit(Identifier i){
        out += i.id;
        return null;
    }
}
