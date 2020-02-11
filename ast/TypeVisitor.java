/* Checks the syntax for types and reference validity*/
package ast;

import java.util.TreeMap;
import java.util.ArrayList;

public class TypeVisitor extends Visitor {
        private TreeMap<Identifier,Function> functions;
        private TreeMap<Identifier,Type> variables;
        private Function current;

        private ArrayList<ErrorMessage> violations;
        private static class ErrorMessage {
                int tokenline;
                int tokenchar;
                String message;
                private ErrorMessage(int tokenline, int tokenchar, String message) {
                    this.tokenline = tokenline;
                    this.tokenchar = tokenchar;
                    this.message = message;
                }

                public String toString() {
                    return tokenline + ":" + tokenchar + " error - "  + message;
                }
        }

        public TypeVisitor() {
            functions = new TreeMap<Identifier,Function>();
            variables = new TreeMap<Identifier,Type>();
            violations = new ArrayList<ErrorMessage>();
        }

        public boolean errors() {
            return violations.size() > 0;
        }

        public String dumpErrors() {
            String buff = "";
            for(int i = 0; i < violations.size(); i++) {
                buff += violations.get(i) + "\n";
            }
            return buff;
        }

        public Type visit(Program p) {
            for(int i = 0; i < p.getFunctionCount(); i++) {
                Identifier fid = p.getFunction(i).decl.id;
                Function other = functions.get(fid);
                if( other != null ){
                    ErrorMessage e = new ErrorMessage(fid.tokenline, fid.tokenchar, "Function \"" +  fid + 
                                                      "\" redeclared, originaly declared at " + other.tokenline + ":" + other.tokenchar);
                    violations.add(e);   
                }
                functions.put(fid,p.getFunction(i));
            }
            Function main = functions.get(new Identifier("main"));
            if(main == null) {
                ErrorMessage e = new ErrorMessage(p.tokenline, p.tokenchar, "missing main function in program.");
                violations.add(e);
            }else if ( main.decl.type.getClass() != VoidType.class ){
                ErrorMessage e = new ErrorMessage(main.tokenline, main.tokenchar, "main function return type must be void");
                violations.add(e);
            }else if (main.decl.pl != null ){
                ErrorMessage e = new ErrorMessage(main.tokenline, main.tokenchar, "main function must take no arguements.");
                violations.add(e);
            } //TODO: voidtype arrays

            for(int i = 0; i < p.getFunctionCount(); i++) {
                p.getFunction(i).accept(this);
                variables.clear(); //TODO: use environment type if needed
            }
            return null;
        }

        public Type visit(Function f) {
            current = f;
            f.decl.accept(this);
            f.body.accept(this);
            return null;
        }

        public Type visit(FunctionDeclaration fd) {
            if(fd.pl != null) {
                fd.pl.accept(this);
            }
            return null;
        }

        public Type visit(FormalParameterList fpl) {
            for(int i = 0; i < fpl.getParameterCount(); i++) {
                fpl.getParameter(i).accept(this);
            }
            return null;
        }

        public Type visit(FormalParameter fp) {
            if(variables.get(fp.name) != null) {
                ErrorMessage e = new ErrorMessage(fp.tokenline, fp.tokenchar, "formal parameter \"" + fp.name + "\" redefined");
                violations.add(e);
            } else if(fp.type.getClass() == VoidType.class) {
                ErrorMessage e = new ErrorMessage(fp.tokenline, fp.tokenchar, "void type used for parameter \"" + fp.name + "\"");
                violations.add(e);
            } else if (fp.type.getClass() == ArrayType.class && ((ArrayType) fp.type).type.getClass() == VoidType.class){
                ErrorMessage e = new ErrorMessage(fp.tokenline, fp.tokenchar, "void type used for parameter array " + "\"" + fp.name + "\"");
                violations.add(e);
            }else { //TODO VOIDTYPE ARRAYs
                variables.put(fp.name,fp.type);
            }
            return null;
        }

        public Type visit(FunctionBody fb) {
            for(int i = 0; i < fb.getDeclarationCount(); i++) {
                fb.getDeclaration(i).accept(this);
            }
            for(int i = 0; i < fb.getStatementCount(); i++) {
                fb.getStatement(i).accept(this);
            }
            return null;
        }

        public Type visit(VariableDeclaration vd) {
            Type other = variables.get(vd.name);
            if(other != null) {
                ErrorMessage e = new ErrorMessage(vd.tokenline, vd.tokenchar, "variable \"" + vd.name + "\" redefind, originaly defined at " 
                                                                              + other.tokenline + ":" + other.tokenchar );
                violations.add(e);
            } else if(vd.type.getClass() == VoidType.class) {
                ErrorMessage e = new ErrorMessage(vd.tokenline, vd.tokenchar, "Void type used for variable \"" + vd.name + "\"");
                violations.add(e);
            } else if (vd.type.getClass() == ArrayType.class && ((ArrayType) vd.type).type.getClass() == VoidType.class) {
                ErrorMessage e = new ErrorMessage(vd.tokenline, vd.tokenchar, "void type used for array " + "\"" + vd.name + "\"");
                violations.add(e);
            } else {
                variables.put(vd.name,vd.type);
            }
            return null;
        }

        public Type visit(ExpressionStatement es) {
            es.expression.accept(this);
            return null;
        }

        public Type visit(IfStatement i) {
            Type cond = (Type) i.cond.accept(this);
            if(cond.getClass() != BooleanType.class) {
                ErrorMessage e = new ErrorMessage(i.tokenline, i.tokenchar, "Expression in if statement must return a boolean.");
                violations.add(e);
            }
            i.block.accept(this);
            if(i.elseblock != null) {
                i.elseblock.accept(this);
            }
            return null;
        }

        public Type visit(WhileStatement ws) {
            Type cond = (Type) ws.cond.accept(this);
            if(cond.getClass() != BooleanType.class) {
                ErrorMessage e = new ErrorMessage(ws.tokenline, ws.tokenchar, "Expression in while statement must return a boolean.");
                violations.add(e);
            }
            ws.block.accept(this);
            return null;
        }

        public Type visit(PrintStatement ps) {
            Type m = (Type) ps.expression.accept(this);
            if(m.getClass() == VoidType.class) {
                ErrorMessage e = new ErrorMessage(ps.tokenline, ps.tokenchar, "Print cannot be used with void type.");
                violations.add(e);
            }
            return null;
        }

        public Type visit(PrintLnStatement ps) {
            Type m = (Type) ps.expression.accept(this);
            if(m.getClass() == VoidType.class) {
                ErrorMessage e = new ErrorMessage(ps.tokenline, ps.tokenchar, "Print cannot be used with void type.");
                violations.add(e);
            }
            return null;
        }

        public Type visit(ReturnStatement r) {
            Type ret = (Type) r.expression.accept(this);
            if(!ret.equals(current.decl.type)) {
                ErrorMessage e = new ErrorMessage(r.tokenline, r.tokenchar, "Return statement does not match function return type.");
                violations.add(e);
            }
            return null;
        }

        public Type visit(AssignmentStatement as) {
            Type t = variables.get(as.name);
            Type got = (Type) as.value.accept(this);
            if(t == null) {
                ErrorMessage e = new ErrorMessage(as.tokenline, as.tokenchar, "assignment statement to undeclared variable \"" + as.name + "\"");
                violations.add(e);
            } else if(!t.equals(got)) {
                ErrorMessage e = new ErrorMessage(as.tokenline, as.tokenchar, "assignment value of \"" + as.name
                                                    + "\" does not match expected type expected:" + t + " got: " + got);
                violations.add(e);
            }
            return null;
        }

        public Type visit(ArrayAssignment as) {
            Type t = variables.get(as.name);
            Type got = (Type) as.value.accept(this);
            Type index = (Type) as.index.accept(this);
            if(t == null) {
                ErrorMessage e = new ErrorMessage(as.tokenline, as.tokenchar,  "variable \"" + as.name + "\" in array assignment is not declared");
                violations.add(e);
            } else if( t.getClass() != ArrayType.class ) {
                ErrorMessage e = new ErrorMessage(as.tokenline, as.tokenchar, "Variable is not a array!");
                violations.add(e);
            } else {
                ArrayType tmp = (ArrayType) t;
                if(tmp.type.getClass() != got.getClass()) {
                    ErrorMessage e = new ErrorMessage(as.tokenline, as.tokenchar, "Types of assignment does not match!");
                    violations.add(e);
                }
            }
            if(index.getClass() != IntergerType.class) {
                ErrorMessage e = new ErrorMessage(as.tokenline, as.tokenchar, "Types of index is not int!");
                violations.add(e);
            }
            return null;
        }

        public Type visit(Block b) {
            for(int i = 0; i < b.getStatementCount(); i++) {
                b.getStatement(i).accept(this);
            }
            return null;
        }

        public Type visit(ExpressionList el) {
            for(int i = 0; i < el.getExpressionCount(); i++) {
                el.getExpression(i).accept(this);
            }
            return null;
        }

        public Type visit(EqualityExpression ee) {
            Type t1 = (Type) ee.e1.accept(this);
            Type t2 = (Type) ee.e2.accept(this);
            if(!t1.equals(t2)) {
                ErrorMessage e = new ErrorMessage(ee.tokenline, ee.tokenchar, "Equality type mismatch failed");
                violations.add(e);
            }
            if(t1.getClass() == VoidType.class || t2.getClass() == VoidType.class) {
                ErrorMessage e = new ErrorMessage(ee.tokenline, ee.tokenchar, "Equality cannot be used with void value.");
                violations.add(e);
            }
            return new BooleanType(-1,-1);
        }

        public Type visit(LessThanExpression ls) {
            Type t1 = (Type) ls.e1.accept(this);
            Type t2 = (Type) ls.e2.accept(this);
            if(!t1.equals(t2)) {
                ErrorMessage e = new ErrorMessage(ls.tokenline, ls.tokenchar, "Less type mismatch failed");
                violations.add(e);
            }
            if(t1.getClass() == VoidType.class || t2.getClass() == VoidType.class) {
                ErrorMessage e = new ErrorMessage(ls.tokenline, ls.tokenchar, "Less than cannot be used with void value.");
                violations.add(e);
            }
            return new BooleanType(-1,-1);
        }

        public Type visit(AddExpression ae) {
            Type t1 = (Type) ae.e1.accept(this);
            Type t2 = (Type) ae.e2.accept(this);
            if(!t1.equals(t2)) {
                ErrorMessage e = new ErrorMessage(ae.tokenline, ae.tokenchar, "Add type mismatch failed");
                violations.add(e);
            }

            if(t1.getClass() == VoidType.class || t2.getClass() == VoidType.class) {
                ErrorMessage e = new ErrorMessage(ae.tokenline, ae.tokenchar, "Add cannot be used with void value.");
                violations.add(e);
            }
            if(t1.getClass() == BooleanType.class || t2.getClass() == BooleanType.class) {
                ErrorMessage e = new ErrorMessage(ae.tokenline, ae.tokenchar, "Add cannot be used with Boolean value.");
                violations.add(e);
            }
            return t1;
        }

        public Type visit(SubtractExpression se) {
            Type t1 = (Type) se.e1.accept(this);
            Type t2 = (Type) se.e2.accept(this);
            if(!t1.equals(t2)) {
                ErrorMessage e = new ErrorMessage(se.tokenline, se.tokenchar, "Substract types do not match");
                violations.add(e);
            }
            if(t1.getClass() == VoidType.class || t2.getClass() == VoidType.class) {
                ErrorMessage e = new ErrorMessage(se.tokenline, se.tokenchar, "Subtract cannot be used with void value.");
                violations.add(e);
            }
            if(t1.getClass() == BooleanType.class || t2.getClass() == BooleanType.class) {
                ErrorMessage e = new ErrorMessage(se.tokenline, se.tokenchar, "Substract cannot be used with Boolean value.");
                violations.add(e);
            }
            if(t1.getClass() == StringType.class || t2.getClass() == StringType.class) {
                ErrorMessage e = new ErrorMessage(se.tokenline, se.tokenchar, "Substart cannot be used with String value.");
                violations.add(e);
            }
            return t1;
        }

        public Type visit(MultiExpression me) {
            Type t1 = (Type) me.e1.accept(this);
            Type t2 = (Type) me.e2.accept(this);
            if(!t1.equals(t2)) {
                ErrorMessage e = new ErrorMessage(me.tokenline, me.tokenchar, "Multiply types do not match");
                violations.add(e);
            }
            if(t1.getClass() == VoidType.class || t2.getClass() == VoidType.class) {
                ErrorMessage e = new ErrorMessage(me.tokenline, me.tokenchar, "Multiply cannot be used with void value.");
                violations.add(e);
            }
            if(t1.getClass() == BooleanType.class || t2.getClass() == BooleanType.class) {
                ErrorMessage e = new ErrorMessage(me.tokenline, me.tokenchar, "Multiply cannot be used with Boolean value.");
                violations.add(e);
            }
            if(t1.getClass() == StringType.class || t2.getClass() == StringType.class) {
                ErrorMessage e = new ErrorMessage(me.tokenline, me.tokenchar, "Multiply cannot be used with String value.");
                violations.add(e);
            }
            if(t1.getClass() == CharType.class || t2.getClass() == CharType.class) {
                ErrorMessage e = new ErrorMessage(me.tokenline, me.tokenchar, "Multiply cannot be used with Char value.");
                violations.add(e);
            }
            return t1;
        }

        public Type visit(FunctionCall fc) {
            Function f = functions.get(fc.name);
            if((f.decl.pl != null &&  fc.args == null) || (f.decl.pl == null && fc.args != null)) {
                ErrorMessage e = new ErrorMessage(fc.tokenline, fc.tokenchar, "Parameters length do not match!");
                violations.add(e);
            }   else if(fc.args != null) {
                if(f.decl.pl.getParameterCount() != fc.args.getExpressionCount()) {
                    ErrorMessage e = new ErrorMessage(fc.tokenline, fc.tokenchar, "Parameters length do not match!");
                    violations.add(e);
                } else {
                    for(int i = 0; i < f.decl.pl.getParameterCount(); i++) {
                        Type expected = f.decl.pl.getParameter(i).type;
                        Type got = (Type) fc.args.getExpression(i).accept(this);
                        if(!expected.equals(got)) {
                            ErrorMessage e = new ErrorMessage(fc.tokenline, fc.tokenchar, "Parameters Type" + i +  " does not match!");
                            violations.add(e);
                        }
                    }
                }
            }
            return f.decl.type;
        }

        public Type visit(ArrayValue av) {
            Type t = (Type) variables.get(av.name);
            if(t == null) {
                ErrorMessage e = new ErrorMessage(av.tokenline, av.tokenchar, "Varriable does not exists"); //TODO: dump id
                violations.add(e);
            }
            if(t.getClass() != ArrayType.class) {
                ErrorMessage e = new ErrorMessage(av.tokenline, av.tokenchar, "Varriable is not an array!"); //TODO: dump id
                violations.add(e);
            } else {
                Type index = (Type) av.index.accept(this);
                if(index.getClass() != IntergerType.class) {
                    ErrorMessage e = new ErrorMessage(av.tokenline, av.tokenchar, "Index is not integer type!");
                    violations.add(e);
                }
            }
            return t;
        }

        public Type visit(IdentifierValue iv) {
            Type t = (Type) variables.get(iv.name);
            if(t == null) {
                ErrorMessage e = new ErrorMessage(iv.tokenline, iv.tokenchar, "Varriable does not exists"); //TODO: dump id
                violations.add(e);
            }
            return t;
        }

        public Type visit(StringLiteral sl) {
            return new StringType(sl.tokenline,sl.tokenchar);
        }

        public Type visit(IntergerLiteral il) {
            return new IntergerType(il.tokenline,il.tokenchar);
        }

        public Type visit(CharacterLiteral cl) {
            return new CharType(cl.tokenline,cl.tokenchar);
        }

        public Type visit(FloatLiteral fl) {
            return new FloatType(fl.tokenline,fl.tokenchar);
        }

        public Type visit(BooleanLiteral bl) {
            return new BooleanType(bl.tokenline,bl.tokenchar);
        }

        public Type visit(ArrayType at) {
            return null; //not needed
        }

        public Type visit(IntergerType it) {
            return null; //not needed
        }

        public Type visit(FloatType ft) {
            return null; //not needed
        }

        public Type visit(CharType ct) {
            return null; //not needed
        }

        public Type visit(StringType st) {
            return null; //not needed
        }

        public Type visit(BooleanType bt) {
            return null; //not needed
        }

        public Type visit(VoidType vt) {
            return null; //not needed
        }

        public Type visit(Identifier i) {
            return null; //not needed
        }
}
