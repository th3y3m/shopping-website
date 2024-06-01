<%-- 
    Document   : ManageCustomer
    Created on : Mar 8, 2024, 1:58:14 AM
    Author     : admin
--%>

<%@page import="Models.Entity.Category"%>
<%@page import="Models.DAO.CategoryDAO"%>
<%@page import="Models.Entity.Customer"%>
<%@page import="java.util.List"%>
<%@page import="Models.DAO.CustomerDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Customer</title>
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
            table {
                width: 100%;
                border-collapse: collapse;
            }

            /* Style table header */
            th {
                background-color: #f2f2f2;
                text-align: left;
                padding: 15px;
            }

            /* Style table rows */
            td {
                border-bottom: 1px solid #ddd;
                padding: 15px;
            }

            /* Alternate row background color */
            tr{
                background-color: white;
            }

            /* Hover effect on rows */
            tr:hover {
                background-color: #ddd;
            }

            /* Style the action links */
            .action-links a {
                color: blue;
                text-decoration: none;
                margin-right: 5px;
            }

            /* Add margin to buttons */
            .btn {
                margin-top: 10px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="NavigationBar.jsp"></jsp:include>
            <div class="container">
                <h1>Customers</h1>
                <table>
                    <thead>
                        <tr>
                            <th>Customer ID</th>
                            <th>Password</th>
                            <th>Customer Name</th>
                            <th>Address</th>
                            <th>Phone</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                    <%
                        CustomerDAO customerDao = new CustomerDAO();
                        List<Customer> customerList = customerDao.getCustomerList();
                        for (Customer p : customerList) {
                    %>
                    <tr>
                        <td><%=p.getCustomerId()%></td>
                        <td><%=p.getPassword()%></td>
                        <td><%=p.getContactName()%></td>
                        <td><%=p.getAddress() != null ? p.getAddress() : ""%></td>
                        <td><%=p.getPhone() != null ? p.getPhone() : "" %></td>
                        <td>
                            <a href="AccountController?action=view&action1=update&customerId=<%=p.getCustomerId()%>">Edit</a> |
                            <a href="AccountController?action=delete&customerId=<%=p.getCustomerId()%>">Delete</a>
                        </td>
                    </tr>
                    <%
                        }
                    %>

                </tbody>
            </table>
        </div>

        <jsp:include page="Footer.jsp"></jsp:include>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    </body>
</html>
