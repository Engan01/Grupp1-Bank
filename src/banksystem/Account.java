package banksystem;

import java.util.ArrayList;

/**
 *
 * @author asanilssonenglund
 */
public abstract class Account  {
    
    private static int accountNumberAll = 1001;
    
    private int accountNumber;
    private double balance = 0;
    private ArrayList <Transaction> transaction = new ArrayList<>();
    private String accountType;
   
    
    
    public Account(){
        // default
    }
    
    public Account(double balance){
        
        this.balance = balance;
        
        setAccountNumber();
    }
    
    
    public int getAccountNumber(){
        return accountNumber;
    }
    
    public void setAccountNumber() {
        this.accountNumber = accountNumberAll;
        accountNumberAll++;
    }


    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


    public ArrayList <Transaction> getTransaction() {
        return transaction;
    }


    public void setTransaction(ArrayList <Transaction> transaction) {
        this.transaction = transaction;
    }


    public String getAccountType() {
        return accountType;
    }


    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    
    public abstract String getAccountName();
    
    public abstract double getInterest();

}
