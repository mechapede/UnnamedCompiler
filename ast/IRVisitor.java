/* Checks the syntax for types and reference validity*/
package ast;

import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Collections;

import inter.*;

public class IRVisitor extends Visitor {
    private IRProgram program;
    private IRFunction current;
    private TreeMap<Identifier,Temporary> variables;
    private TreeMap<Identifier,IRFunction> functions;

    private Temporary.Type convert(Type t) {
        Class c = t.getClass();
        if(c == VoidType.class) {
            return Temporary.Type.VOID;
        } else if(c == IntergerType.class) {
            return Temporary.Type.INT;
        } else if(c == FloatType.class) {
            return Temporary.Type.FLOAT;
        } else if(c == CharType.class) {
            return Temporary.Type.CHAR;
        } else if(c == StringType.class) {
            return Temporary.Type.STRING;
        } else if(c == BooleanType.class) {
            return Temporary.Type.BOOL;
        } else if(c == ArrayType.class) {
            Class subc = ((ArrayType) t).type.getClass();
            if(subc == IntergerType.class) {
                return Temporary.Type.ARRAY_INT;
            } else if(subc == FloatType.class) {
                return Temporary.Type.ARRAY_FLOAT;
            } else if(subc == CharType.class) {
                return Temporary.Type.ARRAY_CHAR;
            } else if(subc == StringType.class) {
                return Temporary.Type.ARRAY_STRING;
            } else if(subc == BooleanType.class) {
                return Temporary.Type.ARRAY_BOOL;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public IRVisitor() {
        variables = new TreeMap<Identifier,Temporary>();
        functions = new TreeMap<Identifier,IRFunction>();
    }

    public IRProgram getIRProgram() {
        return program;
    }

    public Temporary visit(Program p) {
        program = new IRProgram();
        for(int i = 0; i < p.getFunctionCount(); i++) { //func ref first
            Identifier id = p.getFunction(i).decl.id;
            IRFunction f = new IRFunction(id.id);
            functions.put(id,f);
            program.addFunction(f);
        }

        for(int i = 0; i < p.getFunctionCount(); i++) {
            p.getFunction(i).accept(this);
            variables.clear();
        }
        return null;
    }

    public Temporary visit(Function f) {
        current = functions.get(f.decl.id);
        f.decl.accept(this);
        f.body.accept(this);
        if(current.getStatement(current.getStatementCount()-1).getClass() != IRReturn.class ) { //should only happen for void functions
            IRReturn rs = new IRReturn(null);
            current.addStatement(rs);
        }
        return null;
    }

    public Temporary visit(FunctionDeclaration fd) {
        current.ret = convert(fd.type);
        if(fd.pl != null) {
            fd.pl.accept(this);
        }
        return null;
    }

    public Temporary visit(FormalParameterList fpl) {
        for(int i = 0; i < fpl.getParameterCount(); i++) {
            fpl.getParameter(i).accept(this);
        }
        return null;
    }

    public Temporary visit(FormalParameter fp) {
        Temporary t = current.vars.getArg(convert(fp.type),fp.id.id);
        variables.put(fp.id,t);
        return null;
    }

    public Temporary visit(FunctionBody fb) {
        for(int i = 0; i < fb.getDeclarationCount(); i++) {
            fb.getDeclaration(i).accept(this);
        }
        for(int i = 0; i < fb.getStatementCount(); i++) {
            fb.getStatement(i).accept(this);
        }
        return null;
    }

    public Temporary visit(VariableDeclaration vd) {
        Temporary v = current.vars.getLocal(convert(vd.type),vd.id.id);
        variables.put(vd.id,v);
        if(vd.type.getClass() == ArrayType.class) {
            ArrayType at = (ArrayType) vd.type;
            IRArrayCreate c = new IRArrayCreate(v,at.size.value);
            current.addStatement(c);
        }

        return null;
    }

    public Temporary visit(ExpressionStatement es) {
        es.expression.accept(this);
        return null;
    }

    public Temporary visit(IfStatement i) {
        Temporary c = (Temporary) i.cond.accept(this);
        Temporary negc = current.vars.getTemp(Temporary.Type.BOOL);
        IRUnaryOp negop = new IRUnaryOp(negc,c,IRUnaryOp.Op.NEGATE);
        IRLabel elselabel = current.vars.getLabel();
        IRLabel endlabel = current.vars.getLabel();
        IRIfGoto compare = new IRIfGoto(negc,elselabel);
        IRGoto endtrue = new IRGoto(endlabel);
        current.addStatement(negop);
        current.addStatement(compare);
        i.block.accept(this);
        current.addStatement(endtrue);
        current.addStatement(elselabel);
        if(i.elseblock != null) i.elseblock.accept(this);
        current.addStatement(endlabel);
        return null;
    }

    public Temporary visit(WhileStatement ws) {
        IRLabel startlabel = current.vars.getLabel();
        IRLabel endlabel = current.vars.getLabel();
        IRGoto checktrue = new IRGoto(startlabel);
        current.addStatement(startlabel);
        Temporary c = (Temporary) ws.cond.accept(this);
        Temporary negc = current.vars.getTemp(Temporary.Type.BOOL);
        IRUnaryOp negop = new IRUnaryOp(negc,c,IRUnaryOp.Op.NEGATE);
        IRIfGoto compare = new IRIfGoto(negc,endlabel);
        current.addStatement(negop);
        current.addStatement(compare);
        ws.block.accept(this);
        current.addStatement(checktrue);
        current.addStatement(endlabel);
        return null;
    }

    public Temporary visit(PrintStatement ps) {
        Temporary contents = (Temporary) ps.expression.accept(this);
        IRPrint p = new IRPrint(contents);
        current.addStatement(p);
        return null;
    }

    public Temporary visit(PrintLnStatement ps) {
        Temporary contents = (Temporary) ps.expression.accept(this);
        IRPrintln p = new IRPrintln(contents);
        current.addStatement(p);
        return null;
    }

    public Temporary visit(ReturnStatement r) {
        IRReturn rs = null;
        if(r.expression == null) {
            rs = new IRReturn(null);
        } else {
            Temporary val = (Temporary) r.expression.accept(this);
            rs = new IRReturn(val);
        }
        current.addStatement(rs);
        return null;
    }

    public Temporary visit(AssignmentStatement as) {
        Temporary val = (Temporary) as.value.accept(this);
        Temporary dest = variables.get(as.id);
        IRAssignment asign = new IRAssignment(dest,val);
        current.addStatement(asign);
        return null;
    }

    public Temporary visit(ArrayAssignment as) {
        Temporary val = (Temporary) as.value.accept(this);
        Temporary index = (Temporary) as.index.accept(this);
        Temporary dest = variables.get(as.id);
        IRArrayAssignment asign = new IRArrayAssignment(dest,val,index);
        current.addStatement(asign);
        return null;
    }

    public Temporary visit(Block b) {
        for(int i = 0; i < b.getStatementCount(); i++) {
            b.getStatement(i).accept(this);
        }
        return null;
    }

    public Temporary visit(ExpressionList el) {
        return null; //not used
    }

    public Temporary visit(EqualityExpression ee) {
        Temporary e1 = (Temporary) ee.e1.accept(this);
        Temporary e2 = (Temporary) ee.e2.accept(this);
        Temporary dest = current.vars.getTemp(Temporary.Type.BOOL);
        IRBinaryOp result = new IRBinaryOp(dest,e1,e2,IRBinaryOp.Op.EQUAL);
        current.addStatement(result);
        return dest;
    }

    public Temporary visit(LessThanExpression ls) {
        Temporary e1 = (Temporary) ls.e1.accept(this);
        Temporary e2 = (Temporary) ls.e2.accept(this);
        Temporary dest = current.vars.getTemp(Temporary.Type.BOOL);
        IRBinaryOp result = new IRBinaryOp(dest,e1,e2,IRBinaryOp.Op.LT);
        current.addStatement(result);
        return dest;
    }

    public Temporary visit(AddExpression ae) {
        Temporary e1 = (Temporary) ae.e1.accept(this);
        Temporary e2 = (Temporary) ae.e2.accept(this);
        Temporary dest = current.vars.getTemp(e1.type);
        IRBinaryOp result = new IRBinaryOp(dest,e1,e2,IRBinaryOp.Op.ADD);
        current.addStatement(result);
        return dest;
    }

    public Temporary visit(SubtractExpression se) {
        Temporary e1 = (Temporary) se.e1.accept(this);
        Temporary e2 = (Temporary) se.e2.accept(this);
        Temporary dest = current.vars.getTemp(e1.type);
        IRBinaryOp result = new IRBinaryOp(dest,e1,e2,IRBinaryOp.Op.SUB);
        current.addStatement(result);
        return dest;
    }

    public Temporary visit(MultiExpression me) {
        Temporary e1 = (Temporary) me.e1.accept(this);
        Temporary e2 = (Temporary) me.e2.accept(this);
        Temporary dest = current.vars.getTemp(e1.type);
        IRBinaryOp result = new IRBinaryOp(dest,e1,e2,IRBinaryOp.Op.MUL);
        current.addStatement(result);
        return dest;
    }

    public Temporary visit(FunctionCall fc) {
        IRFunction f = functions.get(fc.name);
        Temporary dest = null;
        if(f.ret != Temporary.Type.VOID) dest = current.vars.getTemp(f.ret);
        IRCall call = new IRCall(dest,f);
        for(int i = 0; i < fc.args.getExpressionCount(); i++) {
            Temporary t = (Temporary) fc.args.getExpression(i).accept(this);
            call.args.add(t);
        }
        current.addStatement(call);
        return dest;
    }

    public Temporary visit(ArrayValue av) {
        Temporary array = variables.get(av.name);
        Temporary index = (Temporary) av.index.accept(this);
        Temporary t = current.vars.getTemp(array.type.subType());
        IRArrayValue aval = new IRArrayValue(array,index,t);
        current.addStatement(aval);
        return t;
    }

    public Temporary visit(IdentifierValue iv) {
        return variables.get(iv.id);
    }

    public Temporary visit(StringLiteral sl) {
        Temporary t = current.vars.getTemp(Temporary.Type.STRING);
        IRConstantString s = new IRConstantString(sl.value);
        IRConstantAssignment assign = new IRConstantAssignment(t,s);
        current.addStatement(assign);
        return t;
    }

    public Temporary visit(IntergerLiteral il) {
        Temporary t = current.vars.getTemp(Temporary.Type.INT);
        IRConstantInt i = new IRConstantInt(il.value);
        IRConstantAssignment assign = new IRConstantAssignment(t,i);
        current.addStatement(assign);
        return t;
    }

    public Temporary visit(CharacterLiteral cl) {
        Temporary t = current.vars.getTemp(Temporary.Type.CHAR);
        IRConstantChar c = new IRConstantChar(cl.value);
        IRConstantAssignment assign = new IRConstantAssignment(t,c);
        current.addStatement(assign);
        return t;
    }

    public Temporary visit(FloatLiteral fl) {
        Temporary t = current.vars.getTemp(Temporary.Type.FLOAT);
        IRConstantFloat f = new IRConstantFloat(fl.value);
        IRConstantAssignment assign = new IRConstantAssignment(t,f);
        current.addStatement(assign);
        return t;
    }

    public Temporary visit(BooleanLiteral bl) {
        Temporary t = current.vars.getTemp(Temporary.Type.BOOL);
        IRConstantBool b = new IRConstantBool(bl.value);
        IRConstantAssignment assign = new IRConstantAssignment(t,b);
        current.addStatement(assign);
        return t;
    }

    public Temporary visit(ArrayType at) {
        return null; //not used
    }

    public Temporary visit(IntergerType it) {
        return null; //not used
    }

    public Temporary visit(FloatType ft) {
        return null; //not used
    }

    public Temporary visit(CharType ct) {
        return null; //not used
    }

    public Temporary visit(StringType st) {
        return null; //not used
    }

    public Temporary visit(BooleanType bt) {
        return null; //not used
    }

    public Temporary visit(VoidType vt) {
        return null; //not used
    }

    public Temporary visit(Identifier i) {
        return null; //not used
    }
}

