package banksystem;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Anton
 */
public class FXMLpopUp1 implements Initializable {
    
    private Singelton s;
    
 
    @FXML
    private TextField labelNamePop1;
    
    @FXML
    private TextField labelSsnPop1;

    @FXML
    private void confirmPop1(ActionEvent event) {
        s.setN(labelNamePop1.getText());
        s.setB(Boolean.TRUE);
        String s1 = labelSsnPop1.getText();
        s1 = s1.replaceAll("-", "").trim();
        s.setN2(s1);
        
        
        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.close();
    }

    @FXML
    private void cancelPop1(ActionEvent event) {
        s.setB(Boolean.FALSE);

        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        s = Singelton.getInstance();

    }

}
