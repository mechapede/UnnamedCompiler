grammar ulNoActions;

@members
{
protected void mismatch (IntStream input, int ttype, BitSet follow)
        throws RecognitionException
{
        throw new MismatchedTokenException(ttype, input);
}
public Object recoverFromMismatchedSet (IntStream input,
                                      RecognitionException e,
                                      BitSet follow)
        throws RecognitionException
{
        reportError(e);
        throw e;
}
}

@rulecatch {
        catch (RecognitionException ex) {
                reportError(ex);
                throw ex;
        }
}

/*
 * This is a subset of the ulGrammar to show you how
 * to make new production rules.
 * You will need to:
 *  - change type to be compoundType and include appropriate productions
 *  - introduce optional formalParameters
 *  - change functionBody to include variable declarations and statements
 */

program : function+ EOF
	;

function: functionDecl functionBody
	;

functionDecl: compoundType identifier '(' formalParameters* ')'
	;

formalParameters : compoundType ID moreFormals*
    ;

moreFormals : ',' compoundType ID
    ;

functionBody: '{' vardecl* statement* '}'
	;

vardecl: compoundType identifier ';'
    ;

compoundType : type ( '[' INTERGERCONSTANT ']' )?
    ;

statement: (';' | IF '(' expr ')' block (ELSE block)? |
            WHILE '(' expr ')' block | PRINT expr ';' |
            PRINTLN expr ';' | RETURN expr ';' |
            ID '=' expr ';' | ID '[' expr ']' '=' expr ';' )
    ;

block: '{' statement* '}'
    ;

expr: ltop ('==' ltop)*
    ;

ltop: addop ('<' addop)*
    ;

addop: multiop ( ('+' | '-') multiop)*
    ;

multiop: atom ('*' atom)*
    ;

atom: (ID '[' expr ']' |  ID '(' exprList ')' | ID |literal | '(' expr ')' )
    ;

literal : ( STRINGCONSTANT |  INTERGERCONSTANT | CHARACTERCONSTANT |
            FLOATCONSTANT | TRUE | FALSE )
    ;

exprList: expr exprMore*
    ;

exprMore: ',' + expr
    ;

identifier : ID
	;

type: TYPE
	;

/* Lexer */


IF	: 'if'
	;

ELSE : 'else'
	;

WHILE : 'while'
    ;

PRINT : 'print'
    ;

PRINTLN : 'println'
    ;

RETURN : 'return'
    ;

STRINGCONSTANT : '"' ( 'a'..'z' | 'A'..'Z' | '0'..'9' | '!' | ',' | ':' | '_' | '{' | '}' | ' ' )* '"'
    ;

INTERGERCONSTANT : ( '0'..'9' )+
    ;

CHARACTERCONSTANT : '\'' ( 'a'..'z' | 'A'..'Z' | '0'..'9' | '!' | ',' | ':' | '_' | '{' | '}' | ' ' ) '\''
    ;

FLOATCONSTANT : ( '0'..'9')+ '.' ( '0'..'9')+
    ;

TRUE : 'true'
    ;

FALSE : 'fallse'
    ;

TYPE	: ( 'int' | 'float' | 'char' | 'string' | 'boolean' | 'void' )
	;

ID	: ( 'a'..'z' | 'A'..'Z' | '_'  ) ( 'a'..'z' | 'A'..'Z' | '_' | '0'..'9' )*
	;

/* These two lines match whitespace and comments
 * and ignore them.
 * You want to leave these as last in the file.
 * Add new lexical rules above
 */
WS      : ( '\t' | ' ' | ('\r' | '\n') )+ { $channel = HIDDEN;}
        ;

COMMENT : '//' ~('\r' | '\n')* ('\r' | '\n') { $channel = HIDDEN;}
        ;
