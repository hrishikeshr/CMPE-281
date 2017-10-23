/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server;

import com.db.RegistrationDatabase;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author hrish
 */
public class RegistrationServlet extends HttpServlet 
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    { 
        String username;
        String password;
        boolean flagForRegistration=false;
        String loginPage="Login.jsp";
        
        HttpSession ss=request.getSession(true);
        
        
        username= request.getParameter("username").toString();
        password=request.getParameter("password").toString();
        
        RegistrationDatabase databaseObject=new RegistrationDatabase();
        databaseObject.register(username, password);
        
        if(flagForRegistration)
         ss.setAttribute("registerFlag","Registration successful go to log in page");
        else
        ss.setAttribute("registerFlag","Registration failed username already present,please try again with diffrent username");
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
        dispatcher.forward(request, response);
       
       
         
    }

}
