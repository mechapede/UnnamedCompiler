/* Checks the syntax for types and reference validity*/
package ast;

import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Collections;

public class TypeVisitor extends Visitor {
  private TreeMap<Identifier,Function> functions;
  private TreeMap<Identifier,Type> variables;
  private Function current;

  private ArrayList<ErrorMessage> violations;
  private static class ErrorMessage implements Comparable<ErrorMessage> {
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

    public int compareTo(ErrorMessage other) {
      if(this.tokenline < other.tokenline) {
        return -1;
      }
      if(this.tokenline > other.tokenline) {
        return 1;
      }
      if(this.tokenchar < other.tokenchar) {
        return -1;
      }
      if(this.tokenchar > other.tokenchar) {
        return 1;
      } else {
        return 0;
      }
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
    Collections.sort(violations);
    for(int i = 0; i < violations.size(); i++) {
      buff += violations.get(i) + "\n";
    }
    return buff;
  }

  public Type visit(Program p) {
    for(int i = 0; i < p.getFunctionCount(); i++) {
      Identifier fid = p.getFunction(i).decl.id;
      Function other = functions.get(fid);
      if(other != null) {
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
    } else if(main.decl.type.getClass() != VoidType.class) {
      ErrorMessage e = new ErrorMessage(main.tokenline, main.tokenchar, "main function return type must be void");
      violations.add(e);
    } else if(main.decl.pl != null) {
      ErrorMessage e = new ErrorMessage(main.tokenline, main.tokenchar, "main function must take no arguements.");
      violations.add(e);
    }

    for(int i = 0; i < p.getFunctionCount(); i++) {
      p.getFunction(i).accept(this);
      variables.clear(); //TODO: change to use environment
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
    if(fd.type.getClass() == ArrayType.class && ((ArrayType) fd.type).type.getClass() == VoidType.class) {
      ErrorMessage e = new ErrorMessage(fd.tokenline, fd.tokenchar, "void array type used for function " + "\"" + fd.id + "\"");
      violations.add(e);
    }
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
    if(variables.get(fp.id) != null) {
      ErrorMessage e = new ErrorMessage(fp.tokenline, fp.tokenchar, "formal parameter \"" + fp.id + "\" redefined");
      violations.add(e);
    } else if(fp.type.getClass() == VoidType.class) {
      ErrorMessage e = new ErrorMessage(fp.tokenline, fp.tokenchar, "void type used for parameter \"" + fp.id + "\"");
      violations.add(e);
    } else if(fp.type.getClass() == ArrayType.class && ((ArrayType) fp.type).type.getClass() == VoidType.class) {
      ErrorMessage e = new ErrorMessage(fp.tokenline, fp.tokenchar, "void type used for parameter array " + "\"" + fp.id + "\"");
      violations.add(e);
    } else {
      variables.put(fp.id,fp.type);
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
    Type other = variables.get(vd.id);
    if(other != null) {
      ErrorMessage e = new ErrorMessage(vd.tokenline, vd.tokenchar, "variable \"" + vd.id + "\" redefind, originaly defined at "
                                        + other.tokenline + ":" + other.tokenchar);
      violations.add(e);
    } else if(vd.type.getClass() == VoidType.class) {
      ErrorMessage e = new ErrorMessage(vd.tokenline, vd.tokenchar, "Void type used for variable \"" + vd.id + "\"");
      violations.add(e);
    } else if(vd.type.getClass() == ArrayType.class && ((ArrayType) vd.type).type.getClass() == VoidType.class) {
      ErrorMessage e = new ErrorMessage(vd.tokenline, vd.tokenchar, "void type used for array " + "\"" + vd.id + "\"");
      violations.add(e);
    } else {
      variables.put(vd.id,vd.type);
    }
    return null;
  }

  public Type visit(ExpressionStatement es) {
    es.expression.accept(this);
    return null;
  }

  public Type visit(IfStatement i) {
    Type cond = (Type) i.cond.accept(this);
    if(cond != null && cond.getClass() != BooleanType.class) {
      ErrorMessage e = new ErrorMessage(i.tokenline, i.tokenchar, "expression in if statement must be a boolean, got " + cond);
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
    if(cond != null && cond.getClass() != BooleanType.class) {
      ErrorMessage e = new ErrorMessage(ws.tokenline, ws.tokenchar, "expression in while statement must be a boolean, got " + cond);
      violations.add(e);
    }
    ws.block.accept(this);
    return null;
  }

  public Type visit(PrintStatement ps) {
    Type m = (Type) ps.expression.accept(this);
    if(m != null && m.getClass() == VoidType.class) {
      ErrorMessage e = new ErrorMessage(ps.tokenline, ps.tokenchar, "print cannot be used with void type");
      violations.add(e);
    }
    return null;
  }

  public Type visit(PrintLnStatement ps) {
    Type m = (Type) ps.expression.accept(this);
    if(m != null && m.getClass() == VoidType.class) {
      ErrorMessage e = new ErrorMessage(ps.tokenline, ps.tokenchar, "println cannot be used with void type");
      violations.add(e);
    }
    return null;
  }

  public Type visit(ReturnStatement r) {
    if(r.expression == null) {
      if(current.decl.type.getClass() != VoidType.class) {
        ErrorMessage e = new ErrorMessage(r.tokenline, r.tokenchar, "empty return statement from non-void fucntion ");
        violations.add(e);
      }
    } else {
      Type ret = (Type) r.expression.accept(this);
      if(ret != null && !ret.equals(current.decl.type)) {
        ErrorMessage e = new ErrorMessage(r.tokenline, r.tokenchar, "return statement does not match function, expected: " + current.decl.type + " got:" +  ret);
        violations.add(e);
      }
    }
    return null;
  }

  public Type visit(AssignmentStatement as) {
    Type t = variables.get(as.id);
    Type got = (Type) as.value.accept(this);
    if(t == null) {
      ErrorMessage e = new ErrorMessage(as.tokenline, as.tokenchar, "assignment statement to undeclared variable \"" + as.id + "\"");
      violations.add(e);
    } else if(got != null && !t.equals(got)) {
      ErrorMessage e = new ErrorMessage(as.tokenline, as.tokenchar, "assignment value of \"" + as.id
                                        + "\" does not match expected type expected:" + t + " got: " + got);
      violations.add(e);
    } else if(t.getClass() == ArrayType.class) {
      ErrorMessage e = new ErrorMessage(as.tokenline, as.tokenchar, "array assignment is not allowed, cannot assign to \"" + as.id
                                        + "\"");
      violations.add(e);
    }
    return null;
  }

  public Type visit(ArrayAssignment as) {
    Type t = variables.get(as.id);
    Type got = (Type) as.value.accept(this);
    Type index = (Type) as.index.accept(this);
    if(t == null) {
      ErrorMessage e = new ErrorMessage(as.tokenline, as.tokenchar,  "variable \"" + as.id + "\" in array assignment is not declared");
      violations.add(e);
    } else if(t.getClass() != ArrayType.class) {
      ErrorMessage e = new ErrorMessage(as.tokenline, as.tokenchar, "variable \"" + as.id + "\" in not an array");
      violations.add(e);
    } else {
      ArrayType tmp = (ArrayType) t;
      if(tmp.type.getClass() != got.getClass()) {
        ErrorMessage e = new ErrorMessage(as.tokenline, as.tokenchar, "array assignment value of \"" + as.id
                                          + "\" does not match expected type expected:" + t + " got: " + got);
        violations.add(e);
      }
    }
    if(index != null && index.getClass() != IntergerType.class) {
      ErrorMessage e = new ErrorMessage(as.tokenline, as.tokenchar, "type of index for \"" + as.id + "\" is not a integer, got " + index);
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

  private static class TypePair {
    private Class c1;
    private Class c2;
    public TypePair(Class c1, Class c2) {
      this.c1 = c1;
      this.c2 = c2;
    }

    public boolean equals(TypePair other) {
      return (this.c1 == other.c1) && (this.c2 == other.c2);
    }
  }

  private boolean typecheck(Type t1, Type t2, TypePair operands[]) {
    TypePair got = new TypePair(t1.getClass(),t2.getClass());
    TypePair match = null;
    for(TypePair tmp : operands) {
      if(got.equals(tmp)) {
        match = tmp;
        break;
      }
    }
    return match != null;
  }

  private static final TypePair equality_operands[] = {new TypePair(IntergerType.class, IntergerType.class),
            new TypePair(FloatType.class, FloatType.class),
            new TypePair(CharType.class, CharType.class),
            new TypePair(StringType.class, StringType.class),
            new TypePair(BooleanType.class, BooleanType.class)
  };
  public Type visit(EqualityExpression ee) {
    Type t1 = (Type) ee.e1.accept(this);
    Type t2 = (Type) ee.e2.accept(this);
    if(t1 !=null && t2 != null && !typecheck(t1,t2,equality_operands)) {
      ErrorMessage e = new ErrorMessage(ee.tokenline, ee.tokenchar, "unsupported operands, given: " + t1 + " == " + t2);
      violations.add(e);
      return null;
    }
    return new BooleanType(-1,-1);
  }

  private static final TypePair less_operands[] = {new TypePair(IntergerType.class, IntergerType.class),
            new TypePair(FloatType.class, FloatType.class),
            new TypePair(CharType.class, CharType.class),
            new TypePair(StringType.class, StringType.class),
            new TypePair(BooleanType.class, BooleanType.class)
  };
  public Type visit(LessThanExpression ls) {
    Type t1 = (Type) ls.e1.accept(this);
    Type t2 = (Type) ls.e2.accept(this);
    if(t1 !=null && t2 != null && !typecheck(t1,t2,less_operands)) {
      ErrorMessage e = new ErrorMessage(ls.tokenline, ls.tokenchar, "unsupported operands, given: " + t1 + " < " + t2);
      violations.add(e);
      return null;
    }
    return new BooleanType(-1,-1);
  }

  private static final TypePair add_operands[] = {new TypePair(IntergerType.class, IntergerType.class),
            new TypePair(FloatType.class, FloatType.class),
            new TypePair(CharType.class, CharType.class),
            new TypePair(StringType.class, StringType.class)
  };
  public Type visit(AddExpression ae) {
    Type t1 = (Type) ae.e1.accept(this);
    Type t2 = (Type) ae.e2.accept(this);
    if(t1 !=null && t2 != null && !typecheck(t1,t2,add_operands)) {
      ErrorMessage e = new ErrorMessage(ae.tokenline, ae.tokenchar, "unsupported operands, given: " + t1 + " + " + t2);
      violations.add(e);
      return null;
    }
    return t1;
  }

  private static final TypePair sub_operands[] = {new TypePair(IntergerType.class, IntergerType.class),
            new TypePair(FloatType.class, FloatType.class),
            new TypePair(CharType.class, CharType.class)
  };
  public Type visit(SubtractExpression se) {
    Type t1 = (Type) se.e1.accept(this);
    Type t2 = (Type) se.e2.accept(this);
    if(t1 !=null && t2 != null && !typecheck(t1,t2,sub_operands)) {
      ErrorMessage e = new ErrorMessage(se.tokenline, se.tokenchar, "unsupported operands, given: " + t1 + " - " + t2);
      violations.add(e);
      return null;
    }
    return t1;
  }

  private static final TypePair multi_operands[] = {new TypePair(IntergerType.class, IntergerType.class),
            new TypePair(FloatType.class, FloatType.class)
  };
  public Type visit(MultiExpression me) {
    Type t1 = (Type) me.e1.accept(this);
    Type t2 = (Type) me.e2.accept(this);
    if(t1 !=null && t2 != null && !typecheck(t1,t2,multi_operands)) {
      ErrorMessage e = new ErrorMessage(me.tokenline, me.tokenchar, "unsupported operands, given: " + t1 + " * " + t2);
      violations.add(e);
      return null;
    }
    return t1;
  }

  public Type visit(FunctionCall fc) {
    Function f = functions.get(fc.name);
    if((f.decl.pl != null &&  fc.args == null) || (f.decl.pl == null && fc.args != null)) {
      ErrorMessage e = new ErrorMessage(fc.tokenline, fc.tokenchar, "function call of " + fc.name + " parameter length does not match");
      violations.add(e);
    } else if(fc.args != null) {
      if(f.decl.pl.getParameterCount() != fc.args.getExpressionCount()) {
        ErrorMessage e = new ErrorMessage(fc.tokenline, fc.tokenchar, "function call of " + fc.name + " parameter length does not match");
        violations.add(e);
      } else {
        for(int i = 0; i < f.decl.pl.getParameterCount(); i++) {
          Type expected = f.decl.pl.getParameter(i).type;
          Type got = (Type) fc.args.getExpression(i).accept(this);
          if(got != null && !expected.equals(got)) {
            ErrorMessage e = new ErrorMessage(fc.tokenline, fc.tokenchar, "function parameter " + i +  " of " + fc.name
                                              + " mismatch, given: " + got + " expected: " + expected);
            violations.add(e);
          }
        }
      }
    }
    return f.decl.type;
  }

  public Type visit(ArrayValue av) {
    Type t = (Type) variables.get(av.name);
    Type index = (Type) av.index.accept(this);
    if(t == null) {
      ErrorMessage e = new ErrorMessage(av.tokenline, av.tokenchar, "variable \"" + av.name + "\" is undefined");
      violations.add(e);
    } else if(t.getClass() != ArrayType.class) {
      ErrorMessage e = new ErrorMessage(av.tokenline, av.tokenchar, "variable \"" + av.name + "\" is not an array");
      violations.add(e);
      t = null;
    } else {
      t = ((ArrayType) t).type;
      if(index != null && index.getClass() != IntergerType.class) {
        ErrorMessage e = new ErrorMessage(av.tokenline, av.tokenchar, "index of \"" + av.name + "\" is not an int");
        violations.add(e);
      }
    }
    return t;
  }

  public Type visit(IdentifierValue iv) {
    Type t = (Type) variables.get(iv.id);
    if(t == null) {
      ErrorMessage e = new ErrorMessage(iv.tokenline, iv.tokenchar, "variable \"" + iv.id + "\" is undefined");
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
    return null; //not used
  }

  public Type visit(IntergerType it) {
    return null; //not used
  }

  public Type visit(FloatType ft) {
    return null; //not used
  }

  public Type visit(CharType ct) {
    return null; //not used
  }

  public Type visit(StringType st) {
    return null; //not used
  }

  public Type visit(BooleanType bt) {
    return null; //not used
  }

  public Type visit(VoidType vt) {
    return null; //not used
  }

  public Type visit(Identifier i) {
    return null; //not used
  }
}
