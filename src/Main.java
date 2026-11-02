public class Main {
    public static void main(String[] args) {
        BankAccount ac1 = new BankAccount(new Balance(50_000, Currency.RUB), 1_000_000, 1.0);
        BankAccount ac2 = new BankAccount(new Balance(100_000, Currency.RUB), 1_000_000, 0.0);

        System.out.println("Изначально");
        System.out.println("Счет 1: " + ac1.getBalance());
        System.out.println("Счет 2: " + ac2.getBalance());

        System.out.println("\n1. Успешный перевод 300 руб (комиссия 1%)");
        ac1.transfer(ac2, new Balance(30_000, Currency.RUB));
        System.out.println("Счет 1: " + ac1.getBalance());
        System.out.println("Счет 2: " + ac2.getBalance());

        System.out.println("\n2. Ошибка лимита (попытка перевести 9800 руб)");
        try {
            ac1.transfer(ac2, new Balance(980_000, Currency.RUB));
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        System.out.println("\n3. Ошибка перевода самому себе");
        try {
            ac1.transfer(ac1, new Balance(10_000, Currency.RUB));
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        System.out.println("\n4. Ошибка нехватки средств (попытка перевести 196 руб)");
        try {
            ac1.transfer(ac2, new Balance(19_600, Currency.RUB));
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}