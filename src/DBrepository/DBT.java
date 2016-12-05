package DBrepository;

// @author Anton
import banksystem.Account;
import banksystem.BankLogic;
import banksystem.Transaction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBT {

    private static DBT instance;
    
    private BankLogic b = BankLogic.getInstance();

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
    
    public void editName(String name, long pnr){
                try {

            PreparedStatement editName = myConnection.prepareStatement("UPDATE customer SET name =? WHERE pnr = ?");
            
            editName.setString(1, name);
            editName.setLong(2, pnr);
            editName.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        
    }
    
    public void newAccount(long pnr){
        
        
        
    }
    
    public void deleteAccount(int accountNr){
        
        
        
        
    }
    
    public void updateBalance(int accountNr, double newBalance){
        
          try {

            PreparedStatement updateBalance = myConnection.prepareStatement("UPDATE account SET balance =? WHERE accountNr = ?");
            
            updateBalance.setDouble(1, newBalance);
            updateBalance.setInt(2, accountNr);
            
            updateBalance.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        
        
    }
    
    public void getCustomerList(){
        
        try {

            result = myStatement.executeQuery("SELECT name, pnr FROM Customer");
            while (result.next()) {
                b.addCustomer(result.getString(1), result.getLong(2));
            }

        } catch (SQLException ex) {
            
        }
        
       
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
