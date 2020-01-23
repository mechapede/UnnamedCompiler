package ast;

import java.util.ArrayList;

public class Block{
    ArrayList<Statement> statements;

    public Block(){
        statements = new ArrayList<Statement>();
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

}
