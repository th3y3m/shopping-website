<%-- 
    Document   : PersonalProfile
    Created on : Mar 9, 2024, 2:30:07 PM
    Author     : admin
--%>

<%@page import="Models.DAO.CustomerDAO"%>
<%@page import="Models.Entity.Account"%>
<%@page import="Models.Entity.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Personal Profile</title>
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
                background-color: #e5edff;
                padding-bottom: 50px;
            }

            .profile {
                max-width: 800px;
                margin: 0 auto; 
            }

        </style>
    </head>
    <body>
        <jsp:include page="NavigationBar.jsp"></jsp:include>
            <div class="container profile">

            <%
                Account a = (Account) session.getAttribute("account");
                CustomerDAO customerDao = new CustomerDAO();
                Customer c = customerDao.getCustomerById(a);

            %>
            <h1 style="border-bottom: 1px solid #ccc; width: 400px">Personal Profile<span class="glyphicon glyphicon-user"></span></h1>
            <h3>Account ID: <%=a.getAccountId()%></h3>
            <h3>Full Name: <%=a.getFullName()%></h3>
            <%
                if (c != null) {
            %>
            <form action="CustomerController?action=update" method="post">
                <h3>Address: <input type="text" name="txtAddress" value="<%=c.getAddress() != null ? c.getAddress():"" %>"></h3>
                <h3>Phone: <input type="number" name="txtPhone" minlength="9" maxlength="10" value="<%=c.getPhone() != null ? c.getPhone() : ""%>"></h3>

                <div class="form-group">
                    <div class="col-md-offset-3 col-md-9">
                        <button id="btn-signup" type="submit" class="btn btn-info">Save</button>
                    </div>
                </div>
            </form>
            <%
                }
                String message = (String) request.getAttribute("message");
                if (message != null) {
            %>
            <h3><%=message%></h3>
            <%
                }
            %>
        </div>
        <jsp:include page="Footer.jsp"></jsp:include>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    </body>
</html>
