/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banksystem;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author asanilssonenglund
 */
public class Scene2Controller implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Label name;
    
    @FXML
    private Button editName;
    
    
    @FXML
    private Label ssn;
    
    @FXML
    private ListView customerList;
    
    @FXML
    private Button deleteCustomer;
    
    @FXML
    private Button addCustomer;
    
    @FXML
    private Label accountNr;
    
    @FXML
    private Label balance;
    
    @FXML
    private Label rate;
    
    @FXML
    private Button deposit;
    
    @FXML
    private Button withdraw;
    
    @FXML
    private TextField amount;
    
    @FXML
    private ListView transactionList;
    
    @FXML
    private Button exportToFile;
    
    @FXML
    private Label mainStatus;
    
    @FXML
    private ChoiceBox transferFrom;
    
    @FXML
    private ChoiceBox transferTo;
    
    @FXML
    private TextField transferAmount;
    
    @FXML
    private Label transferStatus;
    
    @FXML
    private Button back;
    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
