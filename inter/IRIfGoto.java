package inter;

public class IRIfGoto extends IRStatement{
        Temporary cond;
        IRLabel label;

        public IRIfGoto(Temporary cond, IRLabel label) {
            this.cond = cond;
            this.label = label;
        }
}
