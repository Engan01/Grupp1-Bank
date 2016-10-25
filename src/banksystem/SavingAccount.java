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
    
    public SavingAccount(int accountNumber, double balance, double interestRate){
        super(accountNumber, balance, interestRate);
    }
   
}
