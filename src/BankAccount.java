public class BankAccount {
    private Balance balance;
    private final long dailyLimit;
    private long transferredToday = 0L;
    private final double feePercent;

    public BankAccount(Balance bal, long dailyLimit, double feePercent) {
        if (bal == null) {
            throw new IllegalArgumentException("Не указан баланс");
        }
        if (dailyLimit < 0) {
            throw new IllegalArgumentException("Лимит не может быть отрицательным");
        }
        if (feePercent < 0) {
            throw new IllegalArgumentException("Комиссия не может быть отрицательной");
        }

        this.balance = bal;
        this.dailyLimit = dailyLimit;
        this.feePercent = feePercent;
    }

    public void deposit(Balance amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Не указана сумма");
        }
        balance = balance.add(amount);
    }

    public void transfer(BankAccount receiver, Balance amount) {
        if (receiver == null) {
            throw new IllegalArgumentException("Нет получателя");
        }
        if (receiver == this) {
            throw new IllegalArgumentException("Отправка самому себе");
        }
        if (amount == null) {
            throw new IllegalArgumentException("Не указана сумма");
        }

        if (transferredToday + amount.getSumma() > dailyLimit) {
            throw new IllegalArgumentException("Превышен дневной лимит на переводы");
        }

        long feeKopecks = (long) (amount.getSumma() * (feePercent / 100.0));
        long totalToDeduct = amount.getSumma() + feeKopecks;

        if (balance.getSumma() < totalToDeduct) {
            throw new IllegalArgumentException("Недостаточно средств с учетом комиссии");
        }

        Balance deduction = new Balance(totalToDeduct, Currency.RUB);
        this.balance = this.balance.subtract(deduction);
        receiver.deposit(amount);

        transferredToday = Math.addExact(transferredToday, amount.getSumma());
    }

    public void resetLimitToday() {
        transferredToday = 0L;
    }

    public Balance getBalance() {
        return balance;
    }
}