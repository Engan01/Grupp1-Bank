package banksystem;

/**
 *
 * @author asanilssonenglund
 */
public class SavingAccount extends Account{
    
    
    private int numberOfWithdraw = 1;
    private double amount;
    
    
    public SavingAccount(){
        super(); // default constructor
    }
    
    public SavingAccount(double balance, double interestRate){
        super(balance, interestRate);
    }
       public double deposit(double balance){
        balance+=amount;
        return balance;
    }
    
    public double withdraw(double balance, double creditLimit){
        balance-=amount;
        return balance;

    }
    public String getAccountName(){
    return "Saving Account";    
    }
}

