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
        
        String newName = name.replaceAll("[^A-Za-z ]","").trim();
        this.name=newName;
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
    
    public int addSavingAccount(double balance, double interestRate){
        SavingAccount sA = new SavingAccount(balance, interestRate);
        int nr = sA.getAccountNumber();
        accounts.add(sA);
        return nr;    
    }
    
    public int addCheckingAccount(double balance, double interestRate){
        CreditAccount cA = new CreditAccount(balance, interestRate);
        int nr = cA.getAccountNumber();
        accounts.add(cA);
        return nr;
        
    }
    
    public Account getSelectedAccount(int aNr){
        for(Account a : accounts){
            if(a.getAccountNumber() == aNr){
                return a;
            }
        }
        return null;
    }
    
//    public String[] closeAllAccounts(){
//        
//    }
}
