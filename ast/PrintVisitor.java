/* Returns a string representation of the parse tree */
package ast;

public class PrintVisitor extends Visitor {
    int indent;
    String out;

    public PrintVisitor() {
        indent = 0;
        out = "";
    }

    public String getOut() {
        return out;
    }

    private String getIndent() {
        String spacing = "";
        for(int i = 0; i < indent; i++) {
            spacing += "    ";
        }
        return spacing;
    }

    public Object visit(Program p) {
        if(p.getFunctionCount() > 0) {
            p.getFunction(0).accept(this);
        }
        for(int i = 1; i < p.getFunctionCount(); i++) {
            out += "\n";
            p.getFunction(i).accept(this);
        }
        return null;
    }

    public Object visit(Function p) {
        p.decl.accept(this);
        indent += 1;
        out += "\n{\n";
        p.body.accept(this);
        out += "}\n";
        indent -= 1;
        return null;
    }

    public Object visit(FunctionDeclaration fd) {
        fd.type.accept(this);
        out += " ";
        fd.id.accept(this);
        out += " (";
        if(fd.pl != null) { //for no arg case
            fd.pl.accept(this);
        }
        out += ")";
        return null;
    }

    public Object visit(FormalParameterList fpl) {
        if(fpl.getParameterCount() > 0) {
            fpl.getParameter(0).accept(this);
        }
        for(int i = 1; i < fpl.getParameterCount(); i++) {
            out += ", ";
            fpl.getParameter(i).accept(this);
        }
        return null;
    }

    public Object visit(FormalParameter fp) {
        fp.type.accept(this);
        out += " ";
        fp.id.accept(this);
        return null;
    }

    public Object visit(FunctionBody fb) {
        for(int i = 0; i < fb.getDeclarationCount(); i++) {
            out += getIndent();
            fb.getDeclaration(i).accept(this);
            out += "\n";
        }
        for(int i = 0; i < fb.getStatementCount(); i++) {
            out += getIndent();
            fb.getStatement(i).accept(this);
            out += "\n";
        }
        return null;
    }

    public Object visit(VariableDeclaration vd) {
        vd.type.accept(this);
        out += " ";
        vd.id.accept(this);
        out += ";";
        return null;
    }

    public Object visit(ExpressionStatement es) {
        es.expression.accept(this);
        out += ";";
        return null;
    }

    public Object visit(IfStatement i) {
        out += "if (";
        i.cond.accept(this);
        out += ")\n";
        i.block.accept(this);
        if(i.elseblock != null) {
            out += getIndent() + "\n" + getIndent() + "else\n";
            i.elseblock.accept(this);
        }
        return null;
    }

    public Object visit(WhileStatement ws) {
        out += "while (";
        ws.cond.accept(this);
        out += ")\n";
        ws.block.accept(this);
        return null;
    }

    public Object visit(PrintStatement ps) {
        out += "print ";
        ps.expression.accept(this);
        out += ";";
        return null;
    }

    public Object visit(PrintLnStatement ps) {
        out += "println ";
        ps.expression.accept(this);
        out += ";";
        return null;
    }

    public Object visit(ReturnStatement r) {
        out += "return ";
        if(r.expression != null) r.expression.accept(this);
        out += ";";
        return null;
    }

    public Object visit(AssignmentStatement as) {
        as.id.accept(this);
        out += "=";
        as.value.accept(this);
        out += ";";
        return null;
    }

    public Object visit(ArrayAssignment as) {
        as.id.accept(this);
        out += "[";
        as.index.accept(this);
        out += "]=";
        as.value.accept(this);
        out += ";";
        return null;
    }

    public Object visit(Block b) {
        out += getIndent() + "{\n";
        indent += 1;
        for(int i = 0; i < b.getStatementCount(); i++) {
            out += getIndent();
            b.getStatement(i).accept(this);
            out += "\n";
        }
        indent -= 1;
        out += getIndent() + "}";
        return null;
    }

    public Object visit(ExpressionList el) {
        if(el.getExpressionCount() > 0) {
            el.getExpression(0).accept(this);
        }
        for(int i = 1; i < el.getExpressionCount(); i++) {
            out += ", ";
            el.getExpression(i).accept(this);
        }
        return null;
    }

    public Object visit(EqualityExpression ee) {
        out += "(";
        ee.e1.accept(this);
        out += "==";
        ee.e2.accept(this);
        out += ")";
        return null;
    }

    public Object visit(LessThanExpression ls) {
        out += "(";
        ls.e1.accept(this);
        out += "<";
        ls.e2.accept(this);
        out += ")";
        return null;
    }

    public Object visit(AddExpression ae) {
        out += "(";
        ae.e1.accept(this);
        out += "+";
        ae.e2.accept(this);
        out += ")";
        return null;
    }

    public Object visit(SubtractExpression se) {
        out += "(";
        se.e1.accept(this);
        out += "-";
        se.e2.accept(this);
        out += ")";
        return null;
    }

    public Object visit(MultiExpression me) {
        out += "(";
        me.e1.accept(this);
        out += "*";
        me.e2.accept(this);
        out += ")";
        return null;
    }

    public Object visit(FunctionCall fc) {
        fc.name.accept(this);
        out += "(";
        if(fc.args != null) {
            fc.args.accept(this);
        }
        out += ")";
        return null;
    }

    public Object visit(ArrayValue av) {
        av.name.accept(this);
        out += "[";
        av.index.accept(this);
        out += "]";
        return null;
    }

    public Object visit(IdentifierValue iv) {
        iv.id.accept(this);
        return null;
    }

    public Object visit(StringLiteral sl) {
        out += "\"" + sl.value + "\"";
        return null;
    }

    public Object visit(IntergerLiteral il) {
        out += il.value+"";
        return null;
    }

    public Object visit(CharacterLiteral cl) {
        out += "'" + cl.value + "'";
        return null;
    }

    public Object visit(FloatLiteral fl) {
        out += fl.value+"";
        return null;
    }

    public Object visit(BooleanLiteral bl) {
        out += bl.value+"";
        return null;
    }

    public Object visit(ArrayType at) {
        at.type.accept(this);
        out += "[";
        at.size.accept(this);
        out += "]";
        return null;
    }

    public Object visit(IntergerType it) {
        out += "int";
        return null;
    }

    public Object visit(FloatType ft) {
        out += "float";
        return null;
    }

    public Object visit(CharType ct) {
        out += "char";
        return null;
    }

    public Object visit(StringType st) {
        out += "string";
        return null;
    }

    public Object visit(BooleanType bt) {
        out += "boolean";
        return null;
    }

    public Object visit(VoidType vt) {
        out += "void";
        return null;
    }

    public Object visit(Identifier i) {
        out += i.id;
        return null;
    }
}
