package ast;

public class IdentifierValue extends Expression{
  Identifier identifier;

  public IdentifierValue(int tokenline, int tokenchar,Identifier identifier){
      super(tokenline,tokenchar);
      this.identifier = identifier;
  }

  public Object accept(Visitor v){
       return v.visit(this);
  }

}
