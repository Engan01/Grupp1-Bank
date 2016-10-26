package banksystem;

/**
 *
 * @author asanilssonenglund
 */
public class CreditAccount extends Account {
    
    private final double creditLimit = 5000;
    //private final double debtInterest = 0.07;
    
       public CreditAccount(){
       super(); // default constructor
        
    }
    
    public CreditAccount(double balance, double interestRate){
        super(balance, interestRate);
    }
    
}