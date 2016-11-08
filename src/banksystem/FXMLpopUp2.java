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
           
    BankLogic b;


    @FXML
    private void confirmPop2(ActionEvent event) { 
        String[] ss = b.removeCustomer(b.getpNr()); // tar bort kunden samt tar emot en lista med information om kunden enligt projetet
        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.close();

    }
    @FXML
    private void cancelPop2(ActionEvent event) { // avbryter borttagning av kund, återgår till föregeånde fönster
        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow(); 

    }


    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        b = BankLogic.getInstance(); 
        
        Customer c = null; // skapar ett Customer object 
            ArrayList<Customer> lista = b.getCustomerList(); // överför alla Customers till en ny lista 
            for(Customer c1 : lista){ // loopar igenom listan
                if(b.getpNr() == c1.getPnr()) // matchar personummer
                    c = c1; 
            }
            ArrayList<Account> arr = c.getAccountList(); // hämtar accountList från object c och lägger in i en ny ArrayList arr
            
            int savingsAccounts = 0; // tilldelar startvärden till 0
            double savingsBalance = 0;
            double savingTotal = 0;
            
            int creditAccountsPlus = 0;
            double plusBalance = 0;
            double plusTotal = 0;
            
            int creditAccountsMinus = 0;
            double minusBalance = 0;
            double minusTotal = 0;
            
  
            for(Account a : arr){
                if(a.getAccountName().equals("Saving Account")){ // om det är ett SavingsAccount
                    savingsAccounts++; // tilldelar värde för hur många SavingsAccount som exsisterar 
                    savingsBalance += a.getBalance(); // tilldelar balansvärde
                    savingTotal += a.getTotalBalance(); // tilldelar nytt totalBelopp
                }else{  // om det är ett CreditAccount
                    
                    double d = a.getBalance(); // hämta balance på creditAccount
                    
                    if(d < 0){                              // kontrollerar om balancen är negativ
                       minusBalance += a.getBalance();  // tilldealar ny balans
                       double bb = a.getBalance();
                       bb = bb * 0.07;          // beräknar balans till hänsyn med ränta
                       minusTotal += a.getTotalBalance();
                       creditAccountsMinus++;
                    }
                    else{                               // om balansen är positiv
                        plusBalance += a.getBalance();  // tilldela nya värden
                        plusTotal += a.getTotalBalance();
                        creditAccountsPlus++;
                    }
                }
            }
            /* Skriver ut information i tabellen. 
            Formaterar även värden så att de endast består av 2st decimaler.
            Detta gör vi med koden "%.2f"*/
            
            
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
            
            row41.setText(Integer.toString(savingsAccounts + creditAccountsPlus + creditAccountsMinus)); // Räknar ut totalt belopp på alla konton
        
            row44.setText(String.format("%.2f", savingTotal + plusTotal + minusTotal));      
    }    
    
}
