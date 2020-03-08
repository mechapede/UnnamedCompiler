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
    d=functionDecl{f.decl = d;f.setTokenLine(d.getTokenLine());f.setTokenChar(d.getTokenChar());}
    b=functionBody{f.body = b;}
	;

functionDecl returns [FunctionDeclaration fd]
@init
{
    fd = new FunctionDeclaration();
}
    :
    t=compoundType{fd.type = t;fd.setTokenLine(t.getTokenLine());fd.setTokenChar(t.getTokenChar());}
    i=identifier{fd.id = i;}
    '(' (p=formalParameters{fd.pl = p;})? ')'
	;

formalParameters returns [FormalParameterList pl]
@init
{
    pl = new FormalParameterList();
}
    :
    f1=formalParameter{pl.addParameter(f1);pl.setTokenLine(f1.getTokenLine());pl.setTokenChar(f1.getTokenChar());}
    (fn=moreFormals{pl.addParameter(fn);})*
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
    c=compoundType{fp.type = c; fp.setTokenLine(c.getTokenLine()); fp.setTokenChar(c.getTokenChar());}
    i=identifier{fp.id = i;}
    ;

functionBody returns [FunctionBody fb]
@init
{
    fb = new FunctionBody();
}
    :
    k='{'{fb.setTokenLine(k.getLine()); fb.setTokenChar(k.getCharPositionInLine());}
    (v=vardecl{fb.addDeclaration(v);})*
    (s=statement{if(s!= null) fb.addStatement(s);})* '}' //filter out empty statements
	;

vardecl returns [VariableDeclaration va]
@init
{
    	va = new VariableDeclaration();
}
    :
    t=compoundType{va.type = t;va.setTokenLine(t.getTokenLine()); va.setTokenChar(t.getTokenChar());}
    i=identifier{va.id = i;} ';'
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
        t = new ArrayType(t,array_size,t.getTokenLine(),t.getTokenChar());
    }
}
    :
    tb=type{t=tb;}
    ( '[' n=INTERGERCONSTANT{array=true; array_size=new IntergerLiteral(Integer.parseInt(n.getText()),n.getLine(),n.getCharPositionInLine());} ']' )?
    ;

statement returns [Statement s]
    :
    ((ss=emptystatement)|
    (ss=ifstatement)|
    (ss=whilestatement)|
    (ss=printstatement)|
    (ss=printlnstatement)|
    (ss=returnstatement)|
    ((identifier '=' expr ';') => (ss=assignmentstatement))|
    ((identifier '[' expr ']' '=' expr ';') => (ss=arrayassignment))|
    (ss=exprstatement)) {s=ss;}
    ;

emptystatement returns [Statement s]
@init
{
    s = null; // this object will be excluded in further rules
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
    j=IF{f.setTokenLine(j.getLine());f.setTokenChar(j.getCharPositionInLine());} '(' e=expr{f.cond = e;} ')'
    b=block{f.block = b;}
    (ELSE be=block{f.elseblock = be;})?
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
    j=WHILE{w.setTokenLine(j.getLine());w.setTokenChar(j.getCharPositionInLine());}
    '(' e=expr{w.cond = e;} ')' b=block{w.block = b;}
    ;

printstatement returns [Statement s]
    :
    j=PRINT e=expr{s = new PrintStatement(e,j.getLine(),j.getCharPositionInLine());} ';'
    ;

printlnstatement returns [Statement s]
    :
    j=PRINTLN e=expr{s = new PrintLnStatement(e,j.getLine(),j.getCharPositionInLine());} ';'
    ;

returnstatement returns [Statement s]
@init
{
    s = null;
    ReturnStatement r = new ReturnStatement();
}
@after
{
    s = r;
}
    :
    j=RETURN{r.setTokenLine(j.getLine()); r.setTokenChar(j.getCharPositionInLine());} 
    (e=expr{r.expression = e;})? 
    ';'
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
    i=identifier{as.id = i;as.setTokenLine(i.getTokenLine()); as.setTokenChar(i.getTokenChar());}
    '=' e=expr{as.value = e;} ';'
    ;

arrayassignment returns [Statement s]
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
    ( (i=identifier{as.id = i; as.setTokenLine(i.getTokenLine()); as.setTokenChar(i.getTokenChar());}
    '[' index=expr{as.index = index;} ']' '=' v=expr{as.value = v;})) ';'
    ;

exprstatement returns [Statement s]
    :
    e=expr{s = new ExpressionStatement(e,e.getTokenLine(),e.getTokenChar());} ';'
    ;

block returns [Block b]
@init
{
    b = new Block();
}
    :
    j='{'{b.setTokenLine(j.getLine()); b.setTokenChar(j.getCharPositionInLine());}
    (s=statement{if(s!= null) b.addStatement(s);})* '}' //filter out empty statements
    ;

expr returns [Expression e]
    :
    ee=ltop{e = ee;} (z='==' ee=ltop{e = new EqualityExpression(e,ee,z.getLine(),z.getCharPositionInLine());})*
    ;

ltop returns [Expression e]
    :
    ee=addop{e = ee;} (z='<' ee=addop{e = new LessThanExpression(e,ee,z.getLine(),z.getCharPositionInLine());})*
    ;

addop returns [Expression e]
    :
    ee=multiop{e=ee;} (z='+' ee=multiop{e = new AddExpression(e,ee,z.getLine(),z.getCharPositionInLine());} |
                       z='-' ee=multiop{e = new SubtractExpression(e,ee,z.getLine(),z.getCharPositionInLine());} )*
    ;

multiop returns [Expression e]
    :
    ee=atom{e=ee;} (z='*' ee=atom{e = new MultiExpression(e,ee,z.getLine(),z.getCharPositionInLine());})*
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
    boolean isFunction = false;
    ExpressionList list = null;
    Expression index  = null;
    Identifier id = null;
}
@after
{
    if( isFunction ){
      e = new FunctionCall(id,list,id.getTokenLine(),id.getTokenChar());
    }else if( index != null ) {
      e = new ArrayValue(id,index,id.getTokenLine(),id.getTokenChar());
    } else{
      e = new IdentifierValue(id,id.getTokenLine(),id.getTokenChar());
    }
}
    :
    i=identifier{id = i;} ( '[' ei=expr{index=ei;} ']' | '(' (el=exprList{list=el;})? ')'{isFunction=true;} )?
    ;


literalexpr returns [Expression e]
    :
    (n=STRINGCONSTANT{e = new StringLiteral(n.getText(),n.getLine(),n.getCharPositionInLine());})|
    (n=INTERGERCONSTANT{e = new IntergerLiteral(Integer.parseInt(n.getText()),n.getLine(),n.getCharPositionInLine());})|
    (n=CHARACTERCONSTANT{e = new CharacterLiteral(n.getText().charAt(1),n.getLine(),n.getCharPositionInLine());})|
    (n=FLOATCONSTANT{e = new FloatLiteral(Float.parseFloat(n.getText()),n.getLine(),n.getCharPositionInLine());})|
    (n=TRUE{e = new BooleanLiteral(true,n.getLine(),n.getCharPositionInLine());})|
    (n=FALSE{e = new BooleanLiteral(false,n.getLine(),n.getCharPositionInLine());})
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
    i=ID{s = new Identifier(i.getText(),i.getLine(),i.getCharPositionInLine());}
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
