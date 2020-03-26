package ast;

public class FormalParameter extends TreeNode {
  public Type type;
  public Identifier id;

  public FormalParameter() {
    type = null;
    id = null;
  }

  public Object accept(Visitor v) {
    return v.visit(this);
  }
}
