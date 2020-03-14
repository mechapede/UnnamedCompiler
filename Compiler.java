/*Compiler - core glue code for running the parser and generating the tree
              also prints out the tree output if it accepts */

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
            return;
        }
        
        Pattern name_regex = Pattern.compile("^(?:[^/]*/+)*([^/.]+)\\.ul$");
        Matcher match = name_regex.matcher(args[0]);
        boolean found = match.find();
        if( !found ){
            System.out.println("filename must end with .ul");
        } 
        
        ANTLRInputStream input;
        input = new ANTLRInputStream(new FileInputStream(args[0]));
        
        boolean prettyprint = false;
        if(args.length > 1 && args[1].equals("-p")) {
            prettyprint = true;
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
            } else {
                TypeVisitor checker = new TypeVisitor();
                p.accept(checker);
                if(checker.errors()) {
                    System.err.println(checker.dumpErrors());
                } else {
                    IRVisitor ir = new IRVisitor();
                    p.accept(ir);
                    IRProgram irp = ir.getIRProgram();
                    irp.setName(match.group(1));
                    
                    System.out.print(irp); //TODO: make IRProgram print name
                }
            }


        } catch(RecognitionException e)	{
            System.err.println("Compiler failed. See errors below:");
            // A lexical or parsing error occured.
            // ANTLR will have already printed information on the
            // console due to code added to the grammar.  So there is
            // nothing to do here.
        } catch(Exception e) {
            System.err.println("Compiler failed. See errors below:");
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
