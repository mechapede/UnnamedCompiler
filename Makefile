#
GNAME= ulNoActions
GSRC= $(GNAME).g

all: grammar compiler

grammar: $(GSRCS)
	java org.antlr.Tool -fo . $(GSRC)

compiler:
	javac Compiler.java

clean:
	rm *.class $(GNAME)*.java $(GNAME).tokens ast/*.class inter/*.class

