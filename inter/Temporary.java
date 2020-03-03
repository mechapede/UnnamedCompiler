package inter;



public class Temporary{
    public enum Type{
        VOID,
        INT,
        CHAR,
        FLOAT,
        STRING,
        BOOL,
        ARRAY_INT,
        ARRAY_CHAR,
        ARRAY_FLOAT,
        ARRAY_STRING,
        ARRAY_BOOL
    };
    
    public enum Use{
        PARAMETER,
        LOCAL
    };
    
    public Type type;
    public Use use;
    public String name;
    public Temporary(Use use, Type type, String name){
        this.use = use;
        this.type = type;
        this.name = name;
    }
    
}
