package banksystem;

import DBrepository.DBT;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
public class FXMLpopUp1 implements Initializable {

    private BankLogic b; // bankLogic
    private DBT dbt;

    @FXML
    private TextField labelNamePop1, labelSsnPop1;

    @FXML
    private Label error;

    @FXML
    private void confirmPop1(ActionEvent event) {
        try {
            String n = labelNamePop1.getText(); // namn
            String n2 = labelSsnPop1.getText(); // SSN
            n = n.trim();
            n2 = n2.trim();

            if (n.isEmpty()) {
                error.setText("Please type a name!"); // om man ej fylt i namn
                throw new NullPointerException();
            }

            int i1 = 0;
            int i2 = 0;
            for (int i = 0; i < n.length(); i++) { // for loop för att räkna mellanslag och '-'
                if (n.charAt(i) == ' ') {
                    i1++;
                } else if (n.charAt(i) == '-') {
                    i2++;
                }
            }
            if (i1 > 2 || i2 > 1) { // man kan max ha två mellanslag i sitt namn och ett "-"
                error.setText("Invalid name!");
                throw new NullPointerException();
            }

            String s1 = n.replaceAll("[A-Za-z -]", "");
            s1 = s1.replaceAll("[ÅåÄäÖö]", ""); // ÅÄÖåäö är också tillåtet
            if (!s1.isEmpty()) {
                error.setText("Name can only contain letters!");
                throw new NullPointerException();
            }

            int i3 = n2.length();
            
            if(i3 != 13 && i3 != 12){ // om den varken är 13 eller 12 tecken långt
                throw new NumberFormatException();
            }
            if(i3 == 13 && !n2.contains("-")){ // om den är 13 tecken lång utan att innehålla "-" då är det för många siffror
                throw new NumberFormatException();
            }
            
            if (i3 == 13) { // ssn +  ett tecken
                int l = 0;
                for (int k = 0; k < n2.length(); k++) {
                    if (n2.charAt(k) == '-') {
                        l++; // om tecknet är "-"
                    }
                } // om man skrivit någor annat tecken än "-" ska det inte gå 
                if (l > 0) // tar bort "-" förutsat att det sitter på rätt plats
                {
                    n2 = n2.substring(0, 8) + n2.substring(9, n2.length());
                }
            }

            i3 = n2.length();
            long l = Long.parseLong(n2);

            LocalDate d = LocalDate.now(); // hämtar dagens datum
            LocalDate dd = d.minusYears(18); // dagens datum minus 18 år
            String tooYoung = dd.toString(); 
            dd = d.minusYears(120); // dagensdatum minus 120 år 
            String tooOld = dd.toString();

            int tY = Integer.parseInt(tooYoung.replaceAll("-", "").trim());
            int tO = Integer.parseInt(tooOld.replaceAll("-", "").trim());
            String n3 = n2.substring(0, 8); // sparar pesonner som datum utan de sista 4 siffrorna
            n3 = n3.substring(0, 4) + "-" + n3.substring(4, 6) + "-" + n3.substring(6, n3.length()); // lägger in '-' mellan år-månad-datum

            LocalDate date = LocalDate.parse(n3); // gör om till localDate - om det ej är ett giltigt datum kastas ett exception

            n3 = n3.replaceAll("-", "");
            int customer = Integer.parseInt(n3);

            if (customer > tY) { // kollar om personnr är för ungt
                error.setText("The customer can't be younger \nthan 18 years old!");
                throw new NullPointerException();
            } else if (customer < tO) { // kollar om personnr är för gammalt
                error.setText("The customer can't be older \nthan 120 years old!");
                throw new NullPointerException();
            }

            Boolean b1 = b.addCustomer(n, l); // lägger till kund
            if(!b1){ // om det inte gick finns kunden redan
                error.setText("Customer already exists!");
                throw new NullPointerException();
            }else{
                dbt.addCustomer(n, l);
            }
            
            Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stg.close(); // stänger stage

        } catch (NullPointerException ex) {
        } catch (NumberFormatException | DateTimeParseException ex) {
            error.setText("Invalid social security number!");
        }
    }

    @FXML
    private void cancelPop1(ActionEvent event) { // knapp cancel stänger stage
        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        b = BankLogic.getInstance();
        dbt = DBT.getInstance();

    }

}
