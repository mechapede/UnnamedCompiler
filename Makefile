#
GNAME= ulNoActions
GSRC= $(GNAME).g
CLASSPATH=/usr/share/java/stringtemplate.jar:/usr/share/java/stringtemplate4.jar:/usr/share/java/antlr3.jar:/usr/share/java/antlr3-runtime.jar

all: grammar compiler

grammar: $(GSRCS)
	antlr3 -fo . $(GSRC)

compiler:
	javac -cp .:$(CLASSPATH) *.java

clean:
	rm *.class $(GNAME)*.java $(GNAME).tokens ast/*.class

run:
	java -cp .:$(CLASSPATH) Compiler
