package inter;

public class Temporary {
    public enum Type {
        VOID(null) {
            public String toString() {
                return "V";
            }
        },
        INT(null) {
            public String toString() {
                return "I";
            }
        },
        CHAR(null) {
            public String toString() {
                return "C";
            }
        },
        FLOAT(null) {
            public String toString() {
                return "F";
            }
        },
        STRING(null) {
            public String toString() {
                return "U";
            }
        },
        BOOL(null) {
            public String toString() {
                return "Z";
            }
        },
        ARRAY_INT(INT) {
            public String toString() {
                return "AI";
            }
        },
        ARRAY_CHAR(CHAR) {
            public String toString() {
                return "AC";
            }
        },
        ARRAY_FLOAT(FLOAT) {
            public String toString() {
                return "AF";
            }
        },
        ARRAY_STRING(STRING) {
            public String toString() {
                return "AU";
            }
        },
        ARRAY_BOOL(BOOL) {
            public String toString() {
                return "AZ";
            }
        };
        private Type subtype;
        Type(Type subtype) {
            this.subtype = subtype;
        }

        public Type subType() {
            return subtype;
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
