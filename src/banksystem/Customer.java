package banksystem;

// @author Anton
import DBrepository.DBT;
import java.util.ArrayList;

public class Customer {

    private String name;
    private long pNr;
    private ArrayList<Account> accounts = new ArrayList<>(); // ArrayList för att fylla i alla konton
    private DBT dbt = DBT.getInstance();

    public Customer(String name, long pNr) { // Konstruktor som tar in namn och personnummer som parameter
        this.name = name;
        this.pNr = pNr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        String newName = name.replaceAll("[^A-Öa-ö ]", "").trim();// man ska kunna ändra namnet och replaceAll tar bort allt som inte är bokstäver
        this.name = newName;
    }

    public long getPnr() {
        return pNr;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public ArrayList<Account> getAccountList() { // vi hämtar Listan med konton
        return accounts;
    }

    public int[] getAccountNumbers() {
        int[] nr;

        if (!accounts.isEmpty()) {
            nr = new int[accounts.size()];
            int i = 0;
            for (Account a : accounts) {
                nr[i] = a.getAccountNumber();
                i++;
            }
        } else {
            nr = new int[0];
        }
        return nr;
    }

    public int addSavingAccount(double balance) { //  metod för att skapa en savings account som tar emot saldo som är av typen double
        SavingsAccount sA = new SavingsAccount(balance);// skapar ett nytt objekt sA av typen SavingsAccount
        int nr = sA.getAccountNumber();// För att hämta kontonummer
        accounts.add(sA);
        dbt.newAccount(pNr, nr, balance, true);
        return nr;
    }

    public int addCheckingAccount(double balance) { // detta är metod för att skapa en ny credit account 
        CreditAccount cA = new CreditAccount(balance);// skapar ett nytt objekt cA av typen CreditAccount
        int nr = cA.getAccountNumber();// För att hämta kontonummer
        accounts.add(cA);// För att hämta kontonummer
        dbt.newAccount(pNr, nr, balance, false);
        return nr;

    }

    public Account getSelectedAccount(int aNr) { // metod för att hämta ett specifikt konto som man har valt
        for (Account a : accounts) { // går igenom Account listan 
            if (a.getAccountNumber() == aNr) { // och kollar om aNr finns i account Listan då returnerar vi kontot
                return a;
            }
        }
        return null;// Om aNr (Kontonummer) inte finns då returneras det inget.
    }

    public void closeAccount(int aNr) { // metod för att avsluta ett konto
        accounts.remove(getSelectedAccount(aNr));
    }

    public void account() {
        accounts = dbt.getAccountList(pNr);
    }
}
