/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.db;

import com.pojo.DatabaseFilePojo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hrish
 */
public class FileDetailsFromDatabase 
{
    
     public  ArrayList<DatabaseFilePojo> fetchData(String username)
    {
        System.out.println("check1");
       
        Connection connectionObject=null;
        PreparedStatement statementObject=null;
        ResultSet resultSetObject=null;
        
         ArrayList<DatabaseFilePojo> pojo=new ArrayList<DatabaseFilePojo>();
         DatabaseFilePojo pojoObject;
         
        String query= "select * from filedetails where username=?";

        
        DatabaseConnection databaseObject=new DatabaseConnection();
        connectionObject = databaseObject.makeConnection();
        
        System.out.println("check2");
        
        if(connectionObject!=null)
        {
            System.out.println("check3");
            try 
           {
               System.out.println("check4");
                statementObject=connectionObject.prepareStatement(query);
              
                statementObject.setString(1, username);
             
                
                
               resultSetObject = statementObject.executeQuery();
               System.out.println("check5"); 
                while(resultSetObject.next())
                {
                     System.out.println("check6 in filedetailsfromdatabase");
                    pojoObject=new DatabaseFilePojo();
                    String file_name=resultSetObject.getString("filename");
                    String file_upload_time=resultSetObject.getString("uploadtime");
                    String file_description=resultSetObject.getString("description");
                    String user_name=resultSetObject.getString("username");
                   
                    
                  
                    pojoObject.setFileName(file_name);
                    pojoObject.setFileDescription(file_description);
                    pojoObject.setFileUploadTime(file_upload_time);
                    pojoObject.setUserName(user_name);
                   
                    pojo.add(pojoObject);  
                    System.out.println("check7");
                }
                
             
                
                
           } 
            catch (SQLException ex) 
            {
              ex.printStackTrace();
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
                 
                          System.out.println("check8");
                } catch (SQLException ex) 
                {
                    Logger.getLogger(LoginDatabase.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        
        }
        return pojo;

    }
    
    
}
