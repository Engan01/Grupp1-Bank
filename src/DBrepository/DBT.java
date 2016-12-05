package DBrepository;

// @author Anton
import banksystem.Account;
import banksystem.BankLogic;
import banksystem.CreditAccount;
import banksystem.SavingsAccount;
import banksystem.Transaction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
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
    
    public void addTransaction(LocalDateTime date, double amount, boolean type, double balance, int accountNr){
        String dateTime = date.toString();
        dateTime = dateTime.replace("T", " ");
       
        try {

            PreparedStatement addTransaction = myConnection.prepareStatement("INSERT INTO Transaction (date, amount, type, balance, account_accountNr) VALUES (?, ?, ?, ?, ?)");

            addTransaction.setString(1, dateTime);
            addTransaction.setDouble(2, amount);
            addTransaction.setBoolean(3, type);
            addTransaction.setDouble(4, balance);
            addTransaction.setInt(5, accountNr);
            
            addTransaction.executeUpdate();

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
    
    public void newAccount(long pnr, int accountNr, double balance, boolean type){
        
            try {

            PreparedStatement addCustomer = myConnection.prepareStatement("INSERT INTO account (accountNr, balance, customer_pnr, sav_cred) VALUES (?, ?, ?, ?)");

            addCustomer.setInt(1, accountNr);
            addCustomer.setDouble(2, balance);
            addCustomer.setLong(3, pnr);
            addCustomer.setBoolean(4, type);
            
            addCustomer.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }
    
    public void deleteAccount(int accountNr){
        
          try {

            PreparedStatement deleteTrans = myConnection.prepareStatement("DELETE FROM transaction WHERE account_accountNr=?");
            deleteTrans.setInt(1, accountNr);
            deleteTrans.executeUpdate();
            
            PreparedStatement deleteAcc = myConnection.prepareStatement("DELETE FROM account WHERE accountNr=?");
            deleteAcc.setInt(1, accountNr);
            deleteAcc.executeUpdate();
            

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        
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
            ex.printStackTrace();
        }
        
       
    }
    
    
    public ArrayList<Account> getAccountList(long pnr){
        ArrayList<Account> arr = new ArrayList<>();
        try {

            result = myStatement.executeQuery("SELECT accountNr, balance, sav_cred FROM Account WHERE customer_pnr='" + pnr + "'");
            while (result.next()) {
                if(result.getBoolean(3)){
                    arr.add(new SavingsAccount(result.getInt(1), result.getDouble(2)));
                }else{
                    arr.add(new CreditAccount(result.getInt(1), result.getDouble(2)));
                }
            }

        } catch (SQLException ex) {
            
        }
        return arr;
    } 
    
    public ArrayList<Transaction> getTransactions(int accountNr){
        ArrayList<Transaction> trans = new ArrayList<>();
        try {

            result = myStatement.executeQuery("SELECT date, amount, type, balance FROM Transaction WHERE account_accountNr='" + accountNr + "'");
            
            while (result.next()) {

                String d = result.getString(1);
                d = d.replace(" ", "T");
                LocalDateTime dateTime = LocalDateTime.parse(d);
                
                trans.add(new Transaction(dateTime, result.getDouble(2), result.getBoolean(3), result.getDouble(4), accountNr));
                
            }

        } catch (SQLException ex) {
            
        }        
        
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
