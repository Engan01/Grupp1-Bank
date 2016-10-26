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
    }

    public String getName() {
        return name;
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
    
//    public String[] closeAllAccounts(){
//        
//    }
}
