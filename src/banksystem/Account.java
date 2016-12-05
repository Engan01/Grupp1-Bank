package banksystem;

import java.util.ArrayList;

/**
 *
 * @author asanilssonenglund
 */
public abstract class Account  {        // abstrakt klass
    
    private static int accountNumberAll = 1001; // startnummer för alla konton
    
    private int accountNumber;
    private double balance = 0; // tilldelar 0 som startbelopp för på alla konton som skapas
    private ArrayList <Transaction> transaction = new ArrayList<>(); // skapar en lista där som innehåller objekt av klassen Transaction
    private String accountType;
   
    
    
    public Account(){
        // default
    }
    
    public Account(double balance){ // konstuktor för Account
        this.balance = balance;
        setAccountNumber();
    }
    
    public Account(int accountNr, double balance){
        this.accountNumber=accountNr;
        this.balance=balance;
        if(accountNr >= accountNumberAll)
        accountNumberAll = accountNr+1;      
    }
    
    
    public int getAccountNumber(){ // hämtar kontonummer
        return accountNumber;
    }
    
    public void setAccountNumber() { // tilldelar nytt kontonummer
        this.accountNumber = accountNumberAll;
        accountNumberAll++; // ökar variabeln med 1 så att varje kontonummer blir unikt
    }


    public double getBalance() { // hämtar balans på konto
        return balance;
    }

    public void setBalance(double balance) { // tilldelar balans till respektive konto
        this.balance = balance;
    }


    public ArrayList <Transaction> getTransaction() { // hämtar Lista på alla transactioner
        return transaction;
    }


    public void setTransaction(ArrayList <Transaction> transaction) { // tilldelar Lista till Transactions
        this.transaction = transaction;
    }


    public String getAccountType() { // hämtar kontotyp
        return accountType;
    }


    public void setAccountType(String accountType) { // tilldelar kontotyp
        this.accountType = accountType;
    }
    
    public void addTransaction(boolean b, double belopp, double saldo){
        transaction.add(new Transaction(b, belopp, saldo, accountNumber)); // lägger till en ny transaction i listan transactions
    }
    
   
    /* Abstrakta klasser som Credit Account & Savings Account ärver*/
    public abstract String getAccountName();
    
    public abstract double getInterest();
    
    public abstract double withdraw(double dd);
    
    public abstract double deposit(double dd);
    
    public abstract double getTotalBalance();

}
