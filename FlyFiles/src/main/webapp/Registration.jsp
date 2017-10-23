<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<html><body>
       <header> <link rel="stylesheet" href="Main.css"></header>
        
<html><body>
       <header> <link rel="stylesheet" href="Main.css"></header>
        
<div class="login-page">
  <div class="form">
    <!--<form class="register-form">
      <input type="text" placeholder="username"/>
      <input type="password" placeholder="password" />
      <input type="text" placeholder="email address"/>
      <button>create</button>
      <p class="message">Already have an account <a href="#">Sign In</a></p>
    </form>-->
      <form class="login-form" action="RegistrationServlet" method="post">
      <input type="text" placeholder="username"  name="username"  required/>
      <input type="text" placeholder="password" name="password"  required/>
      <button>Register</button>      
      
    </form>
  </div>
</div>
   <script type="text/javascript">
    function validateForm()
    {
        
        if (username==null || username=="",password==null ||password =="")
        {
            alert("All fields are mendatory");
            return false;
        }
    }
    
    </script>
    </body>
    </html>
    
