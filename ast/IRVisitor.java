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
            } else if(c == StringType.class ){
                return Temporary.Type.STRING;
            } else if(c == BooleanType.class ){
                return Temporary.Type.BOOL;
            } else if(c == ArrayType.class){
                Class subc = ((ArrayType) t).type.getClass(); 
                if(subc == IntergerType.class) {
                    return Temporary.Type.ARRAY_INT;
                } else if(subc == FloatType.class) {
                    return Temporary.Type.ARRAY_FLOAT;
                } else if(subc == CharType.class) {
                    return Temporary.Type.ARRAY_CHAR;
                } else if(subc == StringType.class ){
                    return Temporary.Type.ARRAY_STRING;
                } else if(subc == BooleanType.class ){
                    return Temporary.Type.ARRAY_BOOL;
                }else{
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

        public Temporary visit(Program p) {
            program = new IRProgram();
            //create function classes first, so calls can ref
            for(int i = 0; i < p.getFunctionCount(); i++) {
                Identifier id = p.getFunction(i).decl.id;
                functions.put(id,new IRFunction(id.id));
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
            return null;
        }

        public Temporary visit(FunctionDeclaration fd) {
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
            Temporary t = current.vars.getArg(convert(fp.type),fp.name.id);
            variables.put(fp.name,t);
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
            Temporary v = current.vars.getTemp(convert(vd.type));
            variables.put(vd.name,v);
            return null;
        }

        public Temporary visit(ExpressionStatement es) {
            es.accept(this);
            return null;
        }

        public Temporary visit(IfStatement i) {
            return null;
        }

        public Temporary visit(WhileStatement ws) {
            return null;
        }

        public Temporary visit(PrintStatement ps) {
            return null;
        }

        public Temporary visit(PrintLnStatement ps) {
            return null;
        }

        public Temporary visit(ReturnStatement r) {
            return null;
        }

        public Temporary visit(AssignmentStatement as) {
            return null;
        }

        public Temporary visit(ArrayAssignment as) {
            return null;
        }

        public Temporary visit(Block b) {
            return null;
        }

        public Temporary visit(ExpressionList el) {
            return null;
        }

        public Temporary visit(EqualityExpression ee) {
            return null;
        }

        public Temporary visit(LessThanExpression ls) {
            return null;
        }

        public Temporary visit(AddExpression ae) {
            return null;
        }

        public Temporary visit(SubtractExpression se) {
            return null;
        }

        public Temporary visit(MultiExpression me) {
            return null;
        }

        public Temporary visit(FunctionCall fc) {
            return null;
        }

        public Temporary visit(ArrayValue av) {
            return null;
        }

        public Temporary visit(IdentifierValue iv) {
            return null; //not used
        }

        public Temporary visit(StringLiteral sl) {
            Temporary t = current.vars.getTemp(Temporary.Type.STRING);
            return t;
        }

        public Temporary visit(IntergerLiteral il) {
            Temporary t = current.vars.getTemp(Temporary.Type.INT);
            return t;
        }

        public Temporary visit(CharacterLiteral cl) {
            Temporary t = current.vars.getTemp(Temporary.Type.CHAR);
            return t;
        }

        public Temporary visit(FloatLiteral fl) {
            Temporary t = current.vars.getTemp(Temporary.Type.FLOAT);
            return t;
        }

        public Temporary visit(BooleanLiteral bl) {
            Temporary t = current.vars.getTemp(Temporary.Type.BOOL);
            IRConstantBool b = new IRConstantBool(bl.value);
            current.addStatement(new IRAssignment(t,b));
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

