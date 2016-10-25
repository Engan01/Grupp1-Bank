package banksystem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author asanilssonenglund
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label label;
    
    @FXML
    private Button addCustomerButton, deleteCustomerButton,searchButton,
                        clearSearchButton,ExportButton,viewProfileButton ;

    

    @FXML
    private void addCustomer(ActionEvent event) throws IOException {

        Stage stage;
        Parent root;
        if (event.getSource() == addCustomerButton) {
            stage = new Stage();
            root = FXMLLoader.load(getClass().getResource("FXMLpopUp1.fxml"));
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(addCustomerButton.getScene().getWindow());
            stage.showAndWait();
        } else {
            stage = (Stage) addCustomerButton.getScene().getWindow();
            stage.close();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Hej svejs");
        System.out.println("nytt test för delgruppA");
    }

}
