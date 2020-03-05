#
GNAME= ulNoActions
GSRC= $(GNAME).g

all: grammar compiler

grammar:
	java org.antlr.Tool -fo . $(GSRC)

.PHONY: compiler
compiler:
	javac Compiler.java

.PHONY: clean
clean:
	rm *.class $(GNAME)*.java $(GNAME).tokens ast/*.class inter/*.class

