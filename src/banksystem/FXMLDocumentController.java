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
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author asanilssonenglund
 */

public class FXMLDocumentController implements Initializable {
    
    private BankLogic b;

    @FXML
    private TextField ssnField;

    @FXML
    private ListView customersList; 
    
    @FXML
    private TextArea customerDetailList;

    @FXML
    private Button addCustomerButton;
    
    @FXML
    private Button deleteCustomerButton, viewProfileButton;

    @FXML
    private Button confirmPop1, cancelPop1, confirmPop2, cancelPop2;
    
    @FXML
    public void viewProfile(ActionEvent event) throws IOException{
        
        Parent root = FXMLLoader.load(getClass().getResource("scene2.fxml"));
        Scene s1 = new Scene(root);
        Stage stg = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stg.setScene(s1);
        stg.show(); 
        
    }
    
  

    @FXML
    private void addCustomer(ActionEvent event) throws IOException {

        Stage stage;
        Parent root;

        stage = new Stage();
        root = FXMLLoader.load(getClass().getResource("FXMLpopUp1.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(addCustomerButton.getScene().getWindow());
        stage.showAndWait();
    }

    @FXML
    private void confirmPop1(ActionEvent event) {

        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.close();

    }
    
        @FXML
    private void cancelPop1(ActionEvent event) {

        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.close();

    }
    
        @FXML
    private void deleteCustomer(ActionEvent event) throws IOException {

        Stage stage;
        Parent root;

        stage = new Stage();
        root = FXMLLoader.load(getClass().getResource("FXMLpopUp2.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(addCustomerButton.getScene().getWindow());
        stage.showAndWait();
    }

    @FXML
    private void confirmPop2(ActionEvent event) {

        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.close();

    }
    
        @FXML
    private void cancelPop2(ActionEvent event) {

        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        b = BankLogic.getInstance();
        long nr = 198905643943L;
        b.addCustomer("Kalle karlsson", nr);
        nr = 198905643843L;
        b.addCustomer("Peter haraldsson", nr);
        nr = 198967643943L;
        b.addCustomer("Hans haraldsson", nr);
        nr = 198905643978L;
        b.addCustomer("Harry haraldsson", nr);

    }

}
