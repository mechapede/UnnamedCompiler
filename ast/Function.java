package ast;

public class Function extends TreeNode {
  public FunctionDeclaration decl;
  public FunctionBody body;

  public Function() {
    this.decl = null;
    this.body = null;
  }

  public Object accept(Visitor v) {
    return v.visit(this);
  }

}
