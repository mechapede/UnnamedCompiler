package ast;

public class VariableDeclaration extends TreeNode {
  public Type type;
  public Identifier id;

  public VariableDeclaration() {
    type = null;
    id = null;
  }

  public Object accept(Visitor v) {
    return v.visit(this);
  }

}
