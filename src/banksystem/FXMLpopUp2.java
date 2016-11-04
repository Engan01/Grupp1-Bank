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
public class FXMLpopUp2 implements Initializable {
    
    @FXML
    private Label row11, row12, row13, row14, row21, row22, row23, row24, row31, row32, row33, row34, row41, row44;
           
    
    Singelton s;
    BankLogic b;


    @FXML
    private void confirmPop2(ActionEvent event) { 
        String[] ss = b.removeCustomer(s.getL()); // tar bort kunden samt tar emot en lista med information om kunden enligt projetet
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
        b = BankLogic.getInstance();
        
        long pNr = s.getL();
        
        Customer c = null;
            ArrayList<Customer> lista = b.getCustomerList();
            for(Customer c1 : lista){
                if(pNr == c1.getPnr())
                    c = c1;
            }
            ArrayList<Account> arr = c.getAccountList();
            
            int savingsAccounts = 0;
            double savingsBalance = 0;
            double savingTotal = 0;
            
            int creditAccountsPlus = 0;
            double plusBalance = 0;
            double plusTotal = 0;
            
            int creditAccountsMinus = 0;
            double minusBalance = 0;
            double minusTotal = 0;
            
  
            for(Account a : arr){
                if(a.getAccountName().equals("Saving Account")){
                    savingsAccounts++;
                    savingsBalance += a.getBalance();
                    savingTotal += a.getTotalBalance();
                }else{
                    
                    double d = a.getBalance();
                    
                    if(d < 0){
                       minusBalance += a.getBalance();
                       double bb = a.getBalance();
                       bb = bb * 0.07;
                       minusTotal += a.getTotalBalance();
                       creditAccountsMinus++;
                    }
                    else{
                        plusBalance += a.getBalance();
                        plusTotal += a.getTotalBalance();
                        creditAccountsPlus++;
                    }
                }
            }
            row11.setText(Integer.toString(savingsAccounts));
            row12.setText(String.format("%.2f", savingsBalance));
            row13.setText("1%");
            row14.setText(String.format("%.2f", savingTotal));
            row21.setText(Integer.toString(creditAccountsPlus));
            row22.setText(String.format("%.2f", plusBalance));
            row23.setText("0,5%");
            row24.setText(String.format("%.2f", plusTotal));
            row31.setText(Integer.toString(creditAccountsMinus));
            row32.setText(String.format("%.2f", minusBalance));
            row33.setText("-7%");
            row34.setText(String.format("%.2f", minusTotal));
            
            row41.setText(Integer.toString(savingsAccounts + creditAccountsPlus + creditAccountsMinus));
        
            row44.setText(String.format("%.2f", savingTotal + plusTotal + minusTotal));
            
            
            
            
            
    }    
    
}
