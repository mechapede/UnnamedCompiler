package ast;

import java.util.ArrayList;

public class FunctionBody {
    ArrayList<VariableDeclaration> declarations;
    ArrayList<Statement> statements;

    public FunctionBody(){
        this.declarations = new ArrayList<VariableDeclaration>();
        this.statements = new ArrayList<Statement>();
    }

    public void addDeclaration(VariableDeclaration d){
        declarations.add(d);
    }

    public VariableDeclaration getDeclaration(int index){
        return declarations.get(index);
    }

    public int getDeclarationCount(){
        return declarations.size();
    }

    public void addStatement(Statement s){
        statements.add(s);
    }

    public Statement getStatement(int index){
        return statements.get(index);
    }

    public int getStatementCount(){
        return statements.size();
    }

    public Object accept(Visitor v){
         return v.visit(this);
    }

}
