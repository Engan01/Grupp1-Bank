package banksystem;

/**
 *
 * @author asanilssonenglund
 */
public class SavingAccount extends Account {

    private int numberOfWithdraw = 1;
    private static double interest = 0.01;
//    private double amount;

    public SavingAccount() {
        super(); // default constructor
    }

    public SavingAccount(double balance, double interestRate) {
        super(balance, interestRate);
    }

    public double deposit(double balance, double amount) {
        balance += amount;
        return balance;
    }

    public double withdraw(double balance, double amount) {
        if (numberOfWithdraw < 1) {
            balance -= amount;
            numberOfWithdraw++;
        } else {
            balance *= interestRate;
            balance -= amount;
            numberOfWithdraw++;
        }
        return balance;

    }

    @Override
    public String getAccountName() {
        return "Saving Account";
    }

    @Override
    public double getInterest() {
        return interest;
    }
}
