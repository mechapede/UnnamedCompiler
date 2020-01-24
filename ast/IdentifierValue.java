package ast;

public class IdentifierValue extends Expression{
  Identifier identifier;

  public IdentifierValue(Identifier identifier){
      this.identifier = identifier;
  }

  public Object accept(Visitor v){
       return v.visit(this);
  }

}
