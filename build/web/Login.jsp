<%-- 
    Document   : Login
    Created on : Mar 3, 2024, 8:54:04 PM
    Author     : admin
--%>

<%@page import="Models.Entity.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Log In</title>
        <link rel="icon" type="image/x-icon" href="image/favicon.ico">

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">


        <style>
            html {
                position: relative;
                min-height: 100%;
            }

            body {
                margin: 0;
                margin-bottom: 250px;
                background-color: #e5edff
            }

            .inputBox {
                border: 1px solid #ccc;
                background-color: white
            }

            .row {
                display: flex;
                justify-content: center;
                align-items: center
            }

            .loginElement {
                margin: 0px;
                padding: 0px
            }

        </style>
    </head>
    <body>
        <jsp:include page="NavigationBar.jsp"></jsp:include>
        <%
            Account a = null;
            String username = null, password = null;
            if (session.getAttribute("accountSignUp") != null) {
                a = (Account) session.getAttribute("accountSignUp");
                username = a.getUserName();
                password = a.getPassword();
            }
        %>
        <div class="container inputBox">    
            <div class="row">
                <div class="col-md-6 loginElement">
                    <img src="image/Welcome.jpg" style="height: 90%; width: 90%">
                </div>

                <div id="loginbox" style="margin-right: 20px;" class="mainbox col-md-6 loginElement col-md-offset-3 col-sm-8 col-sm-offset-2">                    
                    <div class="panel panel-info" >
                        <div class="panel-heading">
                            <div class="panel-title">Sign In</div>
                        </div>     

                        <div style="padding-top:30px" class="panel-body" >

                            <div style="display:none" id="login-alert" class="alert alert-danger col-sm-12"></div>

                            <form action="LoginController" id="loginform" class="form-horizontal" role="form" method="post">

                                <div style="margin-bottom: 25px" class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                    <input id="login-username" type="text" class="form-control" name="txtUsername" value="<%=username != null ? username : ""%>" placeholder="username">                                        
                                </div>

                                <div style="margin-bottom: 25px" class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                                    <input id="login-password" type="password" class="form-control" name="txtPassword" placeholder="password" value="<%=password != null ? password : ""%>">
                                </div>
                                <div style="margin-top:10px" class="form-group">
                                    <!-- Button -->

                                    <div class="col-sm-12 controls">
                                        <button type="submit" id="btn-login" class="btn btn-success">Login</button>

                                    </div>
                                </div>


                                <div class="form-group">
                                    <div class="col-md-12 control">
                                        <div style="border-top: 1px solid#888; padding-top:15px; font-size:85%" >
                                            Don't have an account! 
                                            <a href="SignUp.jsp">
                                                Sign Up Here
                                            </a>
                                        </div>
                                    </div>
                                </div>    
                            </form>     
                        </div>                     
                    </div>  
                </div>

            </div>

        </div>



        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <jsp:include page="Footer.jsp"></jsp:include>

    </body>
</html>
