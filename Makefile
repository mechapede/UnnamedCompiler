#
GNAME= ulNoActions
GSRC= $(GNAME).g

all: ast inter grammar compiler

grammar: $(GSRC)
	java org.antlr.Tool -fo . $(GSRC)

.PHONY: compiler
compiler:
	javac Compiler.java

.PHONY: ast
ast: 
	javac ast/*.java

.PHONY: inter
inter:
	javac inter/*.java

.PHONY: clean
clean:
	rm *.class $(GNAME)*.java $(GNAME).tokens ast/*.class inter/*.class

