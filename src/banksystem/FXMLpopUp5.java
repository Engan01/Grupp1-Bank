package banksystem;

import java.net.URL;
import java.util.ArrayList;
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
    private BankLogic b;  

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
        ArrayList<Customer> arr = b.getCustomerList();
        for(Customer ss : arr){
            if(ss.getPnr() == s.getL())
                ss.closeAccount(s.getI());
        }
        
        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.close();
       
    }

    @FXML
    private void cancelPop5(ActionEvent event) {s.setB(Boolean.FALSE);
        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        s = Singelton.getInstance();
        b = BankLogic.getInstance();
        ArrayList<Customer> arr = b.getCustomerList();
        Account selectedAccount = null;
        for(Customer c : arr){
            if(c.getPnr() == s.getL()){
                selectedAccount = c.getSelectedAccount(s.getI());
            }
        }
        
        balancePop5.setText(selectedAccount.getBalance() + " SEK");
        interestRatePop5.setText(String.format("%.1f", selectedAccount.getInterest() * 100) + " %");
        totalAmountPop5.setText(selectedAccount.getTotalBalance() + " SEK");
          
        if(selectedAccount.getTotalBalance() < 0)
           creditAccountLabel.setText("You have a debt of " + -1 * selectedAccount.getTotalBalance() + " SEK");
           
    }

}
