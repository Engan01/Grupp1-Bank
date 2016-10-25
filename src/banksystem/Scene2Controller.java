package banksystem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author asanilssonenglund
 */
public class Scene2Controller implements Initializable {

    
    @FXML
    private Label name;
//    
//    @FXML
//    private Button editName;
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
//    
//    @FXML
//    private Button back;
    
    
    
    @FXML
    public void editName(ActionEvent e){
        
        
        // popup-kod för att ändra namn
        
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
        // TODO
    }    
    
}
