package inter;

public class IRBinaryOp extends IRStatement {
        public enum Op {
            ADD {
                public String toString() {
                    return "+";
                }
            },
            SUB {
                public String toString() {
                    return "-";
                }
            },
            MUL {
                public String toString() {
                    return "*";
                }
            },
            EQUAL {
                public String toString() {
                    return "==";
                }
            },
            LT {
                public String toString() {
                    return "<";
                }
            }
        }

        Temporary result;
        Temporary input1;
        Temporary input2;
        Op operation;

        public IRBinaryOp(Temporary result, Temporary input1, Temporary input2, Op operation) {
            this.result = result;
            this.input1 = input1;
            this.input2 = input2;
            this.operation = operation;
        }
        
        public String toString(){
            return "  " + result + " := " + input1 + " " + operation + " " + input2;
        }
}
