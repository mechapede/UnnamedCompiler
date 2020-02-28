package inter;

public class Temporary{
    enum Type{
        INT,
        CHAR,
        FLOAT,
        STRING,
        ARRAY_INT,
        ARRAY_CHAR,
        ARRAY_FLOAT,
        ARRAY_STRING
    }
    
    enum Use{
        PARAMETER,
        LOCAL
    }
    
    Type t;
    Use u;
    public class Temporary(Use u, Type t){
        this.u = u;
        this.t = t;
    }
    
}
