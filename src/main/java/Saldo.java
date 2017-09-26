import java.math.BigDecimal;

public class Saldo {
    public BigDecimal before;
    public BigDecimal after;

    public Saldo(BigDecimal before, BigDecimal after) {
        this.before = before;
        this.after = after;
    }

    public Saldo(String before, String after) {
        this.before = new BigDecimal(before.replace(",", "."));
        this.after = new BigDecimal(after.replace(",", "."));;
    }
}