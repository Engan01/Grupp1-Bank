package banksystem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
    public void viewProfile(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("scene2.fxml"));
        Scene s1 = new Scene(root);
        Stage stg = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stg.setScene(s1);
        stg.show();

    }

    // search method
    @FXML
    public void search(ActionEvent event) throws IOException {
        String str = ssnField.getText();
        Boolean ok = b.searchCustomer(Long.parseLong(str));

        if (ok) {
            b.getCustomer(Long.parseLong(str));
        } else {
            statusLabel.setText("This customer doesn't exist in the system!");
        }
    }

    @FXML
    private void addCustomer(ActionEvent event) throws IOException { // lägger till kunder

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
            String n = s.getN();
            long l = s.getL();
            b.addCustomer(n, l);

            s.setB(Boolean.FALSE);
            s.setL(null);
            s.setN(null);
        }
        setListView();

    }

    @FXML
    private void deleteCustomer(ActionEvent event) throws IOException {
        
        String s1 = (String)customersList.getSelectionModel().getSelectedItem();

        Stage stage;
        Parent root;
        
        stage = new Stage();
        root = FXMLLoader.load(getClass().getResource("FXMLpopUp2.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(deleteCustomerButton.getScene().getWindow());
        stage.showAndWait();
        
        boolean b = s.getB();
        if(b){
            s1 = s1.replaceAll("[A-Za-z-]", "").trim();
            long pNr = Long.parseLong(s1);
            //metod i bankLogic för att ta bort kund (skicka long pNr)
        }
        setListView();
        s.setB(Boolean.FALSE);
        
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
