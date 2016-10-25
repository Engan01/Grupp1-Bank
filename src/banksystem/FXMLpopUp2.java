package banksystem;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Anton
 */
public class FXMLpopUp2 implements Initializable {
    
    Singelton s;
    
    
    

    @FXML
    private void confirmPop2(ActionEvent event) {
        s.setB(Boolean.TRUE);
        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.close();

    }
    @FXML
    private void cancelPop2(ActionEvent event) {
        s.setB(Boolean.FALSE);
        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.close();

    }


    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        s = Singelton.getInstance();
    }    
    
}
