package banksystem;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
    private Label balancePop5;

    @FXML
    private Label interestRatePop5;

    @FXML
    private Label totalAmountPop5;
    
    @FXML
    private Label creditAccountLabel;

    @FXML
    private void confirmPop5(ActionEvent event) {
       s.setB(Boolean.TRUE);
        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.close();
       
    }

    @FXML
    private void cancelPop5(ActionEvent event) {
        s.setB(Boolean.FALSE);
        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
        s=Singelton.getInstance();
        balancePop5.setText(s.getD()+" SEK");
         interestRatePop5.setText(Math.round(s.getD2())+" %");
          totalAmountPop5.setText(s.getdT()+" SEK");
          
          if(s.getB2()){
              creditAccountLabel.setText("You have a debt of " + -1 * s.getdT() + " SEK");
          }
    }

}
