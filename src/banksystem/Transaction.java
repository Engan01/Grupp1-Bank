package banksystem;

import java.time.LocalDateTime;



/**
 *
 * @author asanilssonenglund
 */

public class Transaction {
    private final LocalDateTime date; // yyyy-mm-ddThh:mm:ss
    private final boolean b; // true = in ------ false = out
    private final double belopp;
    private final double saldo;
    
    public Transaction(LocalDateTime date, boolean b, double belopp, double saldo){
        this.date = date;
        this.b = b;
        this.belopp = belopp;
        this.saldo = saldo;
    }
    
    
    @Override
    public String toString(){
        String s = "";
        String datum = date.toString();
        String tid = datum.substring(11, 19);
        datum = datum.substring(0, 10);
        
        if(b){
            s = datum + " " + tid + " In: " + belopp + " Balance: " + saldo;
        }else{
            s = datum + " " + tid + " Out: -" + belopp + " Balance: " + saldo;
        }
    
        return s;
    }
    
}