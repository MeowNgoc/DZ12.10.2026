public final class Balance {
    private final long summa;
    private final Currency cur = Currency.RUB;

    public Balance(long summa, Currency curr) {
        if (summa < 0) {
            throw new IllegalArgumentException("Сумма не может быть отрицательной");
        }
        if (curr == null) {
            throw new IllegalArgumentException("Валюта не может быть null");
        }

        long resultSumma = summa;
        if (curr != Currency.RUB) {
            resultSumma = convert(summa, curr);
        }
        this.summa = resultSumma;
    }

    private long convert(long summa, Currency curr) {
        return Math.multiplyExact(summa, curr.getPriceInRublesKopecks());
    }

    public Balance add(Balance other) {
        if (other == null) {
            throw new IllegalArgumentException("Баланс не может быть null");
        }
        long newSum = Math.addExact(this.summa, other.summa);
        return new Balance(newSum, this.cur);
    }

    public Balance subtract(Balance other) {
        if (other == null) {
            throw new IllegalArgumentException("Баланс не может быть null");
        }
        if (other.summa > this.summa) {
            throw new IllegalArgumentException("Недостаточно средств");
        }
        long newSum = Math.subtractExact(this.summa, other.summa);
        return new Balance(newSum, this.cur);
    }

    public long getSumma() {
        return summa;
    }

    public Currency getCur() {
        return cur;
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", summa / 100.0, cur.getSymbol());
    }
}