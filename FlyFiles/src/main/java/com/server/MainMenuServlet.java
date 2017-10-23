/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.db.FileDetailsToDatabase;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;

@MultipartConfig
public class MainMenuServlet extends HttpServlet {

    private static final String SUFFIX = "/";   
    private static boolean flagForNewFolder=true;
    String bucketName = "hrishikeshbucket1";
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        ArrayList<String> arrayListOfFile;
        arrayListOfFile = new ArrayList<>();
        
      String username= request.getSession(true).getAttribute("username").toString();
      
        System.out.println("<mainmenuservlet>username from session is:"+username);
      
      AWSCredentials credentials = new BasicAWSCredentials(
				"Access_key", 
				"Secret_key");
           
            AmazonS3 s3client = new AmazonS3Client(credentials);
            List bl = s3client.listBuckets();
            String bn = bl.get(0).toString();
            System.out.println("bn  "+bn);
            
            
            s3client.setRegion(Region.getRegion(Regions.US_WEST_1));
            s3client.setEndpoint("s3.us-east-2.amazonaws.com");
        
            System.out.println("Inside MainMenuServlet::::::::::::");

            
        List<Part> fileParts; // Retrieves <input type="file" name="file" multiple="true">
        
        
        fileParts = request.getParts().stream().filter(part -> "file".equals(part.getName())).collect(Collectors.toList());
for(Part p:fileParts){
    System.out.println("p: "+p.getName());
}
        
        ObjectListing listofFolders;
        listofFolders = s3client.listObjects(bucketName);
        List filed = listofFolders.getObjectSummaries();
        String d = filed.get(0).toString();
        System.out.println("<mainmenuservlet>d: "+d);

      
       List<S3ObjectSummary> summaries = listofFolders.getObjectSummaries();
       listofFolders = s3client.listNextBatchOfObjects (listofFolders);
       summaries.addAll (listofFolders.getObjectSummaries());
       
        for (S3ObjectSummary objectSummary : summaries) 
       {
	    String uniqueId = objectSummary.getKey();
            if(uniqueId.equals(username))
            {   
                flagForNewFolder=false;
                break;
                
            }
            else
            {
                System.out.println("check2");
                flagForNewFolder=true;
            }
       }  
       System.out.println("<mainmenuservlet> flagForNewFolder  "+flagForNewFolder);
       if(flagForNewFolder)
        createFolder(bucketName, username, s3client);
       
          for(Part filePart : fileParts) 
         { 
          String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
          System.out.println("fileName  "+fileName);
         
          InputStream fileContent = filePart.getInputStream();
                
          StringWriter writer = new StringWriter();
          IOUtils.copy(fileContent,writer,"UTF-8");
          String theFileContent= writer.toString();
        
          File file = new File(fileName);         
          file.createNewFile();

          FileWriter fwriter = new FileWriter(file);
          fwriter.write(theFileContent);
          fwriter.close();
          
          String completeFilePath = username + SUFFIX + fileName;
          System.out.println("completeFilePath  "+completeFilePath);
          
          s3client.putObject(new PutObjectRequest(bucketName,completeFilePath,file).withCannedAcl(CannedAccessControlList.PublicRead));
          
          arrayListOfFile.add(fileName);         
        } 
        
        FileDetailsToDatabase info=new FileDetailsToDatabase();
        info.addDataIntoDatabase(username,arrayListOfFile);
        
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
     
   
 public static void createFolder(String bucketName, String folderName, AmazonS3 client) {
     System.out.println("<mainmenuservlet> inside create folder method");

	ObjectMetadata metadata = new ObjectMetadata();
	metadata.setContentLength(0);
	
	InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
	
	PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
				folderName + SUFFIX, emptyContent, metadata);
        
        String rbn = putObjectRequest.getBucketName();
        System.out.println("rbn  "+rbn);
	
	PutObjectResult por = client.putObject(putObjectRequest);
        System.out.println("por  "+por.getETag());
        
 }
     

}
         
        
    
    
    


