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

    @FXML
    private TextField ssnField;

    @FXML
    private ListView customersList; 
    
    @FXML
    private TextArea customerDetailList;

    @FXML
    private Button addCustomerButton;
    
    @FXML
    private Button deleteCustomerButton;

    @FXML
    private Button confirmPop1, cancelPop1, confirmPop2, cancelPop2;
    
  

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

    }

}
