package banksystem;

// @author Anton
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBT {

    private static DBT instance;

    private Connection myConnection;
    private Statement myStatement;
    private ResultSet result;

    private String url = "jdbc:mysql://127.0.0.1:3306/RADER_test?user=Student&password=1234";

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
    
    public void addCustomer(String name, long pnr){
        try {

            PreparedStatement addCustomer = myConnection.prepareStatement("INSERT INTO Customer (name, pnr) VALUES (?, ?)");

            addCustomer.setString(1, name);
            addCustomer.setLong(2, pnr);
            
            addCustomer.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        
        
    }
    
    public void deleteCustomer(long pnr){
        
        
        
        
    }
    
    public void editName(long pnr){
        
        
        
    }
    
    public void newAccount(long pnr){
        
        
        
    }
    
    public void deleteAccount(int accountNr){
        
        
        
        
    }
    
    public void updateBalance(int accountNr, double newBalance){
        
        
        
        
    }
    
    public ArrayList<Customer> getCustomerList(){
        ArrayList<Customer> cus = new ArrayList<>();
        
        
        
        return cus;
    }
    
    
    public ArrayList<Account> getAccountList(){
        ArrayList<Account> accounts = new ArrayList<>();
        
        
        return accounts;
    } 
    
    public ArrayList<Transaction> getTransactions(){
        ArrayList<Transaction> trans = new ArrayList<>();
        
        
        return trans;
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
