package ast;

public abstract class Statement extends TreeNode {

  public Statement() {
    super();
  }

  public Statement(int tokenline, int tokenchar) {
    super(tokenline,tokenchar);
  }
}
