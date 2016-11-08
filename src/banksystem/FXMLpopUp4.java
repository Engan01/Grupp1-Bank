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

public class FXMLpopUp4 implements Initializable {
    
    private BankLogic b;


    @FXML
    private void saving(ActionEvent event) { //Event Handler för knappen savingsaccount, där man kan skapa ett nytt konto
        int g = b.addSavingsAccount(b.getpNr());//skapar ett savings account samt initierar g till kontoNr
        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.close();

    }
    
        @FXML
    private void credit(ActionEvent event) { //Event Handler för knappen credit account, där man kan skapa ett nytt konto
        int g = b.addCreditAccount(b.getpNr()); // skapar ett credit account samt initierar g till kontoNr
        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.close();

    }

    @FXML
    private void cancelPop4(ActionEvent event) { // Event handler för cancelknappen, Om man trycker på knappen då stängs popupen och vi återgår till scene2
        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        b = BankLogic.getInstance(); 
     
    }

}
