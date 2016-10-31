package banksystem;

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
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author asanilssonenglund
 */
public class Scene2Controller implements Initializable {

    private ObservableList<String> accountObservableList, transactionObservable;
    private BankLogic b;
    private Singelton s;

    @FXML
    private Label name;

    @FXML
    private Label ssn;

    @FXML
    private Label transferStatus, nameStatus, exportStatus, mainStatus;

    @FXML
    private Button editNameButton;

    @FXML
    private ListView accountList, transactionList;

    @FXML
    private Button addAccountButton;

    @FXML
    private Button back;
    
    @FXML
    private ChoiceBox transferFrom, transferTo;
    

    @FXML
    private TextField amount;
    
    @FXML
    private Label balance;
    

    @FXML
    public void deposit(ActionEvent e) {

        Customer c = getThisObject();
        String selectedAccount = (String) accountList.getSelectionModel().getSelectedItem();
        selectedAccount = selectedAccount.replaceAll("[A-Za-z ]", "").trim(); // tar bort namn. Kontonummer finns kvar
        
        int acountNR = Integer.parseInt(selectedAccount); // konverterar String acount# till int
        double amount2 = Double.parseDouble(amount.getText());  // konverterar String amount till double amount
        
        
        b.deposit(c.getPnr(), acountNR, amount2); // drar pengar från spcifikt konto
        
        
        
        
        
         // *** skrive ut nytt belopp på "balance" label
        for(int i = 0 ; i < c.getAccountList().size(); i ++ ){
            if(acountNR == c.getAccountList().get(i).getAccountNumber()){
                double newBalance = c.getAccountList().get(i).getBalance();
                
                String newString = String.valueOf(newBalance);
                balance.setText(newString);
            }
        }

    }
    
    
    
    @FXML
    public void withdraw(ActionEvent e) {
        Customer c = getThisObject();
        
        String selectedAccount = (String) accountList.getSelectionModel().getSelectedItem();
        selectedAccount = selectedAccount.replaceAll("[A-Za-z ]", "").trim(); // tar bort namn. Kontonummer finns kvar
        
        int acountNR = Integer.parseInt(selectedAccount); // konverterar String acount# till int
        double amount2 = Double.parseDouble(amount.getText());  // konverterar String amount till double amount
        
        
        b.withdraw(c.getPnr(), acountNR, amount2); // drar pengar från spcifikt konto
        
                
         // *** skrive ut nytt belopp på "balance" label
        for(int i = 0 ; i < c.getAccountList().size(); i ++ ){
            if(acountNR == c.getAccountList().get(i).getAccountNumber()){
                double newBalance = c.getAccountList().get(i).getBalance();
                
                String newString = String.valueOf(newBalance);
                balance.setText(newString);
            }
        }

    }



    @FXML
    public void exportToFile() {

    }

    @FXML
    public void transferButton() {

    }

    @FXML
    public void editName(ActionEvent e) throws IOException {
        nameStatus.setText("");

        Stage stage;
        Parent root;

        stage = new Stage();
        root = FXMLLoader.load(getClass().getResource("FXMLpopUp3.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(editNameButton.getScene().getWindow());
        stage.showAndWait();

        if (s.getB()) {
            try {
                String newName = s.getN();
                newName = newName.trim();
                if (newName.isEmpty()) {
                    nameStatus.setText("No new name selected!");
                    throw new NullPointerException();
                }
                int i1 = 0;
                int i2 = 0;
                for(int i = 0; i < newName.length(); i++){
                    if(newName.charAt(i) == ' '){
                        i1++;
                    }
                    else if(newName.charAt(i) == '-'){
                        i2++;
                    }
                }
                if(i1 > 2 || i2 > 1){ // man kan max ha två mellanslag i sitt namn och ett "-"
                    nameStatus.setText("Invalid name!");
                    throw new NullPointerException();
                }
                
                String s1 = newName.replaceAll("[A-Za-z -]", "");
                if (!s1.isEmpty()) {
                    nameStatus.setText("Name can only contain letters!");
                    throw new NullPointerException();
                }

                s.setN(null);
                s.setB(Boolean.FALSE);

                String n = ssn.getText();
                n = n.replaceAll("-", "").trim();
                long l = Long.parseLong(n);

                ArrayList<Customer> a = b.getCustomerList();

                for (Customer c : a) {
                    if (c.getPnr() == l) {
                        c.setName(newName);
                        name.setText(newName);
                    }
                }

            } catch (NullPointerException ex) {

            }
        } else {
            nameStatus.setText("No new name selected!");
        }

    }

    @FXML
    public void addAccountEvent(ActionEvent e) throws IOException {

        Stage stage;
        Parent root;

        stage = new Stage();
        root = FXMLLoader.load(getClass().getResource("FXMLpopUp4.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(addAccountButton.getScene().getWindow());
        stage.showAndWait();

        String s1 = ssn.getText();
        s1 = s1.replaceAll("-", "");
        long l = Long.parseLong(s1);

        if (s.getB()) {
            int i = s.getI();
            switch (i) {
                case (1):
                    int g = b.addSavingsAccount(l);
                    break;
                case (-1):
                    g = b.addCreditAccount(l);
                    break;
            }
        }
        s.setB(Boolean.FALSE);
        s.setI(0);
        setListView();
    }

    @FXML
    public void deleteAccountEvent(ActionEvent e) throws IOException {
        try {
            String s1 = (String) accountList.getSelectionModel().getSelectedItem();
            if (s1.isEmpty()) {
                throw new NullPointerException();
            }

            Stage stage;
            Parent root;

            stage = new Stage();
            root = FXMLLoader.load(getClass().getResource("FXMLpopUp5.fxml"));
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(addAccountButton.getScene().getWindow());
            stage.showAndWait();
            setListView();
        } catch (NullPointerException ex) {
            mainStatus.setText("You have to select a account!");
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
        
                accountList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String str = (String) accountList.getSelectionModel().getSelectedItem();
                setTransactions(str);
            }
        });
                //Fylling i listorna med alla konton som en viss kund har
                
 
                
    }

    public void setListView() {  // metod för att lägga samtliga kunders konto i listView
        String g = ssn.getText();
        g = g.replaceAll("-", "");
        long pNr = Long.parseLong(g);

        accountObservableList = FXCollections.observableArrayList();

        ArrayList<Customer> tC = b.getCustomerList();

        for (Customer c : tC) {
            if (c.getPnr() == pNr) {
                ArrayList<Account> a = c.getAccountList();
                for (Account a1 : a) {
                    accountObservableList.add(a1.getAccountNumber() + " " + a1.getAccountName());
                }
            }
        }
        transferFrom.setItems(accountObservableList);
        transferTo.setItems(accountObservableList);

        accountList.setItems(accountObservableList);
        
    }
    
    public void setTransactions(String str){
        transactionObservable = FXCollections.observableArrayList();
        str  = str.replaceAll("[^0-9]", "").trim();
        int aNr = Integer.parseInt(str);
        
        Customer c = getThisObject();
        
        ArrayList<Transaction> arr = c.getSelectedAccount(aNr).getTransaction(); 
        transactionObservable.add("Account number: " + aNr + "\t Balance: " + c.getSelectedAccount(aNr).getBalance());
        
        if(!arr.isEmpty()){
           
        for(Transaction t : arr){
            transactionObservable.add(t.toString());
        }
        }
       
        transactionList.setItems(transactionObservable);
        
        
    }
    
    public Customer getThisObject(){
        String sn = ssn.getText();
        sn = sn.replaceAll("-", "").trim();
        long l = Long.parseLong(sn);
        ArrayList<Customer> cL = b.getCustomerList();
        Customer rC = null;
        for(Customer c : cL){
            if(c.getPnr() == l)
                rC = c;
        }
        
        return rC;
    }

}
