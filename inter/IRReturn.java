package inter;

public class IRReturn extends IRStatement {
        Temporary value; //void is null

        public IRReturn(Temporary value) {
            this.value = value;
        }

        public String toString() {
            return (value != null) ? ("  return " + value + ";")  : ("  return;");
        }
}
