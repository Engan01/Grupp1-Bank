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
    private int i1;
    private int i2;
    private double d;
    private double d2;
    private double dT;
    private Boolean b2;
    
    public void setToNull(){
        b = false;
        n = null;
        n2 = null;
        l = null;
        i = 0;
        i1 = 0;
        i2 = 0;
        d = 0;
        d2 = 0;
        dT = 0;      
    }
    
    
    public Boolean getB() {
        return b;
    }

    public int getI1() {
        return i1;
    }

    public void setI1(int i1) {
        this.i1 = i1;
    }

    public int getI2() {
        return i2;
    }

    public void setI2(int i2) {
        this.i2 = i2;
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

    public Boolean getB2() {
        return b2;
    }

    public void setB2(Boolean b2) {
        this.b2 = b2;
    }
    
    

}
