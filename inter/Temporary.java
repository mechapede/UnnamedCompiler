package inter;

public class Temporary {
    public enum Type {
        VOID(null,null,"V") {
            public String toString() {
                return "V";
            }
        },
        INT(null,"i","I") {
            public String toString() {
                return "I";
            }
        },
        CHAR(null,"i","C") {
            public String toString() {
                return "C";
            }
        },
        FLOAT(null,"f","F") {
            public String toString() {
                return "F";
            }
        },
        STRING(null,"a","Ljava/lang/String;") {
            public String toString() {
                return "U";
            }
        },
        BOOL(null,"i","Z") {
            public String toString() {
                return "Z";
            }
        },
        ARRAY_INT(INT,"a","[I") {
            public String toString() {
                return "AI";
            }
        },
        ARRAY_CHAR(CHAR,"a","[C") {
            public String toString() {
                return "AC";
            }
        },
        ARRAY_FLOAT(FLOAT,"a","[F") {
            public String toString() {
                return "AF";
            }
        },
        ARRAY_STRING(STRING,"a","[java/lang/String;") {
            public String toString() {
                return "AU";
            }
        },
        ARRAY_BOOL(BOOL,"a","[Z") {
            public String toString() {
                return "AZ";
            }
        };
        private Type subtype;
        private String jvmprefix;
        private String jvmType;
        private Type(Type subtype, String jvmprefix, String jvmType) {
            this.subtype = subtype;
            this.jvmprefix = jvmprefix;
            this.jvmType = jvmType;
        }

        public Type subType() {
            return subtype;
        }
        
        public String jvmType(){
            return jvmType;
        }
        
        public String jvmPrefix(){
            return jvmprefix;
        }
    };

    public enum Use {
        PARAMETER {
            public String toString() {
                return "P";
            }
        },
        LOCAL {
            public String toString() {
                return "L";
            }
        },
        TEMPORARY {
            public String toString() {
                return "T";
            }
        }
    };

    public int index; //# of parameter
    public Type type;
    public Use use;
    public String name;

    public Temporary(int index, Use use, Type type) {
        this.index = index;
        this.use = use;
        this.type = type;
        this.name = null;
    }

    public Temporary(int index, Use use, Type type, String name) {
        this.index = index;
        this.use = use;
        this.type = type;
        this.name = name;
    }

    public String toString() {
        return "" + "T" + index; //TODO: see if need P/L
    }

    public String getName() {
        return (name != null) ? ("[" + name +  "]") : "";
    }

}
