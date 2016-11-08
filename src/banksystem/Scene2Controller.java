package banksystem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author asanilssonenglund
 */
public class Scene2Controller implements Initializable {

    private ObservableList<String> accountObservableList; // observable list för konton
    private ObservableList<String> transactionObservable; // observable list för transactions 
    private BankLogic b; // Singelton banklogic

    @FXML
    private Label name, ssn, rate, transferStatus, nameStatus, mainStatus, accountNr, balance, exportStatus;

    @FXML
    private Button editNameButton, addAccountButton, back;

    @FXML
    private ListView accountList, transactionList;

    @FXML
    private ChoiceBox transferFrom, transferTo;

    @FXML
    private TextField amount, amountTransfer;

    @FXML
    private void deposit(ActionEvent e) throws Exception { // metod för att sätta in pengar
        transferStatus.setText("");
        mainStatus.setText("");
        mainStatus.setTextFill(Color.RED);

        try {
            Customer c = getThisObject();
            String selectedAccount = (String) accountList.getSelectionModel().getSelectedItem();

            if (selectedAccount.isEmpty()) { // om man ej valt ett konto i listan
                throw new NullPointerException();
            }

            int acountNR = Integer.parseInt(selectedAccount.replaceAll("[A-Öa-ö ]", "").trim()); // konverterar String acount# till int
            double amount2 = Double.parseDouble(amount.getText());  // konverterar String amount till double amount
            if (amount2 < 1) { // om man försöker sätta in ett värde under 1 kastas ett exception
                throw new NumberFormatException();
            }

            //drar pengar från spcifikt konto
            if (b.deposit(c.getPnr(), acountNR, amount2) == true) { // om det går bra utförs detta
                c.getSelectedAccount(acountNR).addTransaction(true, amount2, c.getSelectedAccount(acountNR).getBalance());
                String gg = Integer.toString(acountNR);
                setTransactions();
                mainStatus.setTextFill(Color.GREEN);
                mainStatus.setText("Deposit succesfull!");
            } else {
                mainStatus.setText("Error. Deposit failed."); // annars skrivs detta ut
            }

            // *** skrive ut nytt belopp på "balance" label
            for (int i = 0; i < c.getAccountList().size(); i++) {
                if (acountNR == c.getAccountList().get(i).getAccountNumber()) {
                    double newBalance = c.getAccountList().get(i).getBalance();

                    balance.setText(String.format("%.2f", newBalance));
                }
            }
        } catch (NullPointerException ex2) {
            mainStatus.setText("You must select an account!");
        } catch (NumberFormatException ex) {
            mainStatus.setText("Invalid amount!");
        }
    }

    @FXML
    private void withdraw(ActionEvent e) throws Exception { // metod för att ta ut pengar
        transferStatus.setText("");
        mainStatus.setText("");
        mainStatus.setTextFill(Color.RED);

        try {
            Customer c = getThisObject();

            String selectedAccount = (String) accountList.getSelectionModel().getSelectedItem();

            if (selectedAccount.isEmpty()) { // om man ej valt ett konto kastas ett exception
                throw new NullPointerException();
            }
            
            int acountNR = Integer.parseInt(selectedAccount.replaceAll("[A-Öa-ö ]", "").trim()); // konverterar String acount# till int

            double amount2 = Double.parseDouble(amount.getText());  // konverterar String amount till double amount
            if(amount2 < 1) // kan ej ta ut 0 eller minusvärde
                throw new NumberFormatException();

            if (b.withdraw(c.getPnr(), acountNR, amount2) == true) { // om det går bra att ta ut

                c.getSelectedAccount(acountNR).addTransaction(false, amount2, c.getSelectedAccount(acountNR).getBalance());
                String gg = Integer.toString(acountNR);
                setTransactions();

                if ("SavingsAccount".equals(c.getSelectedAccount(acountNR).getClass().getSimpleName())) {
                    rate.setText("2%"); // om det är ett savingsaccoutn sätts label rate till 2% för nästa uttag
                }
                mainStatus.setTextFill(Color.GREEN);
                mainStatus.setText("Withdraw succesfull!");
            } else {
                mainStatus.setText("Withdraw not possible!");
            }

            // *** skrive ut nytt belopp på "balance" label
            for (int i = 0; i < c.getAccountList().size(); i++) {
                if (acountNR == c.getAccountList().get(i).getAccountNumber()) {
                    double newBalance = c.getAccountList().get(i).getBalance();

                    String newString = String.format("%.2f", newBalance);
                    balance.setText(newString);
                }
            }

        } catch (NullPointerException ex2) {
            mainStatus.setText("You must select an account!");
        } catch (NumberFormatException ex) {
            mainStatus.setText("Invalid amount!");
        }
    }

    @FXML
    private void exportToFile() throws Exception { // metod för att exportera transactions
        exportStatus.setTextFill(Color.BLACK);

        BufferedWriter writer = null;
        try {

            Customer c = getThisObject();
            long p = c.getPnr();
            String ss = accountNr.getText();
            if (ss.isEmpty()) { // man måste välja ett konto
                exportStatus.setTextFill(Color.RED);
                exportStatus.setText("You have to select an account!");
                throw new NullPointerException();
            }
            int ii = Integer.parseInt(ss);
            ArrayList<Transaction> t = c.getSelectedAccount(ii).getTransaction();
            Account aa = c.getSelectedAccount(ii); // hämtar det valda kontot där transactions skall skrivas ut

            String userHomeFolder = System.getProperty("user.home"); // skriver ut till användarens hem mapp
            File textFile = new File(userHomeFolder, "transactions.txt"); // namn på filen
            writer = new BufferedWriter(new FileWriter(textFile));

            if (t.isEmpty()) {
                exportStatus.setTextFill(Color.RED);
                exportStatus.setText("No transactions to export."); // om det inte finns några transactions
                throw new NullPointerException();
            }

            if (aa.getAccountName().equals("Credit Account")) { // om det är ett credit account skrivs detta ut som första rad
                writer.write("Customer: " + c.getName() + "\nSocial security number: " + c.getPnr() + "\nCredit Account " + aa.getAccountNumber() + " Balance: " + String.format("%.2f", aa.getBalance()) + "\n");
            } else { // om det är ett savings account skrivs detta ut som första rad
                writer.write("Customer: " + c.getName() + "\nSocial security number: " + c.getPnr() + "\nSaving Account " + aa.getAccountNumber() + " Balance: " + String.format("%.2f", aa.getBalance()) + " Interest: 1% \n");
            }

            for (Transaction t1 : t) { // Skriver ut varje transaction 
                writer.newLine();
                writer.write(t1.toString()); // finns en metod i klassen Transaction som heter toString - Override toString
                writer.newLine();
                writer.newLine();
            }
                exportStatus.setTextFill(Color.GREEN);
                exportStatus.setText("Transactionslist successfully exported to file"); // om allt gick bra
           

        } catch (NullPointerException e) {
        } catch (IOException e) {
            exportStatus.setTextFill(Color.RED);
            exportStatus.setText("Transfer file is not accessible!");
        } finally {
            try {
                if (writer != null) { // stänger strömmen
                    writer.close();
                }
            } catch (IOException e) {

            }

        }
    }

    @FXML
    private void transferButton() { // metod för att överföra pengar mellan den valda kundens konton

        transferStatus.setText("");
        mainStatus.setText("");
        transferStatus.setTextFill(Color.RED);
        try {
            
            Customer c = getThisObject(); // kallar på befintlig kund
            int selectedFromAccountNr = Integer.parseInt(transferFrom.getSelectionModel().getSelectedItem().toString().replaceAll("[^\\d.]", "")); // konto från
                accountNr.setText(Integer.toString(selectedFromAccountNr)); // ändrar label accountNr till detta accounts nr
            

            int selectedToAccountNr = Integer.parseInt(transferTo.getSelectionModel().getSelectedItem().toString().replaceAll("[^\\d.]", "")); // hämtar account nr som man ska överföra till

            double selectedAccountsBalance = getThisObject().getSelectedAccount(selectedFromAccountNr).getBalance(); // hämtar saldot på kontot som det skall tas pengar ifrån

            double transferAmount = Double.parseDouble(amountTransfer.getText()); // hämatr hur mycket man vill föra över

            if (selectedFromAccountNr == selectedToAccountNr) { // det går ej att överföra mellan samma konto
                transferStatus.setText("You can not transfer money to the same account!");
            } //I fall av fel inmatning
            else if (transferAmount <= 0) { // man kan inte föra över 0kr eller ett negativt tal
                transferStatus.setText("The amount to be transfered can not be negative or zero!");
            } //Om användaren väljer att skicka pengar till ett och samma konto
            else if ("CreditAccount".equals(c.getSelectedAccount(selectedFromAccountNr).getClass().getSimpleName())) { // om det konto man vill överföra ifrån är ett Credit Account
                if (c.getSelectedAccount(selectedFromAccountNr).getBalance() - transferAmount >= -5000) { // om saldot på kontot ej överstigen -5000

                    double newBalanceFromAccount = getThisObject().getSelectedAccount(selectedFromAccountNr).getBalance() - transferAmount; 
                    getThisObject().getSelectedAccount(selectedFromAccountNr).setBalance(newBalanceFromAccount); // sätter det nya saldot på kontot från

                    c.getSelectedAccount(selectedFromAccountNr).addTransaction(false, transferAmount, c.getSelectedAccount(selectedFromAccountNr).getBalance()); // lägger till en transaction på kontot från
                    String gg = Integer.toString(selectedFromAccountNr);

                    double newBalanceToAccount = getThisObject().getSelectedAccount(selectedToAccountNr).getBalance() + transferAmount;
                    getThisObject().getSelectedAccount(selectedToAccountNr).setBalance(newBalanceToAccount); // sätter den nya balancen på kontot till

                    c.getSelectedAccount(selectedToAccountNr).addTransaction(true, transferAmount, c.getSelectedAccount(selectedToAccountNr).getBalance()); // skapar en transaction på kontot till
                    String gg2 = Integer.toString(selectedFromAccountNr);

                    setTransactions(); // uppdaterer transaction litView
                    transferStatus.setTextFill(Color.GREEN);
                    transferStatus.setText("Transfer successful"); // allt gick bra

                } else {
                    transferStatus.setText("Transfer not possible!\nYou have reached your credit limit!");
                }
            } else if (transferAmount > selectedAccountsBalance) { // om det ej är ett credit account och beloppet man vill föra över är mer än saldot går det ej

                transferStatus.setText("There is not enough money in this account to perform this transfer!");
            } else { // Detta är för Savingaccount

                Account ff = c.getSelectedAccount(selectedFromAccountNr);
                SavingsAccount ggg = (SavingsAccount) ff;

                if (ggg.getnumberOfWithdraw() >= 1) { // om man tagit ut tidigare är det utagsränta

                    rate.setText("2%");
                    //Uppdatering saldo på första konto efter att överföra ett visst belopp
                    double newBalanceFromAccount = getThisObject().getSelectedAccount(selectedFromAccountNr).getBalance() - transferAmount * 1.02;
                    getThisObject().getSelectedAccount(selectedFromAccountNr).setBalance(newBalanceFromAccount);

                    c.getSelectedAccount(selectedFromAccountNr).addTransaction(false, transferAmount, c.getSelectedAccount(selectedFromAccountNr).getBalance()); // skapar en transaction
                    String gg = Integer.toString(selectedFromAccountNr);

                    //Uppdatering saldo på andra konto efter att överföra ett visst belopp
                    double newBalanceToAccount = getThisObject().getSelectedAccount(selectedToAccountNr).getBalance() + transferAmount;
                    getThisObject().getSelectedAccount(selectedToAccountNr).setBalance(newBalanceToAccount);

                    c.getSelectedAccount(selectedToAccountNr).addTransaction(true, transferAmount, c.getSelectedAccount(selectedToAccountNr).getBalance()); // skapar en transaction
                    String gg2 = Integer.toString(selectedFromAccountNr);
                    // ändra ränta
                    ggg.setnumberOfWithdraw(); // ökar uttaget på kontot från med ett med denna metod i accounts
                } else { // om det är första utaget är det ingen utagsränta

                    //Uppdatering saldo på första konto efter att överföra ett visst belopp
                    double newBalanceFromAccount = getThisObject().getSelectedAccount(selectedFromAccountNr).getBalance() - transferAmount;
                    getThisObject().getSelectedAccount(selectedFromAccountNr).setBalance(newBalanceFromAccount);

                    c.getSelectedAccount(selectedFromAccountNr).addTransaction(false, transferAmount, c.getSelectedAccount(selectedFromAccountNr).getBalance()); // skapar en transaction
                    String gg = Integer.toString(selectedFromAccountNr);

                    //Uppdatering saldo på andra konto efter att överföra ett visst belopp
                    double newBalanceToAccount = getThisObject().getSelectedAccount(selectedToAccountNr).getBalance() + transferAmount;
                    getThisObject().getSelectedAccount(selectedToAccountNr).setBalance(newBalanceToAccount);

                    c.getSelectedAccount(selectedToAccountNr).addTransaction(true, transferAmount, c.getSelectedAccount(selectedToAccountNr).getBalance()); // skapar en transaction
                    String gg2 = Integer.toString(selectedFromAccountNr);
                    ggg.setnumberOfWithdraw(); // ökar uttaget på kontot från med ett med denna metod i accounts

                }

                setTransactions(); // metod för att uppdatera transactions list
                    //Visa användaren att det gick att överföra pengar
                transferStatus.setTextFill(Color.GREEN);
                transferStatus.setText("Transfer successful!");
            }

        } catch (NumberFormatException e) {
            transferStatus.setText("Invalid amount!");
        } catch (NullPointerException e) {
            transferStatus.setText("You need to choose accounts!");
        }
    }

    @FXML
    private void editName(ActionEvent e) throws IOException { // när man trycker på knappen för att ändra namn
        transferStatus.setText("");
        mainStatus.setText("");
        nameStatus.setText("");
        b.setpNr(getThisObject().getPnr()); // spara vald kunds personnr i banklogic

        Stage stage;
        Parent root;

        stage = new Stage();
        stage.setTitle("Customer name editing");
        stage.setResizable(false);
        root = FXMLLoader.load(getClass().getResource("FXMLpopUp3.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(editNameButton.getScene().getWindow());
        stage.setOnCloseRequest((WindowEvent we) -> {
            stage.close();
        });
        stage.showAndWait(); // popUp startar och nuvarande scen väntar
        
        name.setText(getThisObject().getName()); // sätter labelen name till namnet på vald kund uppdaterat eller ej - uppdateras labelen
    }

    @FXML
    private void addAccountEvent(ActionEvent e) throws IOException { // metod för att lägga till ett konto
        transferStatus.setText("");
        mainStatus.setText("");
        b.setpNr(getThisObject().getPnr()); // sparar personnr på vald kund till banklogic

        Stage stage;
        Parent root;

        stage = new Stage();
        stage.setTitle("Creating new account");
        stage.setResizable(false);
        root = FXMLLoader.load(getClass().getResource("FXMLpopUp4.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(addAccountButton.getScene().getWindow());
        stage.setOnCloseRequest((WindowEvent we) -> {
            stage.close();
        });
        stage.showAndWait(); // öppnar upp en popUp och denna scenen väntar
        setListView(); // Uppdaterar konto llista 
    }
    

    @FXML
    private void deleteAccountEvent(ActionEvent e) throws IOException { // metod för att ta bort ett konto
        transferStatus.setText("");
        mainStatus.setText("");

        try {
            String s1 = (String) accountList.getSelectionModel().getSelectedItem();
            if (s1.isEmpty()) { // om man ej valt ett konto
                throw new NullPointerException();
            }

            b.setAccountNr(Integer.parseInt(s1.replaceAll("[A-Öa-ö ]", "").trim())); // sparar kontonr till bankLogic för att ta med det till popUpen
            b.setpNr(getThisObject().getPnr()); // sparr personNr på vald kund för att ta med det till popupen
            
            Stage stage; 
            Parent root;

            stage = new Stage();
            stage.setTitle("Delete customer account");
            stage.setResizable(false);
            root = FXMLLoader.load(getClass().getResource("FXMLpopUp5.fxml"));
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(addAccountButton.getScene().getWindow());
            stage.setOnCloseRequest((WindowEvent we) -> {
                stage.close();
            });
            stage.showAndWait(); // öppnar popUpen och låter denna scen vänta i bakgrunden
            
            setListView(); // uppdaterar konto och transactionslist
            setTransactions();


        } catch (NullPointerException ex) {
            mainStatus.setText("You have to select an account!");
        }
    }

    @FXML
    private void back() throws IOException { // om man trycker på back går man tillbaka till scen 1 

        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) back.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        b = BankLogic.getInstance();

        transactionObservable = FXCollections.observableArrayList();
        transactionList.setItems(transactionObservable);
        accountObservableList = FXCollections.observableArrayList();
        transferFrom.setItems(accountObservableList);
        transferTo.setItems(accountObservableList);
        accountList.setItems(accountObservableList);

        //Listener som tar förändringar i account listView
        accountList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                Customer c = getThisObject();
                try {
                    String str = (String) accountList.getSelectionModel().getSelectedItem();

                    if (!str.isEmpty()) { // om ej tom
                        str = str.replaceAll("[A-Öa-ö -]", "");
                        accountNr.setText(str); // sätter label till rätt accountNr
                        setTransactions(); // uppdaterar transactions list med det nya kontot transactions
                        exportStatus.setText("");

                        accountList.getSelectionModel().setSelectionMode(null);

                    }
                } catch (NullPointerException e) {
                }
            }
        });

        String sl = Long.toString(b.getpNr()); // tar emot personnr på vald kund från scen 1 via bankLogic
        sl = sl.substring(0, 8) + "-" + sl.substring(8, sl.length()); // gör om till string och placerar ett '-' mellan datum och de fyra sista siffrorna
        String namn = null;
        ArrayList<Customer> a = b.getCustomerList();
        for (Customer c : a) { // löper igenom kundlistan för att hämta rätt namn med hjälp av personnr
            if (c.getPnr() == b.getpNr()) {
                namn = c.getName();
                break;
            }
        }
        name.setText(namn); // label namn till rätt namn
        ssn.setText(sl); // label ssn till personnr
        setListView(); // uppdaterar kontolistan
    }

    private void setListView() {  // metod för att lägga samtliga kunders konto i listView
        accountObservableList.clear(); // tömmer listan först för att sen lägga in alla konton på nytt
        String g = ssn.getText();

        g = g.replaceAll("-", "");
        long pNr = Long.parseLong(g);

        ArrayList<Customer> tC = b.getCustomerList();

        for (Customer c : tC) {
            if (c.getPnr() == pNr) {
                ArrayList<Account> a = c.getAccountList();
                if (!a.isEmpty()) {
                    for (Account a1 : a) {
                        accountObservableList.add(a1.getAccountNumber() + " " + a1.getAccountName()); // löper igenom listan med konto och lägger till alla
                    }
                }
            }
        }

    }

    private void setTransactions() { // Metod för att uppdatera transactions list med transactions för valt konto
        try{
        transactionObservable.clear();     // börjar med att tömma listan
        String str = accountNr.getText().trim(); // hämtar valt konto
        if(str.isEmpty())  // om man ännu inte valt ett konto
            throw new NullPointerException();

        if (getThisObject().getSelectedAccount(Integer.parseInt(str)).getAccountName().equals("Saving Account")) { // om det är ett savingsaccount
            SavingsAccount sA = (SavingsAccount) getThisObject().getSelectedAccount(Integer.parseInt(str));
            int i = sA.getnumberOfWithdraw(); // hämatr tidigare utag
            if (i > 0) { // om man tagit ut pengar tidigare sätts räntan till 2%
                rate.setText("2%");
            } else { // annars 0%
                rate.setText("0%");
            }

        } else {
            rate.setText("0%"); // om det är ett creditAccount är uttagsräntan alltid 0%
        }
        
        int aNr = Integer.parseInt(str);

        Customer c = getThisObject();

        ArrayList<Transaction> arr = c.getSelectedAccount(aNr).getTransaction();
        String d = String.format("%.2f", c.getSelectedAccount(aNr).getBalance());

        transactionObservable.add("Account number: " + aNr + "\t Balance: " + d); // lägger till valt konto och balance överst i listan

        if (!arr.isEmpty()) { // om transactionslist inte är tom

            for (Transaction t : arr) { // fyller listan med alla transactions
                transactionObservable.add(t.toString());
            }
        }
        d = String.format("%.2f", c.getSelectedAccount(aNr).getBalance()); // hämtar balance och sätter till 2 decimaler
        balance.setText(d); // uppdaterar label balance med den sista balance
        }catch(NullPointerException e){}

    }

    private Customer getThisObject() { // metod för att returnera kunden vi befinner oss fördjupad i!
        long l = Long.parseLong(ssn.getText().replaceAll("-", "").trim());
        ArrayList<Customer> cL = b.getCustomerList();
        Customer rC = null;
        for (Customer c : cL) {
            if (c.getPnr() == l) {
                rC = c;
            }
        }
        return rC;
    }
}
