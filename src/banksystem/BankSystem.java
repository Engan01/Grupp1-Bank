package banksystem;

import DBrepository.DBT;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author asanilssonenglund
 */

public class BankSystem extends Application {
    
    private DBT dbt = DBT.getInstance();
    

    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
        stage.setTitle("RADER Banking System");
        
        stage.getFullScreenExitHint();
        stage.setResizable(false);
        stage.setOnCloseRequest((WindowEvent we) -> {
        dbt.closeConn();    // när jag stänger programmet kallar jag på metoden för att stänga alla connections till databasen
        stage.close();
        });
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {   
        launch(args);
        
    }
    
}
