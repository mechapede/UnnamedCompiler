grammar ulNoActions;

@header
{
    import ast.*;
}

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

program returns [Program p]
@init
{
	p = new Program();
}
    :
    (f=function{p.addFunction(f);})+ EOF
	;

function returns [Function f]
@init
{
    f = new Function();
}
    :
    d=functionDecl{f.setDeclaration(d);} b=functionBody{f.setBody(b);}
	;

functionDecl returns [FunctionDeclaration fd]
@init
{
    fd = new FunctionDeclaration();
}
    :
    t=compoundType{fd.setType(t);} i=identifier{fd.setIdentifier(i);} '(' (p=formalParameters{fd.setParameters(p);})? ')'
	;

formalParameters returns [FormalParameterList pl]
@init
{
    pl = new FormalParameterList(); //TODO fix creating Formal Parameters
}
    :
    f1=formalParameter{pl.addParameter(f1);} (fn=moreFormals{pl.addParameter(fn);})*
    ;

moreFormals returns [FormalParameter fp]
    :
    ',' f=formalParameter{fp = f;}
    ;

formalParameter returns [FormalParameter fp]
@init
{
    fp = new FormalParameter();
}
    :
    c=compoundType{fp.setType(c);} i=identifier{fp.setIdentifier(i);}
    ;

functionBody returns [FunctionBody fb]
@init
{
    fb = new FunctionBody();
}
    :
    '{' (v=vardecl{fb.addDeclaration(v);})* (s=statement{fb.addStatement(s);})* '}'
	;

vardecl returns [VariableDeclaration va]
@init
{
    	va = new VariableDeclaration(); //TODO fix populating
}
    :
    t=compoundType{va.setType(t);} i=identifier{va.setIdentifier(i);} ';'
    ;

compoundType returns [Type t]
@init
{
    boolean array = false;
    int array_size = 0;
}
@after
{
    if(array){
        t = new ArrayType(t,array_size);
    }
}
    :
    tb=type{t=tb;} ( '[' n=INTERGERCONSTANT{array=true; array_size=Integer.parseInt(n.getText());} ']' )?
    ;

statement returns [Statement s]
    :
    (s1=emptystatement{s1 = null;})|
    (s2=ifstatement{s2 = null;})|
    (s3=whilestatement{s3 = null;})|
    (s4=printstatement{s4 = null;})|
    (s5=printlnstatement{s5 = null;})|
    (s6=returnstatement){s6 = null;}|
    (s7=assignmentstatement{s7 = null;})|
    (s8=arrayassignmentstatement{s8 = null;})
    ;

emptystatement returns [Statement s]
@init
{
    s = null; //TODO: maybe need a class for this to print
}
    :
    ';'
    ;

ifstatement returns [IfStatement s]
@init
{
    s = new IfStatement();
}
    :
    IF '(' e=expr{s.setExpression(e);} ')' b=block{s.setBlock(b);} (ELSE be=block{s.setElseBlock(be);})?
    ;

whilestatement returns [WhileStatement s]
    @init
    {
        s = new WhileStatement();
    }
    :
    WHILE '(' e=expr{s.setExpression(e);} ')' b=block{s.setBlock(b);}
    ;

printstatement returns [PrintStatement s]
    :
    PRINT e=expr{s = new PrintStatement(e);} ';'
    ;

printlnstatement returns [PrintLnStatement s]
    :
    PRINTLN e=expr{s = new PrintLnStatement(e);} ';'
    ;

returnstatement returns [ReturnStatement s]
    :
    RETURN e=expr{s = new ReturnStatement(e);} ';'
    ;

assignmentstatement returns [AssignmentStatement s]
@init
{
    s = new AssignmentStatement();
}
    :
    i=identifier{s.setIdentifier(i);} '=' e=expr{s.setExpression(e);} ';'
    ;

arrayassignmentstatement returns [ArrayAssignment s]
@init
{
    s = new ArrayAssignment();
}
    :
    i=identifier{s.setIdentifier(i);} '[' index=expr{s.setIndexExpression(index);} ']' '=' v=expr{s.setExpression(v);} ';'
    ;

block returns [Block b]
@init
{
    b = new Block();
}
    :
    '{' (s=statement{b.addStatement(s);})* '}'
    ;

expr returns [Expression e]
    :
    ee=ltop{e = ee;} ('==' ee=ltop{e = new EqualityExpression(e,ee);})*
    ;

ltop returns [Expression e]
    :
    ee=addop{e = ee;} ('<' ee=addop{e = new LessThanExpression(e,ee);})*
    ;

addop returns [Expression e]
    :
    ee=multiop{e=ee;} ('+' ee=multiop{e = new AddExpression(e,ee);} | ee=multiop{e = new SubtractExpression(e,ee);} '-')*
    ;

multiop returns [Expression e]
    :
    ee=atom{e=ee;} ('*' ee=atom{e = new MultiExpression(e,ee);})*
    ;

atom returns [Expression e]
@init
{
    e = new MultiExpression(null,null); //TODO: implement rest of code, fix backtracking
}
    :
    (ee=arrayexpr)|
    (ee=callexpr)|
    (ee=varexpr)|
    (ee=nestedexpr)|
    (ee=literalexpr)
    ;

arrayexpr: ID '[' expr ']'
    ;

callexpr: ID '(' exprList ')'
    ;

varexpr: ID
    ;

nestedexpr: '(' expr ')'
    ;

literalexpr : (STRINGCONSTANT)|
              (INTERGERCONSTANT)|
              (CHARACTERCONSTANT)|
              (FLOATCONSTANT)|
              (TRUE)|
              (FALSE)
    ;

exprList: expr exprMore*
    ;

exprMore: ',' + expr
    ;

identifier returns [Identifier s]
    :
    i=ID{s = new Identifier(i.getText());}
	;

type returns [Type t]:
    (INTTYPE{t = new IntergerType();}) |
    (FLOATTYPE{t = new FloatType();}) |
    (CHARTYPE{t = new CharType();}) |
    (STRINGTYPE{t = new StringType();}) |
    (BOOLTYPE{t = new BooleanType();}) |
    (VOIDTYPE{t = new VoidType();})
	;

/* Lexersection */
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

FALSE : 'false'
    ;

INTTYPE	: 'int'
	;

FLOATTYPE : 'float'
	;

CHARTYPE : 'char'
	;

STRINGTYPE : 'string'
	;

BOOLTYPE : 'boolean'
	;

VOIDTYPE : 'void'
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
