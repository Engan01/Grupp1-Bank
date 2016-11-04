package banksystem;

// @author Anton

import java.util.ArrayList;

public class BankLogic {

    private ArrayList<Customer> customersList = new ArrayList<>();
    private static BankLogic instance;
    private long pNr;
    private int accountNr;
    private boolean trueFalse;

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

    public boolean isTrueFalse() {
        return trueFalse;
    }

    public void setTrueFalse(boolean trueFalse) {
        this.trueFalse = trueFalse;
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

    public String[] getCustomer(long pNr) {
        String[] lista;
        ArrayList<Account> a;
        Customer c1 = null;

        for (Customer c : customersList) {
            if (pNr == c.getPnr()) {
                c1 = c;
                break;
            }
        }

        a = c1.getAccountList();
        int counter = 0;
        int counter1 = 0;
        for (Account aa : a) {//loopar igenom accountList
            if (aa instanceof SavingsAccount) {// kollar om det finns en savings account 
                counter++;
            } else if (aa instanceof CreditAccount) {// kollar om det finns en credit account
                counter1++;
            }
        }
        lista = new String[a.size() + 4];
        lista[0] = c1.getName() + " " + c1.getPnr();
        lista[1] = "Number of savings account(s): " + counter;
        lista[2] = "Number of credit account(s): " + counter1;
        lista[3] = ""; // tom rad
        
        int i = 4;
        for (Account l : a) {
            lista[i] = l.getAccountNumber() + " " + l.getAccountName() + "\tBalance: " + String.format("%.2f", l.getBalance());
            i++;
        }

        return lista;
    }

    public ArrayList getCustomerList() { // metod som returnerar customersList
        return customersList;
    }

    public String[] removeCustomer(long pNr){
        Customer c = null;
        for(Customer cc : customersList){
            if(pNr == cc.getPnr())
                c = cc;
        }
        int antal = c.getAccountList().size() + 2;
        String[] lista = new String[antal];
        
        lista[0] = c.getName() + " " + c.getPnr();
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
            
            lista[j] = c.getAccountList().get(i).getAccountNumber() + " " + c.getAccountList().get(i).getAccountName() 
                    + " Balance: " + c.getAccountList().get(i).getBalance() + " Interest: " + l 
                    + " Total: " + c.getAccountList().get(i).getTotalBalance();
            
            tot += c.getAccountList().get(i).getTotalBalance();
        }
        
        if(tot < 0){
            lista[lista.length-1] = String.format("The customer has to pay: " + "%.2f", tot);
        }else{
            lista[lista.length-1] = String.format("Pay the customer: " + "%.2f", tot);
        }
        
        customersList.remove(c); // tar bort kunden
        return lista;

    }

    public String getAccount(long pNr, int accountId){
        Customer c = null;
        Account a = null;
        for(Customer cc : customersList){
            if(cc.getPnr() == pNr){
                c = cc;
                for(Account aa : c.getAccountList()){
                    if(aa.getAccountNumber() == accountId){
                        a = aa;
                    }
                }
            }
        }
        String intrest;
        if(a.getAccountName().equals("Saving Account")){
            intrest = "1%";
        }else{
            if(a.getBalance() < 0)
                intrest = "-7%";
            else
                intrest = "0,5%";
        }
        
        
        String s = "Customer: " + c.getName() + " Ssn: " + c.getPnr() + " " + a.getAccountName()  
                + " Number: " + a.getAccountNumber() + " Balance: " + a.getBalance() + " Interest: " + intrest;
        
        return s;     
    }
    
    public boolean changeCustomerName(String name, long pNR) {

        for (int i = 0; i < customersList.size(); i++) {
            if (pNR == customersList.get(i).getPnr()) {
                customersList.get(i).setName(name);
                return true;
            }

        }
        return false;
    }

    public int addSavingsAccount(long pNR) {
            for(Customer c : customersList){ // loppar igenom customerList
            if(pNR == c.getPnr()){  // och hittar matchande pNR
                int kontoNr = c.addSavingAccount(0.0);
           return kontoNr; // returnenrar kontonummret

    }
            }
            return -1;
    }

    public int addCreditAccount(long pNR) {
            for(Customer c : customersList){ // loppar igenom customerList
            if(pNR == c.getPnr()){  // och hittar matchande pNR
                int kontoNr = c.addCheckingAccount(0.0);
           return kontoNr; // returnenrar kontonummret
            }

        }
        return -1;
    }



 public boolean withdraw(long pNR, int accountID, double amount) {

    
     
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


}
