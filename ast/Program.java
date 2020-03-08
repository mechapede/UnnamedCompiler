package ast;

import java.util.ArrayList;

public class Program extends TreeNode {
    private ArrayList<Function> functions;

    public Program() {
        functions = new ArrayList<Function>();
    }

    public void addFunction(Function f) {
        functions.add(f);
    }

    public Function getFunction(int index) {
        return functions.get(index);
    }

    public int getFunctionCount() {
        return functions.size();
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
