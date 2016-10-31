package banksystem;

// @author Anton
import java.time.LocalDateTime;
import java.util.ArrayList;

public class BankLogic {

    private ArrayList<Customer> customersList = new ArrayList<>();

    private int accountNumber;
    private static BankLogic instance;

    private BankLogic() {
    }

    public static BankLogic getInstance() {
        if (instance == null) {
            instance = new BankLogic();
        }
        return instance;
    }

    public String[] getCustomers() { // metod för att returnera samtliga namn på alla kunder i en lista
        String[] customers = new String[customersList.size()];
        for (Customer c : customersList) {
            int i = 0;
            customers[i] = c.getName();
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
            long p = c.getPnr();
            if (p == pNr) {
                c1 = c;
                break;
            }
        }

        a = c1.getAccountList();
        lista = new String[a.size() + 1];
        lista[0] = c1.getName() + " " + c1.getPnr();
        int i = 1;
        for (Account l : a) {
            lista[i] = Integer.toString(l.getAccountNumber());
            i++;
        }

        return lista;
    }

    public ArrayList getCustomerList() { // metod som returnerar customersList
        return customersList;
    }

//    public String[] removeCustomer(long pNr){
//
//    }
//
//    public int getAccountNumber(){
//
//    }
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



 public boolean withdraw(long pNR, int accountNumber, double amount) {

        for (int i = 0; i < customersList.size(); i++) { // loppar igenom customerList
            if (pNR == customersList.get(i).getPnr()) { // matchar personnummer

                for (int j = 0; j < customersList.get(i).getAccountList().size(); j++) { // går in i matchande person och hämtar storlek på accountList
                    if (accountNumber == customersList.get(i).getAccountList().get(j).getAccountNumber()) { // letar upp matchande kontonummer

                        if ("CreditAccount".equals(customersList.get(i).getAccountList().get(j).getClass().getSimpleName())) { // kontrollerar om kontonummer är ett Kreditkonto
                            double currentBalance = customersList.get(i).getAccountList().get(j).getBalance(); // isf, hämtar belopp på kontot
                            currentBalance = currentBalance - amount; // räknar ut nytt belopp
                            
                    

                            if (currentBalance >= -5000) { // om det nya beloppet på kontot är mer än -5000
                                
                                customersList.get(i).getAccountList().get(j).setBalance(currentBalance);
                                
                                return true; // Det går bra att sätta in pengar
                            } else {
                                return false; // annars, det går inte
                            }

                        } else {
                            double currentBalance = customersList.get(i).getAccountList().get(j).getBalance(); // om kontot är ett kreditkonto
                            currentBalance = currentBalance - amount; // räknar ut nya beloppet

                            if (currentBalance >= 0) { // kontrollerar om det nya beloppet accepteras på sparkontot
                                customersList.get(i).getAccountList().get(j).setBalance(currentBalance);
                                return true; // Accepteras
                            } else {
                                return false; // Accepteras inte
                            }

                        }

                    }

                }

            } else {
                System.out.println("pNR matchar inte!");
                return false;
            }

        } // första for sats
        return false;
    } // slut på metod
    
    
    
    public boolean deposit(long pNR, int accountNumber, double amount) {

        for (int i = 0; i < customersList.size(); i++) { // loppar igenom customerList
            if (pNR == customersList.get(i).getPnr()) { // matchar personnummer

                for (int j = 0; j < customersList.get(i).getAccountList().size(); j++) { // går in i matchande person och hämtar storlek på accountList
                    if (accountNumber == customersList.get(i).getAccountList().get(j).getAccountNumber()) { // letar upp matchande kontonummer

                        if ("CreditAccount".equals(customersList.get(i).getAccountList().get(j).getClass().getSimpleName())) { // kontrollerar om kontonummer är ett Kreditkonto
                            double currentBalance = customersList.get(i).getAccountList().get(j).getBalance(); // isf, hämtar belopp på kontot
                            double newBalance = currentBalance + amount; // räknar ut nytt belopp
                            customersList.get(i).getAccountList().get(j).setBalance(newBalance);
                            return true;
//                            if (newBalance >= 5000) { // om det nya beloppet på kontot är mer än -5000
//                                customersList.get(i).getAccountList().get(j).setBalance(newBalance);
//                                return true; // Det går bra att sätta in pengar
//                            } else {
//                                return false; // annars, det går inte
//                            }

                        } else {
                            double currentBalance = customersList.get(i).getAccountList().get(j).getBalance(); // om kontot är ett kreditkonto
                            double newBalance = currentBalance + amount; // räknar ut nya beloppet
                            customersList.get(i).getAccountList().get(j).setBalance(newBalance);
                            return true;
//                            if (newBalance >= 0) { // kontrollerar om det nya beloppet accepteras på sparkontot
//                                customersList.get(i).getAccountList().get(j).setBalance(newBalance);
//                                return true; // Accepteras
//                            } else {
//                                return false; // Accepteras inte
//                            }

                        }

                    }

                }

            } else {
                System.out.println("pNR matchar inte!");
                return false;
            }

        } // första for sats
        return false;
    } // slut på metod




}
