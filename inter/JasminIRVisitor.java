package inter;

public class JasminIRVisitor implements IRVisitor {
  private String out;
  private String classname;
  private int label_offset;

  public JasminIRVisitor() {
    out = "";
  }

  public String getOut() {
    return out;
  }

  public Object visit(IRProgram p) {
    classname = p.name;
    out += ".source " + p.name + ".ul\n";
    out += ".class public " + p.name + "\n";
    out += ".super java/lang/Object\n\n";
    for(int i=0; i<p.functions.size(); i++) {
      p.functions.get(i).accept(this);
      out += "\n";
    }
    out += "; BOILERPLATE CODE\n\n";
    out += ".method public static main([Ljava/lang/String;)V\n";
    out += "   .limit locals 1\n";
    out += "   .limit stack 4\n";
    out += "   invokestatic " + p.name + "/__main()V\n";
    out += "   return\n";
    out += ".end method\n\n";
    out += ".method public <init>()V\n";
    out += "   aload 0\n";
    out += "   invokenonvirtual java/lang/Object/<init>()V\n";
    out += "   return\n";
    out += ".end method\n";
    return null;
  }

  public Object visit(IRFunction f) {
    out += ".method public static __" + f.id;
    out += "(";
    int i = 0;
    while(i < f.vars.temps.size()) {
      Temporary t = f.vars.temps.get(i);
      if(t.use != Temporary.Use.PARAMETER) break;
      out += t.type.jvmType();
      i++;
    }
    out += ")" + f.ret.jvmType() +"\n";
    out += "   .limit locals " + f.vars.temps.size() + "\n";
    i = 0;
    while(i < f.vars.temps.size()) {
      Temporary t = f.vars.temps.get(i);
      if(t.name != null) {
        out += "   " + ".var " + t.index + " is " + t.name + " " + t.type.jvmType() + " from L_0 to L_1\n";
      } else {
        out += "   " + ".var " + t.index + " is T" +  t.index + " " + t.type.jvmType()  + " from L_0 to L_1\n";
      }
      i++;
    }
    out += "   .limit stack 16\n"; //plenty of stack room
    out += "L_0:\n";
    label_offset = 2;
    for(i =0; i < f.statements.size(); i++) {
      out += ";" + f.statements.get(i) + ";\n";
      f.statements.get(i).accept(this);
      out += "\n";
    }
    out += "L_1:\n";
    out += ".end method\n";
    return null;
  }

  public Object visit(IRCall ca) {
    for(int i=0; i < ca.args.size(); i++) {
      out += "   " + ca.args.get(i).type.jvmPrefix() + "load " + ca.args.get(i).index + "\n";
    }
    out += "   invokestatic " + classname + "/__" + ca.function.id + "(";
    for(int i=0; i < ca.args.size(); i++) {
      out += ca.args.get(i).type.jvmType();
    }
    out += ")";
    out += ca.function.ret;
    if(ca.dest != null) out += "\n   istore " + ca.dest.index;
    return null;
  }

  public Object visit(IRReturn r) {
    if(r.value == null) {
      out += "   return";
    } else {
      out += "   " + r.value.type.jvmPrefix() + "load " + r.value.index + "\n" + //TODO: returning arrays
             "   " + r.value.type.jvmPrefix()  + "return";
    }
    return null;
  }

  public Object visit(IRArrayCreate ac) {
    out += "   ldc " + ac.size + "\n";
    switch(ac.dest.type) {
    case ARRAY_BOOL:
      out += "   newarray boolean \n";
      break;
    case ARRAY_CHAR:
      out += "   newarray char \n";
      break;
    case ARRAY_FLOAT:
      out += "   newarray float \n";
      break;
    case ARRAY_INT:
      out += "   newarray int\n";
      break;
    case ARRAY_STRING:
      out+= "   newarray java/lang/String\n";
      break;
    default:
      return null;
    }
    out += "   astore " + ac.dest.index;
    return null;
  }

  public Object visit(IRArrayValue av) {
    out += "   " + "aload " + av.array.index + "\n" +
           "   iload " + av.index.index + "\n";
    switch(av.array.type) {
    case ARRAY_BOOL:
      out += "   baload\n";
      break;
    case ARRAY_CHAR:
      out += "   caload\n";
      break;
    case ARRAY_FLOAT:
      out += "   faload\n";
      break;
    case ARRAY_INT:
      out += "   iaload\n";
      break;
    case ARRAY_STRING:
      out += "   aaload\n";
      break;
    default:
      return null;
    }

    out += "   " + av.dest.type.jvmPrefix() + "store " + av.dest.index;
    return null;
  }

  public Object visit(IRArrayAssignment aa) {
    out += "   aload " + aa.dest.index + "\n" +
           "   " + aa.index.type.jvmPrefix() + "load " + aa.index.index + "\n" +
           "   " + aa.input.type.jvmPrefix() + "load " + aa.input.index + "\n";
    switch(aa.dest.type) {
    case ARRAY_BOOL:
      out += "   bastore";
      break;
    case ARRAY_CHAR:
      out += "   castore";
      break;
    case ARRAY_FLOAT:
      out += "   fastore";
      break;
    case ARRAY_INT:
      out += "   iastore";
      break;
    case ARRAY_STRING:
      out += "   aastore";
      break;
    default:
      return null;
    }
    return null;
  }

  public Object visit(IRAssignment as) {
    out += "   " + as.input.type.jvmPrefix() + "load " + as.input.index + "\n" +
           "   " + as.dest.type.jvmPrefix() + "store " + as.dest.index;
    return null;
  }

  public Object visit(IRConstantAssignment ca) {
    out += "   ldc " + ca.val.jsmVal() + "\n" +
           "   " + ca.dest.type.jvmPrefix() + "store " + ca.dest.index;
    return null;
  }

  public Object visit(IRConstantBool b) {
    return null; //not used
  }

  public Object visit(IRConstantChar c) {
    return null; //not used
  }

  public Object visit(IRConstantFloat f) {
    return null; //not used
  }

  public Object visit(IRConstantInt i) {
    return null; //not used
  }

  public Object visit(IRConstantString s) {
    return null; //not used
  }

  public Object visit(IRIfGoto ifg) {
    out += "   iload " + ifg.cond.index + "\n" +
           "   ifne " + ifg.label.name;
    return null;
  }

  public Object visit(IRGoto f) {
    out += "   goto " + f.label.name;
    return null;
  }

  public Object visit(IRLabel l) {
    out += l.name + ":";
    return null;
  }

  public Object visit(IRUnaryOp u) {
    switch(u.operation) {
    case NEGATE: //only boolean used
      out += "   iload " + u.input.index + "\n";
      out += "   ldc 1\n";
      out += "   ixor \n";
      out += "   istore " + u.result.index;
      break;
    default:
      return null;
    }
    return null;
  }

  public Object visit(IRBinaryOp b) {
    switch(b.operation) {
    case ADD:
      switch(b.input1.type) {
      case INT:
      case CHAR:
        out += "   iload " + b.input1.index + "\n";
        out += "   iload " + b.input2.index + "\n";
        out += "   iadd\n";
        out += "   istore " + b.result.index;
        break;
      case FLOAT:
        out += "   fload " + b.input1.index + "\n";
        out += "   fload " + b.input2.index + "\n";
        out += "   fadd\n";
        out += "   fstore " + b.result.index;
        break;
      case STRING:
        out += "   new java/lang/Stringouter\n";
        out += "   dup\n";
        out += "   invokenonvirtual java/lang/Stringouter/<init>()V\n";
        out += "   aload " + b.input1.index + "\n";
        out += "   invokevirtual java/lang/Stringouter/append(Ljava/lang/String;)Ljava/lang/Stringouter;\n";
        out += "   aload " + b.input2.index + "\n";
        out += "   invokevirtual java/lang/Stringouter/append(Ljava/lang/String;)Ljava/lang/Stringouter;\n";
        out += "   invokevirtual java/lang/Stringouter/toString()Ljava/lang/String;\n";
        out += "   astore " + b.result.index;
        break;
      default:
        break; //TODO: error handling
      }
      break;
    case SUB:
      switch(b.input1.type) {
      case INT:
      case CHAR:
        out += "   iload " + b.input1.index + "\n";
        out += "   iload " + b.input2.index + "\n";
        out += "   isub\n";
        out += "   istore " + b.result.index;
        break;
      case FLOAT:
        out += "   fload " + b.input1.index + "\n";
        out += "   fload " + b.input2.index + "\n";
        out += "   fsub\n";
        out += "   fstore " + b.result.index;
        break;
      default:
        break; //TODO error handling
      }
      break;
    case MUL:
      switch(b.input1.type) {
      case INT:
        out += "   iload " + b.input1.index + "\n";
        out += "   iload " + b.input2.index + "\n";
        out += "   imul\n";
        out += "   istore " + b.result.index;
        break;
      case FLOAT:
        out += "   fload " + b.input1.index + "\n";
        out += "   fload " + b.input2.index + "\n";
        out += "   fmul\n";
        out += "   fstore " + b.result.index;
        break;
      default:
        break;
      }
      break;
    case EQUAL:
      switch(b.input1.type) {
      case INT:
      case CHAR:
      case BOOL:
        out += "   iload " + b.input1.index + "\n";
        out += "   iload " + b.input2.index + "\n";
        out += "   isub\n";
        out += "   ifeq L_" + label_offset + "\n";
        out += "   ldc 0\n";
        out += "   goto L_" + (label_offset+1) + "\n";
        out += "L_" + label_offset + ":\n";
        out += "   ldc 1\n";
        out += "L_" + (label_offset+1) + ":\n";
        out += "   istore " + b.result.index;
        label_offset += 2;
        break;
      case FLOAT:
        out += "   fload " + b.input1.index + "\n";
        out += "   fload " + b.input2.index + "\n";
        out += "   fsub\n";
        out += "   ifeq L_" + label_offset + "\n";
        out += "   ldc 0\n";
        out += "   goto L_" + (label_offset+1) + "\n";
        out += "L_" + label_offset + ":\n";
        out += "   ldc 1\n";
        out += "L_" + (label_offset+1) + ":\n";
        out += "   istore " + b.result.index;
        label_offset += 2;
        break;
      case STRING:
        out += "   aload " + b.input1.index + "\n";
        out += "   aload " + b.input2.index + "\n";
        out += "   invokevirtual java/lang/String/compareTo(Ljava/lang/String;)I\n";
        out += "   ifeq L_" + label_offset + "\n";
        out += "   ldc 0\n";
        out += "   goto L_" + (label_offset+1) + "\n";
        out += "L_" + label_offset + ":\n";
        out += "   ldc 1\n";
        out += "L_" + (label_offset+1) + ":\n";
        out += "   istore " + b.result.index;
        label_offset += 2;
        break;
      default:
        break; //TODO: error check
      }
      break;
    case LT:
      switch(b.input1.type) {
      case INT:
      case CHAR:
      case BOOL:
        out += "   iload " + b.input1.index + "\n";
        out += "   iload " + b.input2.index + "\n";
        out += "   isub\n";
        out += "   iflt L_" + label_offset + "\n";
        out += "   ldc 0\n";
        out += "   goto L_" + (label_offset+1) + "\n";
        out += "L_" + label_offset + ":\n";
        out += "   ldc 1\n";
        out += "L_" + (label_offset+1) + ":\n";
        out += "   istore " + b.result.index;
        label_offset += 2;
        break;
      case FLOAT:
        out += "   fload " + b.input1.index + "\n";
        out += "   fload " + b.input2.index + "\n";
        out += "   fsub\n";
        out += "   iflt L_" + label_offset + "\n";
        out += "   ldc 0\n";
        out += "   goto L_" + (label_offset+1) + "\n";
        out += "L_" + label_offset + ":\n";
        out += "   ldc 1\n";
        out += "L_" + (label_offset+1) + ":\n";
        out += "   istore " + b.result.index;
        label_offset += 2;
        break;
      case STRING:
        out += "   aload " + b.input1.index + "\n";
        out += "   aload " + b.input2.index + "\n";
        out += "   invokevirtual java/lang/String/compareTo(Ljava/lang/String;)I\n";
        out += "   iflt L_" + label_offset + "\n";
        out += "   ldc 0\n";
        out += "   goto L_" + (label_offset+1) + "\n";
        out += "L_" + label_offset + ":\n";
        out += "   ldc 1\n";
        out += "L_" + (label_offset+1) + ":\n";
        out += "   istore " + b.result.index;
        label_offset += 2;
        break;
      default:
        break;
      }
      break;
    default:
      return null;
    }
    return null;
  }

  public Object visit(IRPrint p) {
    out += "   getstatic java/lang/System/out Ljava/io/PrintStream;\n";
    out += "   " + p.contents.type.jvmPrefix() + "load " + p.contents.index + "\n";
    out += "   invokevirtual java/io/PrintStream/print(" + p.contents.type.jvmType() + ")V";
    return null;
  }

  public Object visit(IRPrintln pl) {
    out += "   getstatic java/lang/System/out Ljava/io/PrintStream;\n";
    out += "   " + pl.contents.type.jvmPrefix() + "load " + pl.contents.index + "\n";
    out += "   invokevirtual java/io/PrintStream/println(" + pl.contents.type.jvmType() + ")V";
    return null;
  }
}
