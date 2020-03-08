package ast;

public abstract class TreeNode {
    public int tokenline;
    public int tokenchar;

    public TreeNode() {
        this.tokenline = -1;
        this.tokenchar = -1;
    }

    public TreeNode(int tokenline, int tokenchar) {
        this.tokenline = tokenline;
        this.tokenchar = tokenchar;
    }

    public void setTokenLine(int tokenline) {
        this.tokenline = tokenline;
    }

    public int getTokenLine() {
        return this.tokenline;
    }

    public void setTokenChar(int tokenchar) {
        this.tokenchar = tokenchar;
    }

    public int getTokenChar() {
        return this.tokenchar;
    }

    public Object accept(Visitor v) {
        return null;
    }
}
