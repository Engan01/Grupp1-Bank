package banksystem;

/**
 *
 * @author asanilssonenglund
 */
public class SavingsAccount extends Account {

    private int numberOfWithdraw = 0;
    private static double interest = 0.01;
//    private double amount;

    public SavingsAccount() {
        super(); // default constructor
    }

    public SavingsAccount(double balance) {
        super(balance);
    }

    @Override
    public double deposit(double amount) {
        double b = super.getBalance(); 
                b += amount;
                super.setBalance(b);
        return b;
    }

    @Override
    public double withdraw(double amount) {
        if (numberOfWithdraw < 1) {
            double d = super.getBalance(); 
                   d -= amount;
                   super.setBalance(d);
            numberOfWithdraw++;
        } else {
            double balance = super.getBalance();
            amount = amount * 0.02 + amount; // utagsränta efter första utaget = 2%
            balance -= amount;
            numberOfWithdraw++;
            super.setBalance(balance);
        }
        return super.getBalance();

    }
    
    public int getnumberOfWithdraw(){
        return numberOfWithdraw;
    }
    
    public void setnumberOfWithdraw(int numberOfWithdraw){
        this.numberOfWithdraw=numberOfWithdraw;
    }

    @Override
    public String getAccountName() {
        return "Saving Account";
    }

    @Override
    public double getInterest() {
        return interest;
    }
    
    @Override
    public double getTotalBalance(){
      Double b = super.getBalance() * 0.01 + super.getBalance();
      return b;
    }
}
