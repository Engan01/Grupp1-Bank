package banksystem;

/**
 *
 * @author asanilssonenglund
 */
public class CreditAccount extends Account {

    private final double debtInterest = 0.07;
    private final double debtIncrease = 1.005;
    private static double interest = 0.005;
    // private double amount; //textfield value scene2

    public CreditAccount() {
        super(); // default constructor

    }

    public CreditAccount(double balance) {
        super(balance);
    }

    @Override
    public double deposit(double amount) {
        double balance = super.getBalance();
        balance += amount;
        super.setBalance(balance);
        return balance;
    }

    @Override
    public double withdraw(double amount) {
        double balance = super.getBalance();
        balance -= amount;
        super.setBalance(balance);
        return balance;

    }

    public double closeCreditAccount(double balance) {
        if (balance <= 0) {
            balance *= debtInterest;
        } else {
            balance *= debtIncrease;
        }

        return balance;
    }

    @Override
    public String getAccountName() {
        return "Credit Account";
    }

    @Override
    public double getInterest() {
        if(super.getBalance() < 0)
            return debtInterest;
        else
            return interest;   
    }
    
    @Override
    public double getTotalBalance(){
       double b = super.getBalance();
       if(b < 0){
          double bb = super.getBalance();
          bb = bb * 0.07;
          double minusTotal = super.getBalance() + bb;
          return minusTotal;
       }else{
           double plusTotal = super.getBalance() * 0.005 + super.getBalance();
           return plusTotal;
       }
    }

}
