package banksystem;

// @author Anton

import java.util.ArrayList;

 
public class BankLogic {
    
    private ArrayList<Customer> customers = new ArrayList<>();
    
    private static BankLogic instance;
    private BankLogic(){}
    
    public static BankLogic getInstance(){
        if(instance == null)
            instance = new BankLogic();
        return instance;
    }

}
