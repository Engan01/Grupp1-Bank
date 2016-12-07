package banksystem;

import DBrepository.DBT;
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
    private final int accountNr;
    private DBT dbt = DBT.getInstance();
    
    public Transaction(boolean b, double belopp, double saldo, int accountNr){ // konstruktor
        this.date = LocalDateTime.now(); // sätter date till nu
        this.b = b; 
        this.belopp = belopp;
        this.saldo = saldo;
        this.accountNr=accountNr;
        dbt.addTransaction(date, saldo, b, belopp, accountNr);
    }
    
    public Transaction(LocalDateTime date, double saldo, boolean b, double belopp, int accountNr){ // konstruktor för SQL
        this.date = date; // sätter date till nu
        this.b = b; 
        this.belopp = belopp;
        this.saldo = saldo;
        this.accountNr=accountNr;
    }
    
    
    @Override
    public String toString(){ // metod för att skriva ut denna transaktion
        String s = "";
        String datum = date.toString(); // datumet för denna transaction
        String tid;
        try{ // try catch då databasen inte läser in de sista 00 då tiden blir tillexempel 12:00:00
        tid = datum.substring(11, 19); // tar ut tiden med substring
        }catch(IndexOutOfBoundsException e){
             tid = datum.substring(11, 16) + ":00";
        }
        datum = datum.substring(0, 10); // tar ut datumet med substring
        
        String saldoFormat = String.format("%.2f", saldo); // sätter till 2 decimaler
        
        if(b){ // om det är en insättning
            s = datum + " " + tid + " In: " + belopp + " Balance: " + saldoFormat;
        }else{ // om det är ett uttag
            s = datum + " " + tid + " Out: -" + belopp + " Balance: " + saldoFormat;
        }
    
        return s; // returnerar Stringen s
    }
    
    public boolean getB(){
        return b;
    }
    
}