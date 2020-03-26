package ast;

public abstract class Type extends TreeNode {

  public Type() {
    super();
  }

  public Type(int tokenline, int tokenchar) {
    super(tokenline,tokenchar);
  }


  public boolean equals(Type other) {
    return this.getClass() == other.getClass();
  }

}
