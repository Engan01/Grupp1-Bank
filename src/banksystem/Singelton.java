package banksystem;

// @author Anton
public class Singelton { // klass för att hålla värden mellan controllers!!!

    private static Singelton instance;

    private Singelton() {
    }

    public static Singelton getInstance() {
        if (instance == null) {
            instance = new Singelton();
        }
        return instance;
    }

    private Boolean b;
    private String n;
    private Long l;
    private int i;
    private double d;
    private double d1;
    private double d2;

    public void setToNull() {
        b = false;
        n = null;
        l = null;
        i = 0;
        d = 0;
        d1 = 0;
        d2 = 0;
    }

    public Boolean getB() {
        return b;
    }

    public void setB(Boolean b) {
        this.b = b;
    }

    public String getN() {
        return n;
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

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }

    public double getD1() {
        return d1;
    }

    public void setD1(double d1) {
        this.d1 = d1;
    }

    public double getD2() {
        return d2;
    }

    public void setD2(double d2) {
        this.d2 = d2;
    }

}
