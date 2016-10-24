package banksystem;

// @author Anton
 
public class Customer {
    
    private String name;
    private long pNr;
    
    public Customer(String name, long pNr){
        this.name = name;
        this.pNr = pNr;
    }

    public String getName() {
        return name;
    }

    public long getpNr() {
        return pNr;
    }
    
}
