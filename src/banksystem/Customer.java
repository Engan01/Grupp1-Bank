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
        addSavingAccount(0);
        
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
    
    public int addSavingAccount(double balance){
        SavingAccount sA = new SavingAccount(balance);
        int nr = sA.getAccountNumber();
        accounts.add(sA);
        return nr;    
    }
    
    public int addCheckingAccount(double balance){
        CreditAccount cA = new CreditAccount(balance);
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
    //
    public void deleteAccount(int aNr){
        accounts.remove(getSelectedAccount(aNr));
    }
}
