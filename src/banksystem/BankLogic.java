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

}
