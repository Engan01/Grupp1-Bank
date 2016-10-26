package banksystem;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    private ObservableList<String> accountObservableList;
    private BankLogic b;
    private Singelton s;

    @FXML
    private Label name;
    
    @FXML
    private Label ssn;
    
    @FXML
    private Label transferStatus;

    @FXML
    private Button editNameButton;

    @FXML
    private ListView accountList;

    @FXML
    private Button addAccountButton;

    @FXML
    private Button back;
    
    @FXML
    private TextField amount;

    @FXML
    public void deposit(ActionEvent e) {

    }

    @FXML
    public void withdraw(ActionEvent e) {
        
        
        String selectedAccount = (String)accountList.getSelectionModel().getSelectedItem(); 
        selectedAccount = selectedAccount.replaceAll("[A-Za-z ]", "").trim(); // tar bort namn. Kontonummer finns kvar
        
        // behöver konvertera String till int för att gå vodare
//        System.out.println(selectedAccount);
//        
//        
//        double newNum = Integer.parseInt(amount.getText().toString());
//        b.withdraw(0, selectedAccount, newNum);
        
        

    }

    @FXML
    public void exportToFile() {

    }

    @FXML
    public void transferButton() {

    }

    @FXML
    public void editName(ActionEvent e) throws IOException {
       
        Stage stage;
        Parent root;

        stage = new Stage();
        root = FXMLLoader.load(getClass().getResource("FXMLpopUp3.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(editNameButton.getScene().getWindow());
        stage.showAndWait();
        
        if(s.getB()){
            try{
        String newName = s.getN();
        newName = newName.trim();
        if(newName.isEmpty()){
            transferStatus.setText("No new name selected!");
            throw new NullPointerException();
        }
        String s1 = newName.replaceAll("[A-Za-z ]", "");
        if(!s1.isEmpty()){
            transferStatus.setText("Name can't contain numbers!");
            throw new NullPointerException();
        }
        
        s.setN(null);
        s.setB(Boolean.FALSE);
        
        String n = ssn.getText();
        n = n.replaceAll("-", "").trim();
        long l = Long.parseLong(n);
        
        ArrayList<Customer> a = b.getCustomerList();
        
        for(Customer c : a){
            if(c.getPnr() == l){
                c.setName(newName);
                name.setText(newName);
            }
        }
        
            }catch(NullPointerException ex){
                
            }
        }else{
            transferStatus.setText("No new name selected!");
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
        
        if(s.getB()){
            int i = s.getI();
            switch(i){
                case(1):
                    int g = b.addSavingsAccount(l);
                    break;
                case(-1):
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
        try{
        String s1 = (String)accountList.getSelectionModel().getSelectedItem();
        if(s1.isEmpty())
            throw new NullPointerException();
        
        Stage stage;
        Parent root;

        stage = new Stage();
        root = FXMLLoader.load(getClass().getResource("FXMLpopUp5.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(addAccountButton.getScene().getWindow());
        stage.showAndWait();
        setListView();
        }catch(NullPointerException ex){
            transferStatus.setText("You have to select a account!");
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
        for(Customer c : a){
            if(c.getPnr() == l){
                namn = c.getName();
                break;
            }
        }
        name.setText(namn);
        ssn.setText(sl);
        setListView();
    }
    
    
        public void setListView() {  // metod för att lägga samtliga kunders konto i listView
        String g = ssn.getText();
        g = g.replaceAll("-", "");
        long pNr = Long.parseLong(g);
        
        accountObservableList = FXCollections.observableArrayList();

        ArrayList<Customer> tC = b.getCustomerList();
        

        for (Customer c : tC) {
            if(c.getPnr() == pNr){
                ArrayList<Account> a = c.getAccountList();
                for(Account a1 : a){
                    accountObservableList.add(a1.getAccountNumber() + " " + a1.getAccountName());
                }
            }
        }

        accountList.setItems(accountObservableList);
    }

}
