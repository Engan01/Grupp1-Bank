package banksystem;

// @author Anton
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBT {

    private static DBT instance;

    private Connection myConnection;
    private Statement myStatement;
    private ResultSet result;

    private String url = "jdbc:mysql://127.0.0.1:3306/RADER?user=Student&password=1234";

    private DBT() {

        try {
            myConnection = (Connection) DriverManager.getConnection(url);
            myStatement = myConnection.createStatement();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static DBT getInstance() {
        if (instance == null) {
            instance = new DBT();
        }
        return instance;
    }

    public void closeConn() { // metod som körs när programet stängs för att stoppa alla connections till databasen
        

        try {
            if(myConnection != null)
                myConnection.close();
        } catch (SQLException ex) {
        }

        try {
            if(myStatement != null)
                myStatement.close();
        } catch (SQLException ex) {
        }

        try {
            if(result != null)
                result.close();
        } catch (SQLException ex) {
        }
    }

}
