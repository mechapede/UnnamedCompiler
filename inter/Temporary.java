package inter;

public class Temporary {
        public enum Type {
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
        
        public static String TypeConvert(Type t){ //TODO: combine into toString of enum
            switch(t){
                case VOID:
                    return "V";
                case INT:
                    return "I";
                case CHAR:
                    return "C";
                case FLOAT:
                    return "F";
                case STRING:
                    return "U";
                case BOOL:
                    return "Z";
                case ARRAY_INT:
                    return "AI";
                case ARRAY_CHAR:
                    return "AC";
                case ARRAY_FLOAT:
                    return "AF";
                case ARRAY_STRING:
                    return "AU";
                case ARRAY_BOOL:
                    return "AZ";
            }
            return null;
        }

        public enum Use {
            PARAMETER,
            LOCAL
        };

        public Type type;
        public Use use;
        public String name;
        public Temporary(Use use, Type type, String name) {
            this.use = use;
            this.type = type;
            this.name = name;
        }

}
