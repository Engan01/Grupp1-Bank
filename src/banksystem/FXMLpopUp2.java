package banksystem;


import java.net.URL;
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
public class FXMLpopUp2 implements Initializable {
    
    @FXML
    private Label row1, row2, row3, row4, row5;
           
    
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
        int i = s.getI1() + s.getI2();
        row1.setText(Integer.toString(s.getI()));
        row2.setText(Integer.toString(i));
        row3.setText(Integer.toString(s.getI2()));
        row4.setText(Integer.toString(s.getI1()));
        String ss = String.format("%.2f", s.getD());
        row5.setText(ss);
    }    
    
}
