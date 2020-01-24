package ast;

public class IdentifierValue extends Expression{
  Identifier identifier;

  public IdentifierValue(Identifier identifier){
      this.identifier = identifier;
  }

}
