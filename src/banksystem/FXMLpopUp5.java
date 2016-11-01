package banksystem;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Anton
 */
public class FXMLpopUp5 implements Initializable {

    private Singelton s;
    @FXML
    private Button confirmPop5, cancelPop5;

    @FXML
    private Label balancePop5;

    @FXML
    private Label interestRatePop5;

    @FXML
    private Label totalAmountPop5;

    @FXML
    private void confirmPop5(ActionEvent event) {

        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.close();
       
    }

    @FXML
    private void cancelPop5(ActionEvent event) {

        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
        s=Singelton.getInstance();
        balancePop5.setText(String.valueOf(s.getD()));
         interestRatePop5.setText(String.valueOf(s.getD2()));
          totalAmountPop5.setText(String.valueOf(s.getdT()));
    }

}
