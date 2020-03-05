package inter;


public class IRUnaryOp extends IRStatement {
        public enum Op {
            BOOL_NEGATE //TODO: other ops
        }

        Temporary result;
        Temporary input;
        Op operation;

        public IRUnaryOp(Temporary result, Temporary input, Op operation) { //TODO:
            this.result = result;
            this.input = input;
            this.operation = operation;
        }

}
