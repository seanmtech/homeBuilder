/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mattsonHomeBuilderGUIApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
  
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class CRUD {
    final String DB_URL = "jdbc:derby://localhost:1527/CoffeeDB;user=sa;password=sa;create=true";
    String SQLString;
    Connection conn;
    /* PREPARED STATEMENT - is an object for sending SQL statements to the database.
       These statements are precompiled.
       It is a special statement derived from the more general class, Statement.
       If you want to execute a Statement object many times, 
       it usually reduces execution time to use a PreparedStatement object instead.*/
    public PreparedStatement pstmt; 
    public Statement stmt; 
    public ResultSet rs;
    MyGUIForm gui;
    
    public CRUD(MyGUIForm gui)
    {
        System.out.println("In constructor");
        try {
          conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (this.conn != null)
        {
           System.out.println("***Connected To Database***");
        }
        this.gui = gui;      //assign gui object 
    }
   
    public void showTable()
    {
        ArrayList<Home> homes = getHomes();
        
        DefaultTableModel model = (DefaultTableModel) gui.getJTable().getModel();   //
        String[] titles = new String[]{"Home Type", "Home ID", "Cust First Name", "Cust Last Name", "Purchase Price"}; 
        Object[][] myHomeArray = new Object[homes.size()][5];        //num rows, num columns on coffee table
        
        for(int i = 0; i < homes.size(); i++)
        {
            myHomeArray[i][0] = homes.get(i).getHomeType();    //get item from arraylist
            
            System.out.println(homes.get(i).getHomeType()); // these might just be test code?
            System.out.println(homes.get(i).getHomeIDNumber()); // these might just be test code?
            
            myHomeArray[i][1] = homes.get(i).getHomeIDNumber();
            myHomeArray[i][2] = homes.get(i).getCustFirstName();
            
            myHomeArray[i][3] = homes.get(i).getCustLastName();
            myHomeArray[i][4] = homes.get(i).getHomePrice();
        }
        
        model.setDataVector(myHomeArray, titles);
 
    }
            
    public void showRecord(Home obj)
    {
        ArrayList<Home> homes = selectSpecificRecord(obj);
        
        DefaultTableModel model = (DefaultTableModel) gui.getJTable().getModel();   //
        String[] titles = new String[]{"Home Type", "Home ID", "Cust First Name", "Cust Last Name", "Purchase Price"}; 
        Object[][] myHomeArray = new Object[homes.size()][5];      //num rows, num columns on coffee table
        
        for(int i = 0; i < homes.size(); i++)
        {
            myHomeArray[i][0] = homes.get(i).getHomeType();    //get item from arraylist
            
            System.out.println(homes.get(i).getHomeType()); // these might just be test code?
            System.out.println(homes.get(i).getHomeIDNumber()); // these might just be test code?
            
            myHomeArray[i][1] = homes.get(i).getHomeIDNumber();
            myHomeArray[i][2] = homes.get(i).getCustFirstName();
            
            myHomeArray[i][3] = homes.get(i).getCustLastName();
            myHomeArray[i][4] = homes.get(i).getHomePrice();
        }
        
        model.setDataVector(myHomeArray, titles);
 
    }
    
    
    
    public ArrayList<Home> getHomes()
    {
        ArrayList<Home> homes = new ArrayList<Home>();
        String homeQuery = "SELECT * FROM HomeOrders";
        
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(homeQuery);
            Home home;
            
            while(rs.next())
            {
                home = new Home();       
                //set array elements to values from table
                
                home.setHomeType(rs.getString("HomeType"));
                home.setHomeIDNumber(rs.getString("HomeIDNum"));
                home.setCustFirstName(rs.getString("CustFName"));
                home.setCustLastName(rs.getString("CustLName"));
                home.setHomePrice(rs.getDouble("PurchaseAmount")); 
                
                homes.add(home);           //add a Home object to array list
                
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
                
        return homes;                      //return an array list of homes.
    }
         
    
    public void updateDb(Home obj)
    {
        String sql = "UPDATE HomeOrders SET HomeType = ?, PurchaseAmount = ?, CustFName = ?, CustLName = ?, HomeIDNum = ?"
                + " WHERE HomeIDNum = ?";
                            // I think this SQL syntax is right
                            
        // need to add more to sql string since my table has more columns
 
        try{ 
            
            pstmt = conn.prepareStatement(sql);
            // set the corresponding param
            pstmt.setString(1, obj.homeType);
            pstmt.setDouble(2, obj.homePrice);
            pstmt.setString(3, obj.custFirstName);
            pstmt.setString(4, obj.custLastName);
            pstmt.setString(5, obj.homeIDNumber); 
            pstmt.setString(6, obj.homeIDNumber); 
            // execute the update statement
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    

    public ArrayList<Home> selectSpecificRecord(Home hom)
    {
        ArrayList<Home> homes = new ArrayList<Home>();
        String sql = "SELECT * FROM HomeOrders WHERE HomeIDNum = ?"; 
        try
        {
         
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, hom.homeIDNumber); 
         
         ResultSet result = pstmt.executeQuery(); 
       
         if (result.next()) {
      
              //set array elements to values from database
              
                hom.setHomeType(result.getString("HomeType"));
                hom.setHomeIDNumber(result.getString("HomeIDNum"));
                hom.setCustFirstName(result.getString("CustFName"));
                hom.setCustLastName(result.getString("CustLName"));
                hom.setHomePrice(result.getDouble("PurchaseAmount")); 
                
                homes.add(hom);  //add a Home object to array list
        }
  
       }catch (SQLException e) {
           System.out.println("There was a SQL error during SELECT; " + e);
       }
        
       try
       {
            pstmt.close();
       }
       catch (SQLException e)
       {
           System.out.println("Error closing SQL select statement " + e);
       }
       return homes;
    }
    
    public void deleteDb(Home obj)
    {
        String sql = "DELETE FROM HomeOrders WHERE HomeIDNum = ?";
 
        try{
            
            pstmt = conn.prepareStatement(sql);
 
            // set the corresponding param
            pstmt.setString(1, obj.homeIDNumber);
            // execute the delete statement
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    
    }
    
    public void insertDb(Home obj)
    {
        System.out.println("About to insert into table");
    
        System.out.println(obj.homeType);
        System.out.println(obj.homePrice);
        System.out.println(obj.homeIDNumber);
        System.out.println(obj.custFirstName);
        System.out.println(obj.custLastName);
        
        try
        {
     
            SQLString = "INSERT INTO HomeOrders (HomeType, HomeIDNum, CustFName, CustLName, PurchaseAmount) Values"
                       + "(?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(SQLString);
            pstmt.setString(1, obj.homeType);
            pstmt.setString(2, obj.homeIDNumber);
            pstmt.setString(3, obj.custFirstName);
            pstmt.setString(4, obj.custLastName);
            pstmt.setDouble(5, obj.homePrice);
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close(); 
    
         }catch(SQLException ex)
        {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error: " + ex.getMessage());
        }

    }
}
