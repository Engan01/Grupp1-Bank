package banksystem;

// @author Anton

import java.util.ArrayList;

 
public class BankLogic {
    
    private ArrayList<Customer> customersList = new ArrayList<>();
    
    private static BankLogic instance;
    private BankLogic(){}
    
    
    
    public static BankLogic getInstance(){
        if(instance == null)
            instance = new BankLogic();
        return instance;
    }
    
    public String[] getCustomers(){ // metod för att returnera samtliga namn på alla kunder i en lista
        String[] customers = new String[customersList.size()];
        for(Customer c : customersList){
            int i = 0;
            customers[i] = c.getName();
            i++;
        }
        return customers; // returnar en string list med alla namn
    }
    
    public boolean addCustomer(String name, long pNr){ // metod för att lägga till kund
        
        boolean b = searchCustomer(pNr); // kontrollerar om kunden redan existerar
        
        if(b) // om kunden existerar returnas false
            return false;
        
        customersList.add(new Customer(name, pNr)); // om kunden ej finns skapas en ny kund
        return true;
        
    }
    
    public boolean searchCustomer(long pNr){ // metod för att söka efter kunder med personnumer
       
        for(Customer c : customersList){ // löper igenom lista med kunder
            long p = c.getPnr(); 
            if(pNr == p) //om personnumret finns returneras true
                return true;
        }
        return false; // om kunden inte finns returneras false
    }
    
    public String[] getCustemer(long pNr){
        String[] lista;
        for(Customer c : customersList){
            long p = c.getPnr();
            if(p == pNr){
                
                break;
            }
            
        }
        
        
    }

}
