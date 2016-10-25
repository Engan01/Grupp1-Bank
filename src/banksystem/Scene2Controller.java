package banksystem;

import java.io.IOException;
import java.net.URL;
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

    private ObservableList<String> accountObservableList = FXCollections.observableArrayList();
    private BankLogic b;
    
//    @FXML
//    private Label name;
//    
    @FXML
    private Button editName;
//    
//    @FXML
//    private Label ssn;
//    
    @FXML
    private ListView accountList;
    
    @FXML
    private Button deleteAccount;
//    
    @FXML
    private Button addAccount;
//    
//    @FXML
//    private Label accountNr;
//    
//    @FXML
//    private Label balance;
//    
//    @FXML
//    private Label rate;
//    
//    @FXML
//    private Button deposit;
//    
//    @FXML
//    private Button withdraw;
//    
//    @FXML
//    private TextField amount;
//    
//    @FXML
//    private ListView transactionList;
//    
//    @FXML
//    private Button exportToFile;
//    
//    @FXML
//    private Label mainStatus;
//    
//    @FXML
//    private ChoiceBox transferFrom;
//    
//    @FXML
//    private ChoiceBox transferTo;
//    
//    @FXML
//    private TextField transferAmount;
//    
//    @FXML
//    private Label transferStatus;
//    
    @FXML
    private Button back;
    
    
    
    @FXML
    public void editName(ActionEvent e) throws IOException{
        
        // **** POP-UP 3 (edit name) ****
        Stage stage;
        Parent root;
        
        stage = new Stage();
        root = FXMLLoader.load(getClass().getResource("FXMLpopUp3.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(editName.getScene().getWindow());
        stage.showAndWait();
        
        
    }
    
    
    
    @FXML
    public void addAccount(ActionEvent e) throws IOException{
        
          // **** POP-UP 4 (add account) ****
        Stage stage;
        Parent root;
        
        stage = new Stage();
        root = FXMLLoader.load(getClass().getResource("FXMLpopUp4.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(addAccount.getScene().getWindow());
        stage.showAndWait();
        
        
    }
    
    @FXML
    public void deleteAccount(ActionEvent e) throws IOException{
        
        String selectedAccount = (String) accountList.getSelectionModel().getSelectedItem();
        if("Test".equals(selectedAccount)){
            accountObservableList.remove(0);
        }
        

          // **** POP-UP 5 (delete SAVINGS account) ****
//        Stage stage;
//        Parent root;
//        
//        stage = new Stage();
//        root = FXMLLoader.load(getClass().getResource("FXMLpopUp5.fxml"));
//        stage.setScene(new Scene(root));
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.initOwner(deleteAccount.getScene().getWindow());
//        stage.showAndWait();
//        
           // **** POP-UP 6 (delete CHECKING account) ****
//        Stage stage;
//        Parent root;
//        
//        stage = new Stage();
//        root = FXMLLoader.load(getClass().getResource("FXMLpopUp6.fxml"));
//        stage.setScene(new Scene(root));
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.initOwner(deleteAccount.getScene().getWindow());
//        stage.showAndWait();
        
    }
    
    @FXML
    public void back(ActionEvent e) throws IOException{
        
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) back.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
    }
    
    @FXML
    public void deposit(ActionEvent e){
        
    }
    
    @FXML
    public void withdraw(){
        
    }
    
    @FXML
    public void exportToFile(){
        
    }
    
    @FXML
    public void transferButton(){
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        b = BankLogic.getInstance();
        
        SavingAccount s1 = new SavingAccount(1002, 5000, 0.07);
        CreditAccount c1 = new CreditAccount(2014, 4000, 0.05);
        
        
        accountObservableList.add(s1.getClass().getName());
        accountObservableList.add(c1.getClass().getName());
        
        accountList.setItems(accountObservableList);
        
    }    
    
}
