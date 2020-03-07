package inter;

public class IRPrintln extends IRStatement {
    Temporary contents;
    
    public IRPrintln(Temporary contents){
        this.contents = contents;
    }
    
    public String toString(){
        return "  PRINTLN" + contents.type + " " + contents;
    }
}
