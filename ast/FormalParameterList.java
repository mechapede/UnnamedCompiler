package ast;

import java.util.ArrayList;

public class FormalParameterList extends TreeNode{
    ArrayList<FormalParameter> parameters;

    public FormalParameterList(){
        parameters = new ArrayList<FormalParameter>();
    }

    public void addParameter(FormalParameter f){
        parameters.add(f);
    }

    public FormalParameter getParameter(int index){
        return parameters.get(index);
    }

    public int getParameterCount(){
        return parameters.size();
    }

    public Object accept(Visitor v){
         return v.visit(this);
    }

}
