package banksystem;

// @author Anton
 
public class Singelton { // klass för att hålla värden mellan controllers!!!
    
    private static Singelton instance;
    private Singelton(){}
    
    
    
    public static Singelton getInstance(){
        if(instance == null)
            instance = new Singelton();
        return instance;
    }
    
    private Boolean b;
    private String n;
    private Long l;

    public Boolean getB() {
        return b;
    }

    public String getN() {
        return n;
    }

    public void setB(Boolean b) {
        this.b = b;
    }

    public void setN(String n) {
        this.n = n;
    }

    public Long getL() {
        return l;
    }

    public void setL(Long l) {
        this.l = l;
    } 

}
