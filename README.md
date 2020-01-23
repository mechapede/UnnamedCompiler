# UnnamedCompiler
Compiler for Csc435

CLASSPATH=/usr/share/java/stringtemplate.jar:/usr/share/java/stringtemplate4.jar:/usr/share/java/antlr3.jar:/usr/share/java/antlr3-runtime.jar
javac -cp .:$CLASSPATH Compiler.java
java -cp .:$CLASSPATH Compiler foo

type returns [Type t]:
    (i=INTTYPE{t = new IntegerType(Integer.parseInt(i.getText()));}) |
    (i=FLOATTYPE{t = new FloatType(Float.parseFloat(i.getText()));}) |
    (i=CHARTYPE{t = new CharType(i.getText().charAt(0));}) |
    (i=STRINGTYPE{t = new StringType(i.getText())}) |
    (i=BOOLTYPE) |
    (i=VOIDTYPE)
	;
