package banksystem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author asanilssonenglund
 */
public class FXMLDocumentController implements Initializable {
    
    private BankLogic b;
    
    private ObservableList<String> oList;
    
    @FXML
    private TextField ssnField;
    
    @FXML
    private Label statusLabel, viewProfileLabel, mainStatus;
    
    @FXML
    private ListView customersList;
    
    @FXML
    private Button addCustomerButton, deleteCustomerButton;
    
    @FXML
    private TextArea customerDetailList;
    
    @FXML
    public void viewProfile(ActionEvent event) throws IOException {
        try {
            String customer = (String) customersList.getSelectionModel().getSelectedItem();
            if (customer.isEmpty()) {
                throw new NullPointerException();
            }
            b.setpNr(Long.parseLong(customer.replaceAll("[A-Za-z -]", "").trim()));
            
            Parent root = FXMLLoader.load(getClass().getResource("scene2.fxml"));
            Scene s1 = new Scene(root);
            Stage stg = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stg.setScene(s1);
            stg.show();
        } catch (NullPointerException ex) {
            
            viewProfileLabel.setTextFill(Color.RED);
            viewProfileLabel.setText("Select customer!");
            
        }
    }
    
    // search method
    @FXML
    public void search(ActionEvent event) throws IOException {
        statusLabel.setText("");
        
        String str;
        Boolean ok;
        try {
            str = ssnField.getText();
            str = str.trim();
            for (int i = 0; i < str.length(); i++) { // nu går det även att skriva yyyymmdd-xxxx samt om man råkar få in ett mellanslag efter eller före // Anton
                char c = str.charAt(i);
                if (c == '-') { // det går bara att skriva ett '-'
                    str = str.substring(0, 8) + str.substring(9, str.length());
                    break;
                }
            }
            ok = b.searchCustomer(Long.parseLong(str)); // skickar personnr till metod i BankLogic
            
            if (ok) {
                setSearchListView(Long.parseLong(str)); // anropar metoden setSearchListView
                
            } else {
                statusLabel.setText("This customer doesn't exist in the system!");
            }
            
            if (str.length() != 12) { // större eller mindre än 12 // Anton
                throw new IndexOutOfBoundsException();
            }
            
        } catch (IndexOutOfBoundsException ex) {
            statusLabel.setText("Invalid social security number!");
        } catch (NumberFormatException ex) {
            statusLabel.setText("Invalid! You need to type your social \nsecurity No. the right way, YYYYMMDDXXXX");
        }
        
    }
    
    @FXML
    private void addCustomer() throws IOException { // läger till kunder i popUp1
        
        statusLabel.setText(""); 
        Stage stage;
        Parent root;
        
        stage = new Stage();
        stage.setTitle("Adding new customer");
        stage.setResizable(false);
        root = FXMLLoader.load(getClass().getResource("FXMLpopUp1.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(addCustomerButton.getScene().getWindow());
        stage.setOnCloseRequest((WindowEvent we) -> {
            stage.close();
        });
        stage.showAndWait();
        setListView();    
    }
    
    @FXML
    private void deleteCustomer() throws IOException {
        statusLabel.setText("");
        try {
            String s1 = (String) customersList.getSelectionModel().getSelectedItem();
            if (s1.isEmpty()) {
                throw new NullPointerException();
            }
            b.setpNr(Long.parseLong(s1.replaceAll("[A-Za-z-]", "").trim()));
            
            Stage stage;
            Parent root;
            
            stage = new Stage();
            stage.setTitle("Delete selected customer");
            stage.setResizable(false);
            root = FXMLLoader.load(getClass().getResource("FXMLpopUp2.fxml"));
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(deleteCustomerButton.getScene().getWindow());
            stage.setOnCloseRequest((WindowEvent we) -> {
                stage.close();
            });
            stage.showAndWait();
            setListView(); // listan uppdateras
            
        } catch (NullPointerException ex) {
            statusLabel.setText("Select customer!");
        }
    }
    
    @FXML
    private void clearSearch()  {
        statusLabel.setText("");
        if (!ssnField.getText().isEmpty())
            ssnField.clear();
        setListView();
    }
    
    @FXML
    private void exportToFile() throws IOException {
        mainStatus.setTextFill(Color.BLACK);
        statusLabel.setText("");
        
        BufferedWriter writer = null;
        try {
            String[] lista1 = b.getCustomers();
            if(lista1.length==0){
                mainStatus.setTextFill(Color.RED);
                mainStatus.setText("There is no customers to export!");
                throw new NullPointerException();
            }
            String userHomeFolder = System.getProperty("user.home");
            File textFile = new File(userHomeFolder, "customerpage.txt"); // lägger filen i hem mappen istället för i projektmappen
            
            writer = new BufferedWriter(new FileWriter(textFile));
            for (int i = 0; i < lista1.length; i++) {
                writer.write(lista1[i] + "\n");
            }
            mainStatus.setText("CustomerList successfully exported to file");
            
        }catch(NullPointerException e){
        } catch (IOException e) {
            mainStatus.setTextFill(Color.RED);
            mainStatus.setText("Transfer file is not accessible!");
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
        
        oList = FXCollections.observableArrayList();
        customersList.setItems(oList);
        
        setListView(); // fyller lista med kunder
        
        // listener
        customersList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    String str = (String) customersList.getSelectionModel().getSelectedItem();
                    
                    if (!str.isEmpty()) {
                        setCustomerDetails(str);
                        customersList.getSelectionModel().setSelectionMode(null);
                    }
                } catch (NullPointerException e) {
                } // behöver inte hantera detta exceptionet
            }
        });
    }
    
    private void setSearchListView(long ssn) { //metod för att matcha det personnumret användaren matar in med personnummren som finns i listan
        
        oList.clear();
        
        for (Customer c : (ArrayList<Customer>) b.getCustomerList()) {// loopar igenom lista med kunder
            
            if (ssn == c.getPnr()) {// om det inmatade personnummret finns i listan så körs resten av koden
                String ss = Long.toString(c.getPnr());
                ss = ss.substring(0, 8) + "-" + ss.substring(8, ss.length());
                String s1 = c.getName() + " " + ss;
                oList.add(s1);
            }
        }
    }
    
    private void setListView() {  // metod för att lägga samtliga kunders namn i listView
        oList.clear();
        ArrayList<Customer> tC = b.getCustomerList();
        for (Customer c : tC) {
            String ss = Long.toString(c.getPnr());
            ss = ss.substring(0, 8) + "-" + ss.substring(8, ss.length()); // lägger till ett "-" innan de sista fyra siffrorna!
            
            String s1 = c.getName() + " " + ss;
            oList.add(s1);
        }
    }
    
    private void setCustomerDetails(String str) {
        long pNr = Long.parseLong(str.replaceAll("[^0-9]", ""));
        String[] lista = b.getCustomer(pNr);
        
        String utskrift = "";
        for(int i = 0; i < lista.length; i++){
            utskrift  += lista[i] + "\n";
        }
        customerDetailList.setText(utskrift);
    }
}
