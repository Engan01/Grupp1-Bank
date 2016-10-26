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

public class FXMLpopUp3 implements Initializable {
    
    private Singelton s;
    
    @FXML
    private TextField textFieldPop3;

    @FXML
    private void confirmPop3(ActionEvent event) {
        String name = textFieldPop3.getText();
        s.setN(name);
        s.setB(Boolean.TRUE);

        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.close();

    }

    @FXML
    private void cancelPop3(ActionEvent event) {

        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        s = Singelton.getInstance();
    }

}
