package inter;


public class IRUnaryOp extends IRStatement {
        public enum Op {
            NEGATE {
                public String toString(){
                    return "!";
                }
            }
        }

        Temporary result;
        Temporary input;
        Op operation;

        public IRUnaryOp(Temporary result, Temporary input, Op operation) {
            this.result = result;
            this.input = input;
            this.operation = operation;
        }
        
        public String toString(){
            return "  " + result + " := " + operation + input;
        }
}
