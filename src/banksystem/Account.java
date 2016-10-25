package banksystem;

import java.util.ArrayList;

/**
 *
 * @author asanilssonenglund
 */
public abstract class Account  {
    
    protected int accountNumber;
    protected double balance;
    protected ArrayList <Transaction> transaction;
    protected String accountType;
    protected double interestRate;
    
    
    public Account(){
        // default
    }
    
    public Account(int accountNumber, double balance, double interestRate){
        this.accountNumber=accountNumber;
        this.balance=balance;
        this.interestRate=interestRate;
    }
    
    
    public int getAccountNumber(){
        return accountNumber;
    }

    /**
     * @param accountNumber the accountNumber to set
     */
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * @return the transaction
     */
    public ArrayList <Transaction> getTransaction() {
        return transaction;
    }

    /**
     * @param transaction the transaction to set
     */
    public void setTransaction(ArrayList <Transaction> transaction) {
        this.transaction = transaction;
    }

    /**
     * @return the accountType
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * @param accountType the accountType to set
     */
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    
    
    
}
