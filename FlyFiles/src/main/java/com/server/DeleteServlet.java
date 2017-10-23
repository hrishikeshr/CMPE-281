/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.db.DeleteFileFromDatabase;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hrish
 */
public class DeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getSession(false).getAttribute("username").toString();
        System.out.println("<deleteservlet>username  " + username);
        String bucketname = "hrishikeshbucket1";
        // String key=username;
        String filename = request.getParameter("myObject");
        System.out.println("<deleteservlet>key_name  " + filename);
        String key;
        String SUFFIX = "/";
        key = username + SUFFIX + filename;
        System.out.println("key:  " + key);
        
        DeleteFileFromDatabase file = new DeleteFileFromDatabase();
      file.del(filename);
        

        try {
            AWSCredentials credentials = new BasicAWSCredentials(
                    "Access_key",
                    "Secret_key");

            AmazonS3 s3client = new AmazonS3Client(credentials);
            System.out.println("<deleteservlet> s3client   " + s3client.getS3AccountOwner().getDisplayName());
            s3client.setRegion(Region.getRegion(Regions.US_WEST_1));
            s3client.setEndpoint("s3.us-east-2.amazonaws.com");
            s3client.deleteObject(bucketname, key);
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means"
                    + " the client encountered "
                    + "an internal error while trying to "
                    + "communicate with S3, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
        
        RequestDispatcher dispatcher2 = request.getRequestDispatcher("MainMenu.jsp");
         dispatcher2.forward(request, response);

    }

}
