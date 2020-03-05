package inter;

public class IRPrint extends IRStatement {
    Temporary contents;
    
    public IRPrint(Temporary contents){
        this.contents = contents;
    }
}
