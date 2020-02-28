/* Checks the syntax for types and reference validity*/
package ast;

import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Collections;

public class IRVisitor extends Visitor {
        IRProgram program; 
        IRFunction current;

        public TypeVisitor() {

        }

        public Type visit(Program p) {
            return null;
        }

        public Type visit(Function f) {
            return null;
        }

        public Type visit(FunctionDeclaration fd) {
            return null;
        }

        public Type visit(FormalParameterList fpl) {
            return null;
        }

        public Type visit(FormalParameter fp) {
            return null;
        }

        public Type visit(FunctionBody fb) {
            return null;
        }

        public Type visit(VariableDeclaration vd) {
            return null;
        }

        public Type visit(ExpressionStatement es) {
            return null;
        }

        public Type visit(IfStatement i) {
            return null;
        }

        public Type visit(WhileStatement ws) {
            return null;
        }

        public Type visit(PrintStatement ps) {
            return null;
        }

        public Type visit(PrintLnStatement ps) {
            return null;
        }

        public Type visit(ReturnStatement r) {
            return null;
        }

        public Type visit(AssignmentStatement as) {
            return null;
        }

        public Type visit(ArrayAssignment as) {
            return null;
        }

        public Type visit(Block b) {
            return null;
        }

        public Type visit(ExpressionList el) {
            return null;
        }

        public Type visit(EqualityExpression ee) {
            return null;
        }

        public Type visit(LessThanExpression ls) {
            return null;
        }

        public Type visit(AddExpression ae) {
            return null;
        }

        public Type visit(SubtractExpression se) {
            return null;
        }

        public Type visit(MultiExpression me) {
            return null;
        }

        public Type visit(FunctionCall fc) {
            return null;
        }

        public Type visit(ArrayValue av) {
            return null;
        }

        public Type visit(IdentifierValue iv) {
            return null;
        }

        public Type visit(StringLiteral sl) {
            return null;
        }

        public Type visit(IntergerLiteral il) {
            return null;
        }

        public Type visit(CharacterLiteral cl) {
            return null;
        }

        public Type visit(FloatLiteral fl) {
            return null;
        }

        public Type visit(BooleanLiteral bl) {
            return null;
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

