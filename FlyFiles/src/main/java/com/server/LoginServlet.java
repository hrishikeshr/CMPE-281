package com.server;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.db.LoginDatabase;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



public class LoginServlet extends HttpServlet 
{

  
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)        
    {
        
        System.out.println("In login servlet");
        String username=null;
        String password=null;
        boolean flagForLogin=false;
        String mainMenuPage="MainMenu.jsp";
        String loginPage="Login.jsp";
        String message="invalid username/password";
       
       username= request.getParameter("username").toString();

       password=request.getParameter("password").toString();
         
        System.out.println("username"+username);
         System.out.println("username"+password);
        
       
         
        LoginDatabase loginObject=new LoginDatabase();
        flagForLogin = loginObject.login(username, password);
        
  
        if(flagForLogin)
        {
            System.out.println("login successful:::::");
            HttpSession session = request.getSession(true);
          session.setAttribute("username", username);
          try 
        {
            RequestDispatcher dispatcher = request.getRequestDispatcher("MainMenu.jsp");
           dispatcher.forward(request, response);
        } 
        catch (ServletException | IOException ex) 
        {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        else
        {
            System.out.println("unsuccessful login::::");
         
          RequestDispatcher dispatcher2 = request.getRequestDispatcher(loginPage);
          request.setAttribute("invalid username/password",message);
          
            try {
                dispatcher2.forward(request, response);
            } catch (ServletException ex) {
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }   
     
        
    }
    @Override
public void doGet(HttpServletRequest request, HttpServletResponse response)
{
    System.out.println("Inside doGet");
}
}
