package banksystem;

/**
 *
 * @author asanilssonenglund
 */
public class CreditAccount extends Account {
    
    private final double creditLimit = 5000;
    private final double debtInterest = 1.07;
    private double amount; //textfield value scene2
    
       public CreditAccount(){
       super(); // default constructor
        
    }
    
    public CreditAccount(double balance, double interestRate){
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
    public double closeCreditAccount(double balance, double debtInterest){
    if(balance>=0)
    balance*=debtInterest;
       
    return balance;
    }
    public String getAccountName(){
    return "Credit Account";    
    }
    
}