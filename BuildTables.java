  
package mattsonHomeBuilderGUIApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class BuildTables {
    //final String DB_URL = "jdbc:derby:CoffeeDB;create=true";
    final String DB_URL = "jdbc:derby://localhost:1527/CoffeeDB;user=sa;password=sa;create=true";
    
    public BuildTables()
    {
        try
        {
            //try this code
            //create a connection to the database
            Connection conn = DriverManager.getConnection(DB_URL);
            
            dropTables(conn);
            
            buildHomeTable(conn);
          
            
            conn.commit();
            
            //close the connection
            conn.close();
           
        }catch(Exception ex)
        {
           //if it fails do this
            System.out.println("Error: " + ex.getMessage());
        
        }
    }
    
    public static void dropTables(Connection conn)
    {
        System.out.println("Checking for existing tables");
        
        try
        {
            //get a statement object 
            Statement stmt = conn.createStatement();
            
           
            try
            {
                stmt.execute("DROP TABLE HomeOrders");
                System.out.println("HomeOrders table dropped.");
            } catch(SQLException ex)
            {
                //No need to report anything here
                //The table did not exist
            }
            
        }catch(SQLException ex)
        {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public static void buildHomeTable(Connection conn)
    {
        try         // WATCH THE SYNTAX CLOSELY HERE, PROBABLY WILL NEED TO TRIPLE CHECK THESE
                    //  DOUBLE CHECK SYNTAX IN SQL SSMS
        {
            Statement stmt = conn.createStatement();
            
            stmt.execute("CREATE Table HomeOrders (" +      
                    "HomeType CHAR(25), " +
                    "HomeIDNum CHAR(10) NOT NULL PRIMARY KEY, " +
                    "CustFName CHAR(25), " +
                    "CustLName CHAR(25), " +
                    "PurchaseAmount DOUBLE " + 
                    ")");
            
            System.out.println("created HomeOrders table");
            //Insert row #1
            stmt.execute("INSERT INTO HomeOrders Values ( " +    
                         "'Custom', " + "'14001', " + "'Joey', " + "'Favioli', " + "150000)" ); // careful with syntax
            
            stmt.execute("INSERT INTO HomeOrders Values ( " +    
                         "'Luxury', " + "'14002', " + "'Bob', " + "'Hope', " + "2000000)" ); 
            
            stmt.execute("INSERT INTO HomeOrders Values ( " +    
                         "'Standard', " + "'14003', " + "'Shawna', " + "'Nyquist', " + "50000)" ); 
            
            stmt.execute("INSERT INTO HomeOrders Values ( " +    
                         "'Custom', " + "'14004', " + "'Fred', " + "'Flinstone', " + "250000)" ); 
            
            System.out.println("inserted HomeOrders data");  
        }catch(SQLException ex)
        {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
