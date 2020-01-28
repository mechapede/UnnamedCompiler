# UnnamedCompiler by Matthew MacDonald
Compiler for Csc435

Instructions
1) Add antlr to the CLASSPATH environment variable.
2) Run the makefile (run `make` in directory), there should be no errors
3) To run the testcases, run test.py (requires python version >= 3.7)
4) To run do: java Compiler FILENAME
5) Output of the AST will be outputted, or error messages if it fails

Code Structure
1) The Compiler
2) ulNoActions contains all the grammer and anotation information
3) The ast directory includes the visitors and the tree nodes
4) The test directory contains accept and reject tests
