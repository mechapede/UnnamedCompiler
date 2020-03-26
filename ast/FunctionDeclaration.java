package ast;

public class FunctionDeclaration extends TreeNode {
  public Type type;
  public Identifier id;
  public FormalParameterList pl;

  public FunctionDeclaration() {
    this.type = null;
    this.id = null;
    this.pl = null;
  }

  public Object accept(Visitor v) {
    return v.visit(this);
  }

}
