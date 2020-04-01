# UnnamedCompiler by Matthew MacDonald
Compiler for Csc435

Instructions
1) Add antlr to the CLASSPATH environment variable.
2) Run the makefile (run `make` in directory), there should be no errors
3) To run the testcases, run runtests.py (requires python version >= 3.7) (no built in tests for IR part)
4) To run do: java Compiler FILENAME
5) If there is an error, an error message will be outputted.
6) Otherwise, the outputed Jasmin JVM assembly will be produced.
7) Running Jasmin on this code produces the .class file

Code Structure
1) The Compiler is a wrapper for running all other code
2) ulNoActions contains all the grammer and anotation information
3) The ast directory includes the visitors and the tree nodes, the inter directory holds the IR representaion
4) The test directory contains accept and reject tests
5) PrintVisitor is for debuging and TypeVisitor for type and variable checking
8) The TypeVisitor checks the types acording to spec
9) The IRVisitor produces IR code, translating the AST into the inter/* class files, which produces a flat tree
10) The JasminIRVisitor produces the Jasmin code, which is outputed to the console.
