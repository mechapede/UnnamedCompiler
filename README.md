# UnnamedCompiler by Matthew MacDonald
Compiler for Csc435

Instructions
1) Add antlr to the CLASSPATH environment variable.
2) Run the makefile (run `make` in directory), there should be no errors
3) To run the testcases, run runtests.py (requires python version >= 3.7)
4) To run do: java Compiler FILENAME
5) If there is an error, an error message will be outputted. Otherwise, no output will be provided.

Code Structure
1) The Compiler
2) ulNoActions contains all the grammer and anotation information
3) The ast directory includes the visitors and the tree nodes
4) The test directory contains accept and reject tests
5) PrintVisitor is for debuging and TypeVisitor for type and variable checking
