<%@page import="com.db.FileDetailsFromDatabase"%>
<%@page import="com.pojo.DatabaseFilePojo"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <body>
        <header><h1>Hello&nbsp;&nbsp;<%=session.getAttribute("username")%></h1>
            <%
                String s = session.getAttribute("username").toString();
                session.setAttribute("username", s);
            %>

            <h3><a href="Login.jsp">logout</a></h3>
        </header>
            
            <table width="59%" border="1">
        <tr><td>User</td><td>File name</td><td>Description</td><td>Date created</td><td>Download</td><td>Delete</td><td>Update</td></tr>
        <%

            String username = session.getAttribute("username").toString();

            System.out.println("<showmylistoffilesjsp>username  " + username);
            ArrayList<DatabaseFilePojo> pojo = new ArrayList<DatabaseFilePojo>();
            FileDetailsFromDatabase info = new FileDetailsFromDatabase();
            pojo = info.fetchData(username);

            for (int i = 0; i < pojo.size(); i++) {
                DatabaseFilePojo object = new DatabaseFilePojo();
                object = pojo.get(i);
                String fn = object.getFileName();
                System.out.println("object  " + object.getFileName());
                request.setAttribute("filename", fn);
        %>
        <tr>
            <td>
                <%= object.getUserName()%>
            </td>

            <td>
                <%= object.getFileName()%>
            </td>

            <td>
                <%= object.getFileDescription()%>
            </td>

            <td>
                <%= object.getFileUploadTime()%>
            </td>
            <!--<td><button>Download</button>-->
            <td>
                <form action="DownloadServlet" method="post">
                    <input type="submit" value="Download" >
                    <input type="hidden" name="myObject" value=<%= object.getFileName()%> /> 
                </form>
            </td>     
              <td>
                <form action="DeleteServlet" method="post">
                    <input type="submit" value="Delete" >
                    <input type="hidden" name="myObject" value=<%= object.getFileName()%> /> 
                </form>
            </td>
             <td>
                <form action="MainMenuServlet" method="post" enctype="multipart/form-data">
                    <input type="file" id="file2" name="file" multiple>
                    <input type="submit" value="Submit" id="btnSubmit"/>
                </form>
            </td>
        </tr>
        
        <%
            }
        %>
    </table>
            

        <form id="myform"  action="MainMenuServlet"  onsubmit="return myFunction();" method="post" enctype="multipart/form-data">

            <label for="file">Choose file to upload</label>
            <input type="file" id="file" name="file" multiple>
            <input type="submit" value="Submit" id="btnSubmit"/>
            <!--  <button>create</button><a href="MainMenu.jsp"></a> -->
        </form>
        <br> 
      <!--  <form id="FetchData" action="showMyListOfFiles.jsp" method="post">
            <input type="submit" value="View my files">
        </form> -->

        <script>
            function myFunction()
            {

                var fileUpload = document.getElementById('file');
                var flag = false;

                if (typeof (fileUpload.files) !== "undefined")
                {
                    for (var i = 0; i < fileUpload.files.length; i++)
                    {
                        var maxsize = 10 * 1024;

                        var size = fileUpload.files[i].size / 1024;

                        if (size > maxsize)
                        {
                            alert("please reload files size of some files less than 10 MB");
                            document.getElementById("myform").reset();
                            flag = false;


                        } else
                        {
                            flag = true;


                        }

                    }
                } else
                {

                    alert("This browser does not support HTML5.");
                }
                return flag;
            }


        </script>


    </body>

    <style>

        body {
            background: lightcyan; 
            background: -webkit-linear-gradient(right, lightcyan, lightcyan);
            background: -moz-linear-gradient(right, lightcyan, lightcyan);
            background: -o-linear-gradient(right, lightcyan, lightcyan);
            background: linear-gradient(to left, lightcyan, lightcyan);
            font-family: "times", sans-serif;
            -webkit-font-smoothing: antialiased;
            -moz-osx-font-smoothing: grayscale;      
        }
    </style>

</html>
