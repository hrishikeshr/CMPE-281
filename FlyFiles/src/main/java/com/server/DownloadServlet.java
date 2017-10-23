/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.sns.SNSImplementation;
import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.FileWriter;
import java.io.FileInputStream;
import org.apache.commons.io.IOUtils;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hrish
 */
public class DownloadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("in DS:::");
        String username = request.getSession(false).getAttribute("username").toString();
        System.out.println("username username  " + username);
        String bucketname = "hrishikeshbucket1";
        
        String filename = request.getParameter("myObject");
        System.out.println("key_name key_name  " + filename);
        String SUFFIX = "/";
        String key;
        String dir = "E:\\";
        key = username + SUFFIX + filename;
        System.out.println("key:  " + key);
        try {
            AWSCredentials credentials = new BasicAWSCredentials(
                    "Access_key",
                    "Secret_key");

            AmazonS3 s3client = new AmazonS3Client(credentials);
            System.out.println("<downloadservlet> s3client   " + s3client.getS3AccountOwner().getDisplayName());
            s3client.setRegion(Region.getRegion(Regions.US_WEST_1));
            s3client.setEndpoint("s3.us-east-2.amazonaws.com");
            S3Object s3object = s3client.getObject(new GetObjectRequest(
                    bucketname, username + SUFFIX + filename));
            System.out.println("<downloadservlet> s3object  " + s3object.getBucketName());

            InputStream objectData = s3object.getObjectContent();

            InputStreamReader Ip = new InputStreamReader(objectData);

            StringWriter writer = new StringWriter();
            IOUtils.copy(objectData, writer, "UTF-8");
            String theFileContent = writer.toString();

            String totalfileName = dir + filename;
            File file = new File(totalfileName);
            file.createNewFile();

            FileWriter fwriter = new FileWriter(file);
            fwriter.write(theFileContent);
            fwriter.close();
            ServletContext context = getServletContext();
            String mimeType = context.getMimeType(dir);

            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }

            FileInputStream inStream = new FileInputStream(file);
            response.setContentType(mimeType);
            response.setContentLength((int) file.length());

            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", file.getName());
            response.setHeader(headerKey, headerValue);

            OutputStream outStream = response.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            inStream.close();
            outStream.close();
            
            SNSImplementation implementation=new SNSImplementation();
            implementation.setNotificationMethod();
            
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means"
                    + " the client encountered "
                    + "an internal error while trying to "
                    + "communicate with S3, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }

    }

    private static void displayTextInputStream(InputStream input)
            throws IOException {
        // Read one text line at a time and display.
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }

            System.out.println("    " + line);
        }
        System.out.println();
    }
}
