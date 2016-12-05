package banksystem;

// @author Anton

import java.util.ArrayList;

public class BankLogic {

    private ArrayList<Customer> customersList = new ArrayList<>(); // Lista med samtliga kunder
    private static BankLogic instance;
    private long pNr; // variabel som vi vid olika tillfällern tilfälligt sparar personNr i
    private int accountNr; // variabel som vi vid olika tillfällern tilfälligt sparar accountNr i
    

    private BankLogic() {      
    }

    public static BankLogic getInstance() { 
        if (instance == null) {
            instance = new BankLogic();
        }
        return instance;
    }

    public long getpNr() {
        return pNr;
    }

    public void setpNr(long pNr) {
        this.pNr = pNr;
    }

    public int getAccountNr() {
        return accountNr;
    }

    public void setAccountNr(int accountNr) {
        this.accountNr = accountNr;
    }  

    public String[] getCustomers() { // metod för att returnera samtliga namn på alla kunder i en lista
        String[] customers = new String[customersList.size()];
        int i = 0;
        for (Customer c : customersList) {
            customers[i] = "Social security number: " + c.getPnr() + " Name: " + c.getName();
            i++;
        }
        return customers; // returnar en string list med alla namn
    }

    public boolean addCustomer(String name, long pNr) { // metod för att lägga till kund

        boolean b = searchCustomer(pNr); // kontrollerar om kunden redan existerar

        if (b) // om kunden existerar returnas false
        {
            return false;
        }

        customersList.add(new Customer(name, pNr)); // om kunden ej finns skapas en ny kund
        return true;

    }
    
    public void accountList(){
        for(Customer c : customersList){
            c.account();
        }
    }
    
    public void getAllSqlTransactions(){
        for(Customer c : customersList){
            for(Account a : c.getAccountList())
                a.getAllSqlTransactions();
        }
    }
    
    public void setNumberWithdraAtStart(){
        for(Customer c : customersList){
            for(Account a : c.getAccountList()){
                 if (a.getAccountName().equals("Saving Account")){
                    SavingsAccount sa = (SavingsAccount) a;
                    sa.setNumberOfWithdraAtStart();
                }
            }
        }
    }

    public boolean searchCustomer(long pNr) { // metod för att söka efter kunder med personnumer

        for (Customer c : customersList) { // löper igenom lista med kunder
            long p = c.getPnr();
            if (pNr == p) //om personnumret finns returneras true
            {
                return true;
            }
        }
        return false; // om kunden inte finns returneras false
    }

    public String[] getCustomer(long pNr) { // metod som returnerar en lista med information om vald kund och dennes konton
        String[] lista;
        ArrayList<Account> a;
        Customer c1 = null;

        for (Customer c : customersList) { // löper igenom kund listan och hittar rätt kund
            if (pNr == c.getPnr()) {
                c1 = c;
                break; // när kunden hittas stoppas for-loopen och man hoppar vidare
            }
        }

        a = c1.getAccountList(); // hämtar vald kunds kontolista
        int counter = 0;
        int counter1 = 0;
        for (Account aa : a) {//loopar igenom accountList och räknar savings och credit account
            if (aa instanceof SavingsAccount) {// kollar om det finns en savings account 
                counter++;
            } else if (aa instanceof CreditAccount) {// kollar om det finns en credit account
                counter1++;
            }
        }
        lista = new String[a.size() + 4]; // initierar string lista med kontolistans storlek + 4 platser 
        lista[0] = c1.getName() + " " + c1.getPnr(); // lägger namn och personnr överst
        lista[1] = "Number of savings account(s): " + counter; // lägger in antal SavingsAccount
        lista[2] = "Number of credit account(s): " + counter1; // Lägger in antal credit accounts
        lista[3] = ""; // tom rad // en tom rad för snyggare utskrift
        
        int i = 4; // börjar från plats 4 i listan för att ej skriva över någor
        for (Account l : a) { // löper igenom accoutList för att skriva ut nr, namn, saldo
            lista[i] = l.getAccountNumber() + " " + l.getAccountName() + "\tBalance: " + String.format("%.2f", l.getBalance());
            i++;
        }

        return lista; // returnerar listan
    }

    public ArrayList getCustomerList() { // metod som returnerar customersList
        return customersList;
    }

    public String[] removeCustomer(long pNr){ // metod för att ta bort en kund
        Customer c = null;
        for(Customer cc : customersList){ // löper igenom kundlistan för att matcha personnr
            if(pNr == cc.getPnr())
                c = cc;
        }
        int antal = c.getAccountList().size() + 2;
        String[] lista = new String[antal]; // skapar en lista lika stor som antal accounts + 2
        
        lista[0] = c.getName() + " " + c.getPnr(); // lägger namn och personnr överst i listan
        double tot = 0;
        for(int i = 0, j = 1; i < c.getNumberOfAccounts(); i++, j++){
            double dd = c.getAccountList().get(i).getInterest();
            double l = 0;
            if(dd == 0)
                l = 1;
            else if(dd == 0.07)
                l = 7;
            else
                l = 0.5;
            
            lista[j] = c.getAccountList().get(i).getAccountNumber() + " " + c.getAccountList().get(i).getAccountName() // på plats 2 i listan lägger vi in kontoNr och namn på kontot, saldo, ränta och till sist saldo+ränta 
                    + " Balance: " + c.getAccountList().get(i).getBalance() + " Interest: " + l 
                    + " Total: " + c.getAccountList().get(i).getTotalBalance();
            
            tot += c.getAccountList().get(i).getTotalBalance(); // totalt saldo
        }
        
        if(tot < 0){
            lista[lista.length-1] = String.format("The customer has to pay: " + "%.2f", tot); // om det är minus saldo när alla konton lagts ihop
        }else{
            lista[lista.length-1] = String.format("Pay the customer: " + "%.2f", tot); // positivt salto
        }
        
        customersList.remove(c); // tar bort kunden
        return lista; // returnerar listan

    }

    public String getAccount(long pNr, int accountId){ // metod som returnerar en String med information om kund och valt konto
        Customer c = null; 
        Account a = null;
        for(Customer cc : customersList){ // löper igenom kundlista och matchar rätt kund till valt personnr
            if(cc.getPnr() == pNr){
                c = cc;
                for(Account aa : c.getAccountList()){ // när vi har hittat rätt kund löper jag igenom den rätta kundens kontoloista för att hitta det valda kontot
                    if(aa.getAccountNumber() == accountId){
                        a = aa;
                    }
                }
            }
        }
        String intrest;
        if(a.getAccountName().equals("Saving Account")){ // om det är ett savingsAccount 1% ränta
            intrest = "1%";
        }else{ // credit account
            if(a.getBalance() < 0) // saldo under 0 = skuldränta 7%
                intrest = "-7%";
            else // saldo 0 och uppåt = 0,5% ränta
                intrest = "0,5%";
        }
        
        
        String s = "Customer: " + c.getName() + " Ssn: " + c.getPnr() + " " + a.getAccountName()  
                + " Number: " + a.getAccountNumber() + " Balance: " + a.getBalance() + " Interest: " + intrest; // lägger all information i en string 
        
        return s;   // returnerar string s   
    }
    
    public boolean changeCustomerName(String name, long pNR) { // metod för att ändra namn på vald kund

        for (int i = 0; i < customersList.size(); i++) {
            if (pNR == customersList.get(i).getPnr()) {
                customersList.get(i).setName(name); // löper igenom litan med kunder tills vi har rätt kund och kallar då på metoden setName
                return true; // om vi lyckats returneras true
            }

        }
        return false; // om vi misslyckats returneras false
    }

    public int addSavingsAccount(long pNR) { // metod för att lägga till ett savingsAccount
            for(Customer c : customersList){ // loppar igenom customerList
            if(pNR == c.getPnr()){  // och hittar matchande pNR
                int kontoNr = c.addSavingAccount(0.0); // skapar ett nytt konto på vald kund och får tillbaka en int med kontoNr
           return kontoNr; // returnenrar kontonummret

    }
            }
            return -1; // om det misslyckats returneras -1
    }

    public int addCreditAccount(long pNR) { // metod för att lägga till ett CreditAccount
            for(Customer c : customersList){ // loppar igenom customerList
            if(pNR == c.getPnr()){  // och hittar matchande pNR
                int kontoNr = c.addCheckingAccount(0.0); // skapar ett nytt konto på vald kund och får tillbaka en int med kontoNr
                
           return kontoNr; // returnenrar kontonummret
            }

        }
        return -1; // om det misslyckats returneras -1
    }



 public boolean withdraw(long pNR, int accountID, double amount) { // metod för att ta ut pengar

    
     
        for (int i = 0; i < customersList.size(); i++) { // loppar igenom customerList
            if (pNR == customersList.get(i).getPnr()) { // matchar personnummer

                for (int j = 0; j < customersList.get(i).getAccountList().size(); j++) { // går in i matchande person och hämtar storlek på accountList
                    if (accountID == customersList.get(i).getAccountList().get(j).getAccountNumber()) { // letar upp matchande kontonummer

                        if ("CreditAccount".equals(customersList.get(i).getAccountList().get(j).getClass().getSimpleName())) { // kontrollerar om kontonummer är ett Kreditkonto
                            double currentBalance = customersList.get(i).getAccountList().get(j).getBalance(); // isf, hämtar belopp på kontot
                            currentBalance = currentBalance - amount; // räknar ut nytt belopp
                    
                            
                            
                            
                            
                            if (currentBalance >= -5000) { // om det nya beloppet på kontot är mer än -5000
                                
                                customersList.get(i).getAccountList().get(j).withdraw(amount);
                                
                                return true; // Det går bra att sätta in pengar
                            } else {
                                return false; // annars, det går inte
                            }

                        } else {
                            double currentBalance = customersList.get(i).getAccountList().get(j).getBalance(); // om kontot är ett sparkonto
                            SavingsAccount sa = (SavingsAccount) customersList.get(i).getAccountList().get(j);
                            int ii = sa.getnumberOfWithdraw();
                            if(ii < 1)
                            currentBalance = currentBalance - amount; // räknar ut nya beloppet
                            else{
                                double amount1 = amount * 0.02 + amount;
                                currentBalance = currentBalance - amount1; // räknar ut nya beloppet med utagsränta
                            }
                            if (currentBalance >= 0) { // kontrollerar om det nya beloppet accepteras på sparkontot
                                customersList.get(i).getAccountList().get(j).withdraw(amount);
                                return true;
                                // Accepteras
                            } else {
                                return false; // Accepteras inte
                            }
                            

                        }

                    }

                }

            } 

        } // första for sats
        return false;
    } // slut på metod
    
    
    
    public boolean deposit(long pNR, int accountID, double amount) {

        for (int i = 0; i < customersList.size(); i++) { // loppar igenom customerList
            if (pNR == customersList.get(i).getPnr()) { // matchar personnummer

                for (int j = 0; j < customersList.get(i).getAccountList().size(); j++) { // går in i matchande person och hämtar storlek på accountList
                    if (accountID == customersList.get(i).getAccountList().get(j).getAccountNumber()) { // letar upp matchande kontonummer

                        if ("CreditAccount".equals(customersList.get(i).getAccountList().get(j).getClass().getSimpleName())) { // kontrollerar om kontonummer är ett Kreditkonto
                            double currentBalance = customersList.get(i).getAccountList().get(j).getBalance(); // isf, hämtar belopp på kontot
                            double newBalance = currentBalance + amount; // räknar ut nytt belopp
                            customersList.get(i).getAccountList().get(j).deposit(amount);
                            return true;


                        } else {
                            double currentBalance = customersList.get(i).getAccountList().get(j).getBalance(); // om kontot är ett kreditkonto
                            double newBalance = currentBalance + amount; // räknar ut nya beloppet
                            customersList.get(i).getAccountList().get(j).deposit(amount);
                            return true;


                        }

                    }

                }

            } 

        } // första for sats
        return false;
    } // slut på metod
    
    public Customer getSelectedCustomer(long pNr){ // metod som returnerar ett objekt av Customer
        Customer cc = null;
        for(Customer c : customersList){
            if(c.getPnr()==pNr)
                cc = c;
        }
        return cc; 
        
    }


}
