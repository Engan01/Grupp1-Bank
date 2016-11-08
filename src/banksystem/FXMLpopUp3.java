package banksystem;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Anton
 */

public class FXMLpopUp3 implements Initializable {
    
    private BankLogic b;
    
    @FXML
    private TextField textFieldPop3;
    
    @FXML
    private Label error;

    @FXML
    private void confirmPop3(ActionEvent event) {
        try{
        String newName = textFieldPop3.getText();
        newName = newName.trim();
                if (newName.isEmpty()) {
                    error.setText("No new name selected!");
                    
                    throw new NullPointerException();// Den här exception får man när man inte skriver något namn
                }
                
                int i1 = 0;// en variabel för att räkna ut hur många mellanslag på namnet
                int i2 = 0;// en variabel för att räkna ut hur många bindestreck på namnet
                for (int i = 0; i < newName.length(); i++) {
                    if (newName.charAt(i) == ' ') {
                        i1++;
                    } else if (newName.charAt(i) == '-') {
                        i2++;
                    }
                }
                if (i1 > 2 || i2 > 1) { // man kan max ha två mellanslag i sitt namn och ett "-"
                    error.setText("Invalid name!");
                    throw new NullPointerException();
                }
                //Den här är för att se om namnet har något annat än bokstäver och bindestreck
                String s1 = newName.replaceAll("[A-Za-z -]", "");
                s1 = s1.replaceAll("[ÅÄÖåäö]", "");
                if (!s1.isEmpty()) {
                    error.setText("Name can only contain letters!");
                    throw new NullPointerException();
                }

                boolean b1 = b.changeCustomerName(newName, b.getpNr()); // metod i bankLogic för att byta namn

        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.close();
        }catch(NullPointerException e){}
    }

    @FXML
    private void cancelPop3(ActionEvent event) { 
        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        b = BankLogic.getInstance();
    }

}
