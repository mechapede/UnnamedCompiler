/* Generates the Jasmin String representation of JVM instructions */
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

  /* Returns the JVM type name */
  private String jasminType(Temporary.Type t) {
    switch(t) {
    case VOID:
      return "V";
    case BOOL:
      return "Z";
    case CHAR:
      return "C";
    case FLOAT:
      return "F";
    case INT:
      return "I";
    case STRING:
      return "Ljava/lang/String;";
    case ARRAY_BOOL:
      return "[Z";
    case ARRAY_CHAR:
      return "[C";
    case ARRAY_FLOAT:
      return "[F";
    case ARRAY_INT:
      return "[I";
    case ARRAY_STRING:
      return "[Ljava/lang/String;";
    default:
      throw new IRInvalidException("jasminType unexpected type: " + t);
    }
  }

  /* Return the prefix type for operands */
  private String jasminOpPrefix(Temporary.Type t) {
    switch(t) {
    case BOOL:
      return "i";
    case CHAR:
      return "i";
    case FLOAT:
      return "f";
    case INT:
      return "i";
    case STRING:
      return "a";
    case ARRAY_BOOL:
    case ARRAY_CHAR:
    case ARRAY_FLOAT:
    case ARRAY_INT:
    case ARRAY_STRING:
      return "a";
    default:
      throw new IRInvalidException("OpPrefix unexpected type: " + t);
    }
  }

  /* Returns the prefix type for setting array store/load command */
  private String jasminArrayPrefix(Temporary.Type t) {
    switch(t) {
    case BOOL:
      return "b";
    case CHAR:
      return "c";
    case FLOAT:
      return "f";
    case INT:
      return "i";
    case STRING:
      return "a";
    default:
      throw new IRInvalidException("ArrayPrefix unexpected type: " + t);
    }
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
    out += ".method public static __" + f.id; //all user functions __*
    out += "(";
    int i = 0;
    while(i < f.vars.temps.size()) {
      Temporary t = f.vars.temps.get(i);
      if(t.use != Temporary.Use.PARAMETER) break;
      out += jasminType(t.type);
      i++;
    }
    out += ")" + jasminType(f.ret) +"\n";
    out += "   .limit locals " + f.vars.temps.size() + "\n";
    i = 0;
    while(i < f.vars.temps.size()) {
      Temporary t = f.vars.temps.get(i);
      if(t.name != null) {
        out += "   " + ".var " + t.index + " is " + t.name + " " + jasminType(t.type) + " from L_0 to L_1\n";
      } else {
        out += "   " + ".var " + t.index + " is T" +  t.index + " " + jasminType(t.type)  + " from L_0 to L_1\n";
      }
      i++;
    }
    out += "   .limit stack 16\n"; //plenty of stack space
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
      Temporary param = ca.args.get(i);
      out += "   " + jasminOpPrefix(param.type) + "load " + param.index + "\n";
    }
    out += "   invokestatic " + classname + "/__" + ca.function.id + "(";
    for(int i=0; i < ca.args.size(); i++) {
      Temporary param = ca.args.get(i);
      out += jasminType(param.type);
    }
    out += ")";
    out += jasminType(ca.function.ret);
    if(ca.dest != null) out += "\n   " + jasminOpPrefix(ca.dest.type) + "store " + ca.dest.index;
    return null;
  }

  public Object visit(IRReturn ret) {
    if(ret.value == null) {
      out += "   return";
    } else {

      out += "   " + jasminOpPrefix(ret.value.type) + "load " + ret.value.index + "\n" + //TODO: returning arrays
             "   " + jasminOpPrefix(ret.value.type)  + "return";
    }
    return null;
  }

  public Object visit(IRArrayCreate ac) {
    out += "   ldc " + ac.size + "\n";
    switch(ac.dest.type) {
    case ARRAY_BOOL:
      out += "   newarray boolean\n";
      break;
    case ARRAY_CHAR:
      out += "   newarray char\n";
      break;
    case ARRAY_FLOAT:
      out += "   newarray float\n";
      break;
    case ARRAY_INT:
      out += "   newarray int\n";
      break;
    case ARRAY_STRING:
      out+= "   anewarray java/lang/String\n";
      break;
    default:
      throw new IRInvalidException("ArrayCreate type is not an array!");
    }
    out += "   astore " + ac.dest.index;
    return null;
  }

  public Object visit(IRArrayValue av) {
    out += "   " + "aload " + av.array.index + "\n" +
           "   iload " + av.index.index + "\n" +
           "   " + jasminArrayPrefix(av.dest.type) +  "aload\n" +
           "   " + jasminOpPrefix(av.dest.type) + "store " + av.dest.index;
    return null;
  }

  public Object visit(IRArrayAssignment as) {
    out += "   aload " + as.dest.index + "\n" +
           "   " + jasminOpPrefix(as.index.type) + "load " + as.index.index + "\n" +
           "   " + jasminOpPrefix(as.input.type) + "load " + as.input.index + "\n" +
           "   " + jasminArrayPrefix(as.input.type) + "astore";
    return null;
  }

  public Object visit(IRAssignment as) {
    out += "   " + jasminOpPrefix(as.input.type) + "load " + as.input.index + "\n" +
           "   " + jasminOpPrefix(as.dest.type) + "store " + as.dest.index;
    return null;
  }

  public Object visit(IRConstantAssignment ca) {
    out += "   ldc ";
    ca.val.accept(this);
    out += "\n   " + jasminOpPrefix(ca.dest.type) + "store " + ca.dest.index;
    return null;
  }

  public Object visit(IRConstantBool b) {
    out += b.value? "1":"0";
    return null;
  }

  public Object visit(IRConstantChar c) {
    out += "" + ((int) c.value);
    return null;
  }

  public Object visit(IRConstantFloat f) {
    out += "" + f.value;
    return null;
  }

  public Object visit(IRConstantInt i) {
    out += "" + i.value;
    return null;
  }

  public Object visit(IRConstantString s) {
    out += "\"" + s.value + "\"";
    return null;
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
    case NEGATE: //only negate with bool used in language
      switch(u.input.type) {
      case BOOL:
        out += "   iload " + u.input.index + "\n";
        out += "   ldc 1\n";
        out += "   ixor \n";
        out += "   istore " + u.result.index;
        break;
      default:
        throw new IRInvalidException("Unsupported unary negate type: " + u.input.type);
      }
      break;
    default:
      throw new IRInvalidException("Unsupported unary op: " + u.operation);
    }
    return null;
  }

  public Object visit(IRBinaryOp b) {
    switch(b.operation) {
    case ADD:
      switch(b.input1.type) {
      case INT: //TODO: some of these cases can be collapsed with added prefix Jasmin Functions
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
        out += "   new java/lang/StringBuilder\n";
        out += "   dup\n";
        out += "   invokenonvirtual java/lang/StringBuilder/<init>()V\n";
        out += "   aload " + b.input1.index + "\n";
        out += "   invokevirtual java/lang/StringBuilder/append(Ljava/lang/String;)Ljava/lang/StringBuilder;\n";
        out += "   aload " + b.input2.index + "\n";
        out += "   invokevirtual java/lang/StringBuilder/append(Ljava/lang/String;)Ljava/lang/StringBuilder;\n";
        out += "   invokevirtual java/lang/StringBuilder/toString()Ljava/lang/String;\n";
        out += "   astore " + b.result.index;
        break;
      default:
        throw new IRInvalidException("Unsupported type with " + b.operation + ": " + b.input1.type);
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
        throw new IRInvalidException("Unsupported type with " + b.operation + ": " + b.input1.type);
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
        throw new IRInvalidException("Unsupported type with " + b.operation + ": " + b.input1.type);
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
        out += "   fcmpg\n";
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
        throw new IRInvalidException("Unsupported type with " + b.operation + ": " + b.input1.type);
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
        out += "   fcmpg\n";
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
        throw new IRInvalidException("Unsupported type with " + b.operation + ": " + b.input1.type);
      }
      break;
    default:
      throw new IRInvalidException("Unsupported operand " + b.operation);
    }
    return null;
  }

  private String printType(Temporary.Type t) {
    switch(t) {
    case ARRAY_BOOL:
    case ARRAY_CHAR:
    case ARRAY_FLOAT:
    case ARRAY_INT:
    case ARRAY_STRING:
      return "Ljava/lang/Object;";
    default:
      return jasminType(t);
    }
  }

  public Object visit(IRPrint p) {
    out += "   getstatic java/lang/System/out Ljava/io/PrintStream;\n";
    out += "   " + jasminOpPrefix(p.contents.type) + "load " + p.contents.index + "\n";
    out += "   invokevirtual java/io/PrintStream/print(" + printType(p.contents.type) + ")V";
    return null;
  }

  public Object visit(IRPrintln pl) {
    out += "   getstatic java/lang/System/out Ljava/io/PrintStream;\n";
    out += "   " + jasminOpPrefix(pl.contents.type) + "load " + pl.contents.index + "\n";
    out += "   invokevirtual java/io/PrintStream/println(" + printType(pl.contents.type) + ")V";
    return null;
  }
}
