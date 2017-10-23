/* 
    Author     : hrish
*/

package com.db;

import java.sql.*;  
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection 
{
    
    public Connection makeConnection()
    {
       Connection con=null;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");  
            con=DriverManager.getConnection("jdbc:mysql://clouddb.cthp8cwzssmk.us-west-1.rds.amazonaws.com:3306/cloud","cloud","cloudproject");
            //DriverManager.getConnection("jdbc:mysql://mydbinstance.cthp8cwzssmk.us-west-1.rds.amazonaws.com:3306/filemanagement","hrishikesh","rendalkar");
           
            if(con!=null)
                System.out.println("connection successful");
            else
                System.out.println("connection failure");          
            
         
        }
        catch(ClassNotFoundException | SQLException e  )
        {
            System.out.println("Issue is"+e);
        }
     return con;   
    }
    public void closeConnection(Connection con)
    {
        if(con!=null)
        try 
            {
                con.close();
        } catch (SQLException ex) 
        {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
 
    
}
