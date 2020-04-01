/*Compiler - core glue code for running the parser and generating the tree
              and printing the Jasmin code */

import org.antlr.runtime.*;
import java.io.*;
import ast.*;
import inter.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Compiler {
  public static void main(String[] args) throws Exception {
    if(args.length == 0) {
      System.out.println("Usage: Compiler filename.ul");
      System.exit(1);
    }

    Pattern name_regex = Pattern.compile("^(?:[^/]*/+)*([^/]+?)\\.ul$");
    Matcher match = name_regex.matcher(args[0]);
    boolean found = match.find();
    if(!found) {
      System.out.println("filename must end with .ul");
      System.exit(1);
    }

    ANTLRInputStream input;
    input = new ANTLRInputStream(new FileInputStream(args[0]));

    boolean prettyprint = false;
    boolean typeonly = false;
    boolean ironly = false;
    if(args.length > 1 && args[1].equals("-p")) {
      prettyprint = true;
    } else if(args.length > 1 && args[1].equals("-t")) {
      typeonly = true;
    } else if(args.length > 1 && args[1].equals("-i")) {
      ironly = true;
    }

    ulNoActionsLexer lexer = new ulNoActionsLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    ulNoActionsParser parser = new ulNoActionsParser(tokens);

    try {
      Program p = parser.program();
      PrintVisitor v = new PrintVisitor();
      p.accept(v);
      if(prettyprint) {
        System.out.println(v.getOut());
        return;
      }

      TypeVisitor checker = new TypeVisitor();
      p.accept(checker);
      if(checker.errors()) {
        System.err.println(checker.dumpErrors());
        System.exit(2);
      }
      if(typeonly) {
        return;
      }

      IntermediateVisitor ir = new IntermediateVisitor();
      p.accept(ir);
      IRProgram irp = ir.getIRProgram();
      irp.setName(match.group(1));
      if(ironly) {
        System.out.print(irp);
        return;
      }

      JasminIRVisitor irv = new JasminIRVisitor();
      irp.accept(irv);
      System.out.print(irv.getOut());

    } catch(RecognitionException e)	{
      System.err.println("Compiler failed. See errors below:");
      System.exit(2);
      // A lexical or parsing error occured.
      // ANTLR will have already printed information on the
      // console due to code added to the grammar.  So there is
      // nothing to do here.
    } catch(Exception e) {
      System.err.println("Compiler failed. See errors below:");
      System.err.println(e);
      e.printStackTrace();
      System.exit(2);
    }
  }
}
