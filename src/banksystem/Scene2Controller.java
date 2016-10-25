package banksystem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
    
    private BankLogic b;

    
    @FXML
    private Label name;
    
    @FXML
    private Button editNameButton;
//    
//    @FXML
//    private Label ssn;
//    
//    @FXML
//    private ListView customerList;
//    
//    @FXML
//    private Button deleteCustomer;
//    
//    @FXML
//    private Button addCustomer;
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
    
    @FXML
    private Label withdrawPop5;
    
    @FXML
    private Label interestRatePop5;
    
    @FXML
    private Label totalAmountPop5;
    
  
    @FXML
    private Button back;
    
    @FXML
    private Button confirmPop3, cancelPop3;
    
    
    
    @FXML
    public void editName(ActionEvent e) throws IOException{

        // popup-kod för att ändra namn
       
        Stage stage;
        Parent root;

        stage = new Stage();
        root = FXMLLoader.load(getClass().getResource("FXMLpopUp3.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(editNameButton.getScene().getWindow());
        stage.showAndWait();
      
    }
    
        @FXML
    private void confirmPop3(ActionEvent event) {

        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.close();

    }
    
        @FXML
    private void cancelPop3(ActionEvent event) {

        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.close();

    }
    
    
    
    @FXML
    public void addAccount(){
        
    }
    
    @FXML
    public void deleteAccount(){
        
    }
    
    @FXML
    public void back() throws IOException{
        
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) back.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
    }
    
    @FXML
    public void deposit(){
        
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
    }    
    
}
