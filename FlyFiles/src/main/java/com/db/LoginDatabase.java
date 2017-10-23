/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hrish
 */
public class LoginDatabase 
{

    
    public boolean login(String username,String password)
    {
        boolean flagForLogin=false ;
        Connection connectionObject=null;
        PreparedStatement statementObject=null;
        ResultSet resultSetObject=null;
        
        String query="select * from user where username=? and password=?";
        
        DatabaseConnection databaseObject=new DatabaseConnection();
        connectionObject = databaseObject.makeConnection();
        
        if(connectionObject!=null)
        {
            try 
           {
                statementObject=connectionObject.prepareStatement(query);
                System.out.println("Query is:"+query);
                statementObject = connectionObject.prepareStatement(query);
                statementObject.setString(1, username);
                statementObject.setString(2, password);
                resultSetObject = statementObject.executeQuery();
                
                if(resultSetObject.next())
                    flagForLogin=true; 
                else
                    flagForLogin=false;
                
                
                
                
           } 
            catch (SQLException ex) 
            {
                System.out.println("Exception is:"+ex);
          }
            finally
            {
               
                    try 
                    {
                         if(resultSetObject!=null)
                             resultSetObject.close();
                          if(statementObject!=null)
                              statementObject.close();
                          if(connectionObject!=null)
                              databaseObject.closeConnection(connectionObject);
                          
                } catch (SQLException ex) 
                {
                    Logger.getLogger(LoginDatabase.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        
        }
  
        return flagForLogin;
    }
}
