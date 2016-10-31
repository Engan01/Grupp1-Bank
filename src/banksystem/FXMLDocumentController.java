package banksystem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author asanilssonenglund
 */
public class FXMLDocumentController implements Initializable {

    private BankLogic b;
    private Singelton s;

    private ObservableList<String> oList;

    @FXML
    private TextField ssnField;

    @FXML
    private Label statusLabel;

    @FXML
    private ListView customersList;

    @FXML
    private Button addCustomerButton;

    @FXML
    private Button deleteCustomerButton;

    @FXML
    private TextArea customerDetailList;

    @FXML
    public void viewProfile(ActionEvent event) throws IOException {
        try {
            String customer = (String) customersList.getSelectionModel().getSelectedItem();
            if (customer.isEmpty()) {
                throw new NullPointerException();
            }
            customer = customer.replaceAll("[A-Za-z -]", "").trim();
            long l = Long.parseLong(customer);

            s.setL(l);

            Parent root = FXMLLoader.load(getClass().getResource("scene2.fxml"));
            Scene s1 = new Scene(root);
            Stage stg = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stg.setScene(s1);
            stg.show();
        } catch (NullPointerException ex) {
            statusLabel.setText("Select customer!");
        }

    }

    // search method
    @FXML
    public void search(ActionEvent event) throws IOException {
        statusLabel.setText("");

        String str = " ";
        Boolean ok = true;
        try {
            str = ssnField.getText();
            str = str.trim();
            for (int i = 0; i < str.length(); i++) { // nu går det även att skriva yyyymmdd-xxxx samt om man råkar få in ett mellanslag efter eller före // Anton 
                char c = str.charAt(i);
                if (c == '-') { // det går bara att skriva ett '-'
                    str = str.substring(0, i) + str.substring(i + 1, str.length());
                    break;
                }
            }
            ok = b.searchCustomer(Long.parseLong(str));

            if (ok) {
                setSearchListView(Long.parseLong(str)); // anropar metoden setSearchListView

            } else {
                statusLabel.setText("This customer doesn't exist in the system!");
            }

            if (str.length() != 12) { // större eller mindre än 12 // Anton
                throw new IndexOutOfBoundsException();
            }

        } catch (IndexOutOfBoundsException ex) {
            statusLabel.setText("Invalid social security No!");
        } catch (Exception ex) {
            statusLabel.setText("Invalid! You need to type your social \nsecurity No.the right way, YYYYMMDDXXXX");
        }

    }

    @FXML
    private void addCustomer(ActionEvent event) throws IOException { // lägger till kunder
        statusLabel.setText("");

        Stage stage;
        Parent root;

        stage = new Stage();
        root = FXMLLoader.load(getClass().getResource("FXMLpopUp1.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(addCustomerButton.getScene().getWindow());
        stage.showAndWait();

        boolean b1 = s.getB();
        if (b1) {
            try {
                String n = s.getN(); // namn
                String n2 = s.getN2(); // SSN
                n = n.trim();
                n2 = n2.trim();

                if (n.isEmpty()) {
                    statusLabel.setText("Please type a name!");
                    throw new NullPointerException();
                }

                int i1 = 0;
                int i2 = 0;
                for (int i = 0; i < n.length(); i++) {
                    if (n.charAt(i) == ' ') {
                        i1++;
                    } else if (n.charAt(i) == '-') {
                        i2++;
                    }
                }
                if (i1 > 2 || i2 > 1) { // man kan max ha två mellanslag i sitt namn och ett "-"
                    statusLabel.setText("Invalid name!");
                    throw new NullPointerException();
                }

                String s1 = n.replaceAll("[A-Za-z -]", "");
                if (!s1.isEmpty()) {
                    statusLabel.setText("Name can only contain letters!");
                    throw new NullPointerException();
                }

                int i3 = n2.length();
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
              
                LocalDate d = LocalDate.now();
                LocalDate dd = d.minusYears(18);
                String förUng = dd.toString();
                dd = d.minusYears(120);
                String förGammal = dd.toString();
               
                int fU = Integer.parseInt(förUng.replaceAll("-", "").trim());
                int fG = Integer.parseInt(förGammal.replaceAll("-", "").trim());
                String n3 = n2.substring(0, 8);
                n3 = n3.substring(0, 4) + "-" + n3.substring(4, 6) + "-" + n3.substring(6, n3.length());
                
                LocalDate date = LocalDate.parse(n3);
             
                n3 = n3.replaceAll("-", "");
                int kund = Integer.parseInt(n3);
                
                
                if(kund > fU){
                    statusLabel.setText("You must be 18 or older!");
                    throw new NullPointerException();          
                }else if(kund < fG){
                    statusLabel.setText("You are to old!");
                    throw new NullPointerException(); 
                }
                
      
                
                
                if (i3 != 12) {
                    statusLabel.setText("Please type full social security number!");
                    throw new NullPointerException();
                }

                boolean a = b.addCustomer(n, l);
                if (!a) {
                    statusLabel.setText("User already exists!");
                }

            } catch (NullPointerException ex) {
            } catch (NumberFormatException | DateTimeParseException ex) {
                statusLabel.setText("Invalid social security number!");
            }
        }
        s.setB(Boolean.FALSE);
        s.setL(null);
        s.setN(null);
        s.setN2(null);
        setListView();

    }

    @FXML
    private void customersList(ActionEvent event) throws IOException {

        customerDetailList.setText("hej");

    }

    @FXML
    private void deleteCustomer(ActionEvent event) throws IOException {
        try {

            String s1 = (String) customersList.getSelectionModel().getSelectedItem();
            if (s1.isEmpty()) {
                throw new NullPointerException();
            }

            Stage stage;
            Parent root;

            stage = new Stage();
            root = FXMLLoader.load(getClass().getResource("FXMLpopUp2.fxml"));
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(deleteCustomerButton.getScene().getWindow());
            stage.showAndWait();

            boolean b = s.getB();
            if (b) {
                s1 = s1.replaceAll("[A-Za-z-]", "").trim();
                long pNr = Long.parseLong(s1);
                //metod i bankLogic för att ta bort kund (skicka long pNr)
            }
            setListView();
            s.setB(Boolean.FALSE);
        } catch (NullPointerException ex) {
            statusLabel.setText("Select customer!");
        }

    }

    @FXML
    private void clearSearch(ActionEvent event) {

        ssnField.clear();
        setListView();
    }


    @FXML
    private void exportToFile(ActionEvent event) throws IOException {

        BufferedWriter writer = null;
        try {
            ArrayList<Customer> lista1 = b.getCustomerList();
            String userHomeFolder = System.getProperty("user.home");
            File textFile = new File(userHomeFolder, "customerpage.txt"); // lägger filen i hem mappen istället för i projektmappen
            writer = new BufferedWriter(new FileWriter(textFile));
            for (Customer c : lista1) {
                writer.write(c.toString() + "\n");

            }

        } catch (IOException e) {
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        b = BankLogic.getInstance();
        s = Singelton.getInstance();

        setListView(); // fyller lista med kunder

        customersList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String str = (String) customersList.getSelectionModel().getSelectedItem();
                customerDetailList.setText(str);
            }
        });

    }

    public void setSearchListView(long ssn) { //metod för att matcha det personnumret användaren matar in med personnummren som finns i listan 

        oList = FXCollections.observableArrayList();

        for (Customer c : (ArrayList<Customer>) b.getCustomerList()) {// loopar igenom lista med kunder

            if (ssn == c.getPnr()) {// om det inmatade personnummret finns i listan så körs resten av koden
                String s = Long.toString(c.getPnr());
                s = s.substring(0, 8) + "-" + s.substring(8, s.length());
                String s1 = c.getName() + " " + s;
                oList.add(s1);
            }

        }

        customersList.setItems(oList);
    }

    public void setListView() {  // metod för att lägga samtliga kunders namn i listView
        oList = FXCollections.observableArrayList();

        ArrayList<Customer> tC = b.getCustomerList();

        for (Customer c : tC) {
            String s = Long.toString(c.getPnr());
            s = s.substring(0, 8) + "-" + s.substring(8, s.length()); // lägger till ett "-" innan de sista fyra siffrorna!

            String s1 = c.getName() + " " + s;
            oList.add(s1);

        }

        customersList.setItems(oList);
    }

}
