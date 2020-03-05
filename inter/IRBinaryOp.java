package inter;

public class IRBinaryOp extends IRStatement {
        public enum Op {
            ADD, //gets type op from input1
            SUB,
            MUL,
            EQUAL,
            LT
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

}
