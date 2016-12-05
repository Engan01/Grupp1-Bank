package banksystem;

import DBrepository.DBT;
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
    
    private BankLogic b;  
    private DBT dbt = DBT.getInstance();

    @FXML
    private Label balancePop5;

    @FXML
    private Label interestRatePop5;

    @FXML
    private Label totalAmountPop5;
    
    @FXML
    private Label creditAccountLabel;

    @FXML
    private void confirmPop5(ActionEvent event) {//confirm för delete account
        ArrayList<Customer> arr = b.getCustomerList();//inicierar array "arr" av customer
        for(Customer ss : arr){//för arrayen av objekt
            if(ss.getPnr() == b.getpNr())//kolar på person nr
                dbt.deleteAccount(b.getAccountNr());
                ss.closeAccount(b.getAccountNr());//stänger account genom metoden och kör uträkning
        }
        
        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.close();//stäng popup
       
    }

    @FXML
    private void cancelPop5(ActionEvent event) {//cancel för delete account
        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();//hämtar popup
        stg.close();
        //cancel knappen, stänger popup
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        b = BankLogic.getInstance();//instance av backlogic med namn b
        ArrayList<Customer> arr = b.getCustomerList();//inicierar  arraylist av customer
        Account selectedAccount = null;// ingen accaunt är vald i list view
        for(Customer c : arr){//för array lista av customer c
            if(c.getPnr() == b.getpNr()){//om person nr stämer
                selectedAccount = c.getSelectedAccount(b.getAccountNr());//hämntar accoubt nr för selected konto
            }
        }
        
        balancePop5.setText(String.format("%.2f", selectedAccount.getBalance()) + " SEK");//visar balance
        interestRatePop5.setText(String.format("%.1f", selectedAccount.getInterest() * 100) + " %");//visar ränta
        totalAmountPop5.setText(String.format("%.2f", selectedAccount.getTotalBalance()) + " SEK");//visar den totala balance
          
        if(selectedAccount.getTotalBalance() < 0)//om skullden finns
           creditAccountLabel.setText(String.format("You have a debt of %.2f SEK", -1 * selectedAccount.getTotalBalance()));//skullden visas
           
    }

}
