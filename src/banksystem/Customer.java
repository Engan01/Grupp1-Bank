package banksystem;

// @author Anton

import java.util.ArrayList;

 
public class Customer {
    
    private String name;
    private long pNr;
    private ArrayList<Account> accounts = new ArrayList<>();
    
    public Customer(String name, long pNr){
        this.name = name;
        this.pNr = pNr;
        addSavingAccount(100, 0.07);
        //sdgagagad
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name){
        
        String newName = name.replaceAll("[^A-Z]","");
        name=newName;
    }

    public long getPnr() {
        return pNr;
    }
    
    public int getNumberOfAccounts(){
        return accounts.size();
    } 
    
    public ArrayList<Account> getAccountList(){
        return accounts;
    }
   
    @Override
    public String toString(){
        
        String str= "Name: "+this.getName()+", Social security No: "+this.getPnr();
        return str;
    }
    
    public void addSavingAccount(double balance, double interestRate){
        accounts.add(new SavingAccount(balance, interestRate));
        
        
    }
    
    public void addCheckingAccount(double balance, double interestRate){
        
        accounts.add(new CreditAccount(balance, interestRate)); 
        
    }
    
//    public String[] closeAllAccounts(){
//        
//    }
}
