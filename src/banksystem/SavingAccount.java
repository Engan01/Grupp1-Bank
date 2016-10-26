package banksystem;

/**
 *
 * @author asanilssonenglund
 */
public class SavingAccount extends Account{
    
    
    private int numberOfWithdraw = 1;
    
    
    public SavingAccount(){
        super(); // default constructor
    }
    
    public SavingAccount(double balance, double interestRate){
        super(balance, interestRate);
    }
   
}
