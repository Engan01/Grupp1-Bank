package banksystem;

import DBrepository.DBT;
import java.util.ArrayList;

/**
 *
 * @author asanilssonenglund
 */
public class SavingsAccount extends Account {

    private int numberOfWithdraw; // kontrollvariabel för veta antallet uttag
    private static double interest = 0.01; // 1% ränta
    private DBT dbt = DBT.getInstance();


    public SavingsAccount() {
        super(); // default constructor
    }

    public SavingsAccount(double balance) { // konstruktor för SavingsAccount
        super(balance); 
        numberOfWithdraw = 0; // tilldelar startvärde för antal uttag
    }
    
        public SavingsAccount(int accountNr, double balance){
        super(accountNr, balance);
    }

    @Override
    public double deposit(double amount) {      // funktion för att sätta in pengar på ett SavingsAccount
        double b = super.getBalance(); // hämtar balans på konto
                b += amount;    // adderar amount
                super.setBalance(b); // tilldelar nytt belopp
                dbt.updateBalance(super.getAccountNumber(), super.getBalance()); // uppdaterar balans i databas
        return b; // returnerar nya beloppet
    }

    @Override
    public double withdraw(double amount) { // funktion för att ta ut pengar
        if (numberOfWithdraw < 1) { // kontrollear om INTE gjorts tidigare
            double d = super.getBalance(); // i så fall, hämta balans, addera och tilldela ny balans
                   d -= amount;
                   super.setBalance(d);
                   dbt.updateBalance(super.getAccountNumber(), super.getBalance()); // uppdaterar balans i databas
            numberOfWithdraw++; // tilldela nytt värde på antal uttag
        } else {                    // ifall tidigare uttag gjorts
            double balance = super.getBalance();
            amount = amount * 0.02 + amount; // utagsränta efter första utaget = 2%
            balance -= amount;      // hämta balans, addera och tilldela ny balans
            super.setBalance(balance);
            dbt.updateBalance(super.getAccountNumber(), super.getBalance());
            numberOfWithdraw++;
            
        }
        return super.getBalance();

    }
    
    public int getnumberOfWithdraw(){ // funktion för att komma åt hur många uttag som gjorts
        return numberOfWithdraw;
    }
    
    public void setnumberOfWithdraw(){ // tilldela nytt värde för uttagsnummer
        numberOfWithdraw++;
    }
    
    public void setNumberOfWithdraAtStart(){
        ArrayList<Transaction> tran = super.getTransaction();
        if(!tran.isEmpty()){
        for(Transaction t : tran){
            if(t.getB())
                numberOfWithdraw++;
        }
        }
    }

    @Override
    public String getAccountName() { // funktion för att hämta namnet på konto
        return "Saving Account";
    }

    @Override
    public double getInterest() { // funktion för att hämta räntan för kontot
        return interest;
    }
    
    @Override
    public double getTotalBalance(){
      Double b = super.getBalance() * 0.01 + super.getBalance(); // funktion för att beräkna totala balansen med 1% ränta inräknad
      return b;
    }
}
