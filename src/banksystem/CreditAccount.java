package banksystem;

import DBrepository.DBT;

/**
 *
 * @author asanilssonenglund
 */
public class CreditAccount extends Account {

    private final double debtInterest = 0.07;
    private final double debtIncrease = 1.005;
    private static double interest = 0.005;
    private DBT dbt = DBT.getInstance();

    public CreditAccount() {
        super(); // default constructor

    }

    public CreditAccount(double balance) { // kunstruktor
        super(balance);
    }
    
    public CreditAccount(int accountNr, double balance){
        super(accountNr, balance);
    }

    @Override
    public double deposit(double amount) { // metod för att sätta in pengar
        double balance = super.getBalance(); // hämtar saldo från superklass 
        balance += amount; // läger till belopp
        dbt.updateBalance(super.getAccountNumber(), super.getBalance());
        super.setBalance(balance); // skickar det nya beloppet till superklassen Account
        return balance; // returnerar det nya beloppet
    }

    @Override
    public double withdraw(double amount) { // metod för att ta ut pengar
        double balance = super.getBalance(); // hämtar belop från superklass
        balance -= amount; // tar belopp minus det användaren tagit ut
        dbt.updateBalance(super.getAccountNumber(), super.getBalance());
        super.setBalance(balance); // skickar det nya beloppet till superklassen
        return balance; // returnerar beloppet

    }

    public double closeCreditAccount(double balance) { // returnera det saldo man skickar in + ränta
        if (balance <= 0) { 
            balance *= debtInterest;
        } else {
            balance *= debtIncrease;
        }

        return balance;
    }

    @Override
    public String getAccountName() {
        return "Credit Account";
    }

    @Override
    public double getInterest() {
        if(super.getBalance() < 0)
            return debtInterest;
        else
            return interest;   
    }
    
    @Override
    public double getTotalBalance(){ // returnear beloppet på kontot + ränta 
       double b = super.getBalance();
       if(b < 0){
          double bb = super.getBalance();
          bb = bb * 0.07;
          double minusTotal = super.getBalance() + bb;
          return minusTotal; // minus pelopp
       }else{
           double plusTotal = super.getBalance() * 0.005 + super.getBalance();
           return plusTotal; // positivt belopp
       }
    }

}
