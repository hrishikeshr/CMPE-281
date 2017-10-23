<!--
/* 
    Author     : hrish
*/ 
-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html><body>
       <header> <link rel="stylesheet" href="Main.css"></header>
        <style>
</style>
<div class="login-page">
  <div class="form">
      <center>
    <!--<form class="register-form">
        <br/><input type="text" placeholder="username"/>
        <br/><input type="password" placeholder="password" />
        <br/><button>Register</button>
     
      <p class="message">Already have an account <a href="#">Sign In</a></p>
       </center>
    </form>-->
          <center>
          <form class="login-form" action="LoginServlet" method="post">
              <br/><input type="text" placeholder="username"  name="username"  required/>
              <br/><input type="password" placeholder="password" name="password"  required/>
              <br/><button>login</button>      
      <p class="message">New user? <a href="Registration.jsp">open an account</a></p>
      </center>
    </form>
  </div>
</div>
   <script type="text/javascript">
    function validateForm()
    {
        
        if (username==null || username=="",password==null ||password =="")
        {
            alert("User name and password can not be empty");
            return false;
        }
    }
    
    </script>
    </body>
    </html>
    
