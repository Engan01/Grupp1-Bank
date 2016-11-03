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

    private ObservableList<String> accountObservableList;
    private ObservableList<String> transactionObservable;
    private BankLogic b;
    private Singelton s;

    @FXML
    private Label name, ssn, rate, transferStatus, nameStatus, mainStatus, accountNr, balance;

    @FXML
    private Button editNameButton, addAccountButton, back;

    @FXML
    private ListView accountList, transactionList;


    @FXML
    private ChoiceBox transferFrom, transferTo;

    @FXML
    private TextField amount, amountTransfer;

    @FXML
    public void deposit(ActionEvent e) throws Exception {
        transferStatus.setText("");
        mainStatus.setText("");
        mainStatus.setTextFill(Color.RED);
        
        try {
            Customer c = getThisObject();
            String selectedAccount = (String) accountList.getSelectionModel().getSelectedItem();

            if (selectedAccount.isEmpty()) {
                throw new NullPointerException();

            }
            selectedAccount = selectedAccount.replaceAll("[A-Za-z ]", "").trim(); // tar bort namn. Kontonummer finns kvar

            int acountNR = Integer.parseInt(selectedAccount); // konverterar String acount# till int
            double amount2 = Double.parseDouble(amount.getText());  // konverterar String amount till double amount

            String saveSSN = ssn.getText(); // hämtar personNr
            saveSSN = saveSSN.replaceAll("-", ""); // tar bort "-" från PersonNr
            long l = Long.parseLong(saveSSN); // konverterar string PersonNr till long

            //drar pengar från spcifikt konto
            if (b.deposit(l, acountNR, amount2) == true) { // om det går bra
                c.getSelectedAccount(acountNR).addTransaction(true, amount2, c.getSelectedAccount(acountNR).getBalance());
                String gg = Integer.toString(acountNR);
                setTransactions();
                mainStatus.setTextFill(Color.GREEN);
                mainStatus.setText("Deposit succesfull!");
            } else {
                mainStatus.setText("Error. Deposit failed.");
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
    public void withdraw(ActionEvent e) throws Exception {
        transferStatus.setText("");
        mainStatus.setText("");
        mainStatus.setTextFill(Color.RED);

        try {
            Customer c = getThisObject();

            String selectedAccount = (String) accountList.getSelectionModel().getSelectedItem();

            if (selectedAccount.isEmpty()) {
                throw new NullPointerException();
            }

            selectedAccount = selectedAccount.replaceAll("[A-Za-z ]", "").trim(); // tar bort namn. Kontonummer finns kvar
            int acountNR = Integer.parseInt(selectedAccount); // konverterar String acount# till int

            double amount2 = Double.parseDouble(amount.getText());  // konverterar String amount till double amount

            String saveSSN = ssn.getText(); // hämtar personNr
            saveSSN = saveSSN.replaceAll("-", ""); // tar bort "-" från PersonNr
            long l = Long.parseLong(saveSSN); // konverterar string PersonNr till long

            if (b.withdraw(l, acountNR, amount2) == true) { // om det går bra

                c.getSelectedAccount(acountNR).addTransaction(false, amount2, c.getSelectedAccount(acountNR).getBalance());
                String gg = Integer.toString(acountNR);
                setTransactions();
                
                if("SavingsAccount".equals(c.getSelectedAccount(acountNR).getClass().getSimpleName())){
                    rate.setText("2%");
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
    public void exportToFile() {
            
       
        BufferedWriter writer = null;
        try {
           // String[] lista1 = b.getCustomers();
           
            Customer c = getThisObject();
            long p = c.getPnr();
            String ss = accountNr.getText();
            int ii = Integer.parseInt(ss);
            ArrayList<Transaction> t = c.getSelectedAccount(ii).getTransaction();
           
            String userHomeFolder = System.getProperty("user.home");
            File textFile = new File(userHomeFolder, "transactions.txt"); // lägger filen i hem mappen istället för i projektmappen
           // mainStatus.setText("File exported.");
            writer = new BufferedWriter(new FileWriter(textFile));
            for (Transaction t1 : t) {
                writer.write(t.toString() + "\n");
                System.out.println(t.toString());
            }

        } catch (IOException e) {
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
            }
        }
        }

    @FXML
    public void transferButton() {
        
        transferStatus.setText("");
        mainStatus.setText("");
        transferStatus.setTextFill(Color.RED);
      try{  
        Customer c = getThisObject();

        int selectedFromAccountNr = Integer.parseInt(transferFrom.getSelectionModel().getSelectedItem().toString().replaceAll("[^\\d.]", ""));

        int selectedToAccountNr = Integer.parseInt(transferTo.getSelectionModel().getSelectedItem().toString().replaceAll("[^\\d.]", ""));

        double selectedAccountsBalance = getThisObject().getSelectedAccount(selectedFromAccountNr).getBalance();
        try {
            double transferAmount = Double.parseDouble(amountTransfer.getText());

            //Om inte finns tillräckligt pengar på kontot
            if (selectedFromAccountNr == selectedToAccountNr) {
                transferStatus.setText("You can not transfer money to the same account!");
            } //I fall av fel inmatning
            
            else if("CreditAccount".equals(c.getSelectedAccount(selectedFromAccountNr).getClass().getSimpleName())) {
                   if(c.getSelectedAccount(selectedFromAccountNr).getBalance()-transferAmount >= -5000){
                       
                double newBalanceFromAccount = getThisObject().getSelectedAccount(selectedFromAccountNr).getBalance() - transferAmount;
                getThisObject().getSelectedAccount(selectedFromAccountNr).setBalance(newBalanceFromAccount);

                c.getSelectedAccount(selectedFromAccountNr).addTransaction(false, transferAmount, c.getSelectedAccount(selectedFromAccountNr).getBalance());
                String gg = Integer.toString(selectedFromAccountNr);
                       
                       
                double newBalanceToAccount = getThisObject().getSelectedAccount(selectedToAccountNr).getBalance() + transferAmount;
                getThisObject().getSelectedAccount(selectedToAccountNr).setBalance(newBalanceToAccount);

                c.getSelectedAccount(selectedToAccountNr).addTransaction(true, transferAmount, c.getSelectedAccount(selectedToAccountNr).getBalance());
                String gg2 = Integer.toString(selectedFromAccountNr);
                
                       setTransactions();
                       transferStatus.setTextFill(Color.GREEN);
                       transferStatus.setText("Transfer successful");
                       
                        }else{
                       transferStatus.setText("Transfer not possible!\nYou have reached your credit limit!");
                   }}
            
            
            else if (transferAmount <= 0) {
                transferStatus.setText("The amount to be transfered can not be negative or zero!");
            } //Om användaren väljer att skicka pengar till ett och samma konto
            else if (transferAmount > selectedAccountsBalance) {

                transferStatus.setText("There is not enough money in this account to perform this transfer!");
            }
            
            
             else {
                //Uppdatering saldo på första konto efter att överföra ett visst belopp
                double newBalanceFromAccount = getThisObject().getSelectedAccount(selectedFromAccountNr).getBalance() - transferAmount;
                getThisObject().getSelectedAccount(selectedFromAccountNr).setBalance(newBalanceFromAccount);

                c.getSelectedAccount(selectedFromAccountNr).addTransaction(false, transferAmount, c.getSelectedAccount(selectedFromAccountNr).getBalance());
                String gg = Integer.toString(selectedFromAccountNr);

                //Uppdatering saldo på andra konto efter att överföra ett visst belopp
                double newBalanceToAccount = getThisObject().getSelectedAccount(selectedToAccountNr).getBalance() + transferAmount;
                getThisObject().getSelectedAccount(selectedToAccountNr).setBalance(newBalanceToAccount);

                c.getSelectedAccount(selectedToAccountNr).addTransaction(true, transferAmount, c.getSelectedAccount(selectedToAccountNr).getBalance());
                String gg2 = Integer.toString(selectedFromAccountNr);

                setTransactions();
                //Visa användaren att det gick att överföra pengar
                
                transferStatus.setTextFill(Color.GREEN);
                transferStatus.setText("Transfer successful!");
            }     
    }
catch (NumberFormatException e) {
            transferStatus.setText("Invalid amount!");
        }
   
    }
       catch(NullPointerException e){
    
           transferStatus.setText("You need to choose accounts!");
}
    }
    @FXML
    public void editName(ActionEvent e) throws IOException {
        transferStatus.setText("");
        mainStatus.setText("");
        nameStatus.setText("");
        long l = getThisObject().getPnr();
        s.setL(l);

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
            s.setB(false); // ifall användaren trycker på X istället för confirm eller cancel tar denna text hand om det
           
        });
        stage.showAndWait(); // popUp startar och nuvarande scen väntar

        if (s.getB()) { // om användaren klickade confirm i popUpen
            name.setText(s.getN());
        } 
        s.setToNull();
    }

    @FXML
    public void addAccountEvent(ActionEvent e) throws IOException {
        transferStatus.setText("");
        mainStatus.setText("");

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
            s.setB(false);
        });
        stage.showAndWait();

        if (s.getB()) { // om man vill skapa ett konto
            long l = getThisObject().getPnr(); // hämtar personNr på kunden vi befinner oss fördjupad i
            int i = s.getI();
            switch (i) {
                case (1):
                    int g = b.addSavingsAccount(l); // skapar ett savingsaccount samt initierar g till kontoNr
                    String s4 = Integer.toString(g) + " Savings Account";
                    accountObservableList.add(s4); // lägger till i observableList
                    break;
                case (-1):
                    g = b.addCreditAccount(l); // skapar ett credit account samt initierar g till kontoNr
                    s4 = Integer.toString(g) + " Credit Account";
                    accountObservableList.add(s4); // lägger till i observableList
                    break;
            }
        }
        s.setB(Boolean.FALSE);
        s.setI(0);

    }

    @FXML
    public void deleteAccountEvent(ActionEvent e) throws IOException {
        transferStatus.setText("");
        mainStatus.setText("");

        try {
            String s1 = (String) accountList.getSelectionModel().getSelectedItem();
            String s2 = s1;
            if (s1.isEmpty()) {
                throw new NullPointerException();
            }

            String selectedAccountNumber = s1.replaceAll("[A-Za-z ]", "").trim();
            int selectedAccountNr = Integer.parseInt(selectedAccountNumber);
            Account selectedAccount = getThisObject().getSelectedAccount(selectedAccountNr);
            s.setD(selectedAccount.getBalance());
            s.setD2(selectedAccount.getInterest()*100);
            s.setdT(getThisObject().getSelectedAccount(selectedAccountNr).getTotalBalance());
            s.setB2(false);
            if(s.getdT()<0){
                s.setB2(true);
            }
            
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
                s.setB(false);
            });
            stage.showAndWait();

            if (s.getB()) {
                getThisObject().closeAccount(selectedAccountNr);
                accountObservableList.remove(s2);
            }
            transactionObservable.clear();

        } catch (NullPointerException ex) {
            mainStatus.setText("You have to select an account!");
        }

    }

    @FXML
    public void back() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) back.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        b = BankLogic.getInstance();
        s = Singelton.getInstance();

        transactionObservable = FXCollections.observableArrayList();
        transactionList.setItems(transactionObservable);
        accountObservableList = FXCollections.observableArrayList();
        transferFrom.setItems(accountObservableList);
        transferTo.setItems(accountObservableList);
        accountList.setItems(accountObservableList);

        //Listener
        accountList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                Customer c = getThisObject();
                try {
                    String str = (String) accountList.getSelectionModel().getSelectedItem();

                    if (!str.isEmpty()) {
                        str = str.replaceAll("[A-Za-z -]", "");
                        accountNr.setText(str);
                        setTransactions();
                        // konvertera ac

                        int accountNr2 = Integer.parseInt(str); // konverterar String till int
                    
                        if ("SavingAccount".equals(c.getSelectedAccount(accountNr2).getClass().getSimpleName())) {
                            int ii = 0;
                            for (Account a : getThisObject().getAccountList()) {
                                if (a.getAccountNumber() == accountNr2) {
                                    SavingsAccount ss = (SavingsAccount) a;
                                    ii = ss.getnumberOfWithdraw();
                                  
                                }
                            }
                            if(ii > 0)
                                rate.setText("2%");
                            else
                                rate.setText("0%");

                        } else {
                            rate.setText("0%");
                        }
                     

                        accountList.getSelectionModel().setSelectionMode(null);

                    }
                } catch (NullPointerException e) {
                }
            }
        });

        long l = s.getL();
        String sl = Long.toString(l);
        sl = sl.substring(0, 8) + "-" + sl.substring(8, sl.length());
        s.setL(null);
        String namn = null;
        ArrayList<Customer> a = b.getCustomerList();
        for (Customer c : a) {
            if (c.getPnr() == l) {
                namn = c.getName();
                break;
            }
        }
        name.setText(namn);
        ssn.setText(sl);
        setListView();
    }

    public void setListView() {  // metod för att lägga samtliga kunders konto i listView
        accountObservableList.clear();
        String g = ssn.getText();

        g = g.replaceAll("-", "");
        long pNr = Long.parseLong(g);

        ArrayList<Customer> tC = b.getCustomerList();

        for (Customer c : tC) {
            if (c.getPnr() == pNr) {
                ArrayList<Account> a = c.getAccountList();
                if (!a.isEmpty()) {
                    for (Account a1 : a) {
                        accountObservableList.add(a1.getAccountNumber() + " " + a1.getAccountName());
                    }
                }
            }
        }

    }

    public void setTransactions() {

        String str = accountNr.getText().trim();
        transactionObservable.clear();
        int aNr = Integer.parseInt(str);

        Customer c = getThisObject();

        ArrayList<Transaction> arr = c.getSelectedAccount(aNr).getTransaction();
        String d = String.format("%.2f", c.getSelectedAccount(aNr).getBalance());

        transactionObservable.add("Account number: " + aNr + "\t Balance: " + d);

        if (!arr.isEmpty()) {

            for (Transaction t : arr) {
                transactionObservable.add(t.toString());
            }
        }
        d = String.format("%.2f", c.getSelectedAccount(aNr).getBalance()); // hämtar balance och sätter till 2 decimaler
        balance.setText(d);

    }

    public Customer getThisObject() { // metod för att returnera kunden vi befinner oss fördjupad i!
        String sn = ssn.getText();
        sn = sn.replaceAll("-", "").trim();
        long l = Long.parseLong(sn);
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
