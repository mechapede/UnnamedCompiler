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
    d=functionDecl{f.setDeclaration(d);
                   //f.setTokenLine(d.getLine()); //TODO: pushup line numbers
                   //f.setTokenChar(d.getCharPositionInLine());
                   }
    b=functionBody{f.setBody(b);}
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
    IntergerLiteral array_size = null;
}
@after
{
    if(array){
        t = new ArrayType(t,array_size);
    }
}
    :
    tb=type{t=tb;} ( '[' n=INTERGERCONSTANT{array=true; array_size=new IntergerLiteral(Integer.parseInt(n.getText()));} ']' )?
    ;

statement returns [Statement s]
    :
    ((ss=emptystatement)|
    (ss=ifstatement)|
    (ss=whilestatement)|
    (ss=printstatement)|
    (ss=printlnstatement)|
    (ss=returnstatement)|
    (ss=assignmentstatement)|
    (ss=arrayassignmentexprstatement)) {s=ss;}
    ;

emptystatement returns [Statement s]
@init
{
    s = null; //TODO: exclude this from block and functionbody
}
    :
    ';'
    ;

ifstatement returns [Statement s]
@init
{
    s = null;
    IfStatement f =  new IfStatement();
}
@after
{
    s = f;
}
    :
    IF '(' e=expr{f.setExpression(e);} ')' b=block{f.setBlock(b);} (ELSE be=block{f.setElseBlock(be);})?
    ;

whilestatement returns [Statement s]
@init
{
    s = null;
    WhileStatement w = new WhileStatement();
}
@after
{
    s = w;
}
    :
    WHILE '(' e=expr{w.setExpression(e);} ')' b=block{w.setBlock(b);}
    ;

printstatement returns [Statement s]
    :
    PRINT e=expr{s = new PrintStatement(e);} ';'
    ;

printlnstatement returns [Statement s]
    :
    PRINTLN e=expr{s = new PrintLnStatement(e);} ';'
    ;

returnstatement returns [Statement s]
    :
    RETURN e=expr{s = new ReturnStatement(e);} ';'
    ;

assignmentstatement returns [Statement s]
@init
{
    s = null;
    AssignmentStatement as = new AssignmentStatement();
}
@after
{
    s = as;
}
    :
    i=identifier{as.setIdentifier(i);} '=' e=expr{as.setExpression(e);} ';'
    ;

arrayassignmentexprstatement returns [Statement s]
@init
{
    s = null;
    ArrayAssignment as = new ArrayAssignment();
}
@after
{
    s = as;
}
    :
    ( (identifier '[' expr ']' '=' expr ) => (i=identifier{as.setIdentifier(i);} '[' index=expr{as.setIndexExpression(index);} ']' '=' v=expr{as.setExpression(v);}) | expr ) ';'
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
    ee=ltop{e = ee;} (z='==' ee=ltop{e = new EqualityExpression(z.getLine(),z.getCharPositionInLine(),e,ee);})*
    ;

ltop returns [Expression e]
    :
    ee=addop{e = ee;} (z='<' ee=addop{e = new LessThanExpression(z.getLine(),z.getCharPositionInLine(),e,ee);})*
    ;

addop returns [Expression e]
    :
    ee=multiop{e=ee;} (z='+' ee=multiop{e = new AddExpression(z.getLine(),z.getCharPositionInLine(),e,ee);} |
                       z='-' ee=multiop{e = new SubtractExpression(z.getLine(),z.getCharPositionInLine(),e,ee);} )*
    ;

multiop returns [Expression e]
    :
    ee=atom{e=ee;} (z='*' ee=atom{e = new MultiExpression(z.getLine(),z.getCharPositionInLine(),e,ee);})*
    ;

atom returns [Expression e]
    :
    ((ee=getexpr)|
    (ee=literalexpr)
    | ('(' ee=expr ')')) {e = ee;}
    ;

getexpr returns [Expression e]
@init
{
    ExpressionList list = null;
    Expression index  = null;
    Identifier id = null;
}
@after
{
    if( list != null){
      e = new FunctionCall(id,list);
    }else if( index != null ) {
      e = new ArrayValue(id,index);
    } else{
      e = new IdentifierValue(id);
    }
}
    :
    i=identifier{id = i;} ( '[' ei=expr{index=ei;} ']' | '(' el=exprList{list=el;} ')' )?
    ;


literalexpr returns [Expression e]
    :
    (n=STRINGCONSTANT{e = new StringLiteral(n.getText());})|
    (n=INTERGERCONSTANT{e = new IntergerLiteral(Integer.parseInt(n.getText()));})|
    (n=CHARACTERCONSTANT{e = new CharacterLiteral(n.getText().charAt(1));})|
    (n=FLOATCONSTANT{e = new FloatLiteral(Float.parseFloat(n.getText()));})|
    (n=TRUE{e = new BooleanLiteral(true);})|
    (n=FALSE{e = new BooleanLiteral(false);})
    ;

exprList returns [ExpressionList el]
@init
{
    el = new ExpressionList();
}
    :
    e=expr{el.addExpression(e);} (e=exprMore{el.addExpression(e);})*
    ;

exprMore returns [Expression e]
    :
    ',' + ee=expr{e=ee;}
    ;

identifier returns [Identifier s]
    :
    i=ID{s = new Identifier(i.getLine(),i.getCharPositionInLine(),i.getText());}
	;

type returns [Type t]:
    (l=INTTYPE{t = new IntergerType(l.getLine(),l.getCharPositionInLine());}) |
    (l=FLOATTYPE{t = new FloatType(l.getLine(),l.getCharPositionInLine());}) |
    (l=CHARTYPE{t = new CharType(l.getLine(),l.getCharPositionInLine());}) |
    (l=STRINGTYPE{t = new StringType(l.getLine(),l.getCharPositionInLine());}) |
    (l=BOOLTYPE{t = new BooleanType(l.getLine(),l.getCharPositionInLine());}) |
    (l=VOIDTYPE{t = new VoidType(l.getLine(),l.getCharPositionInLine());})
	;

/* Lexer section */
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
