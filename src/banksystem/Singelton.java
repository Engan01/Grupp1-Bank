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
    private String n2;
    private Long l;
    private int i;
    

    private double d;
    private double d2;
    private double dT;
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

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public String getN2() {
        return n2;
    }

    public void setN2(String n2) {
        this.n2 = n2;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }

    public double getD2() {
        return d2;
    }

    public void setD2(double d2) {
        this.d2 = d2;
    }

    public double getdT() {
        return dT;
    }

    public void setdT(double dT) {
        this.dT = dT;
    }
    
    

}
