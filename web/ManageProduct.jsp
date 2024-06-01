<%-- 
    Document   : ManageProduct
    Created on : Mar 8, 2024, 1:58:14 AM
    Author     : admin
--%>

<%@page import="Models.Entity.Category"%>
<%@page import="Models.DAO.CategoryDAO"%>
<%@page import="Models.Entity.Product"%>
<%@page import="java.util.List"%>
<%@page import="Models.DAO.ProductDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Product</title>
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
                <h1>Pizzas</h1>
                <form action="CreatePizza.jsp" method="post">
                    <input type="submit" class="btn btn-success" value="Create New">
                </form>
                <table>
                    <thead>
                        <tr>
                            <th>Product ID</th>
                            <th>Product Name</th>
                            <th>Price</th>
                            <th>Description</th>
                            <th>Image URL</th>
                            <th>Category</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                    <%
                        ProductDAO productDao = new ProductDAO();
                        List<Product> productList = productDao.getProductList();
                        CategoryDAO categoryDao = new CategoryDAO();
                        for (Product p : productList) {
                            Category category = categoryDao.getCategoryById(p.getCategoryId());
                    %>
                    <tr>
                        <td><%=p.getProductId()%></td>
                        <td><%=p.getProductName()%></td>
                        <td>$<%=p.getUnitPrice()%></td>
                        <td><%=category.getDescription()%></td>
                        <td><%=p.getProductImage()%></td>
                        <td><%=category.getCategoryName()%></td>
                        <td>
                            <a href="ProductController?action=view&action1=update&productId=<%=p.getProductId()%>">Edit</a>|
                            <a href="ProductController?action=view&action1=view&productId=<%=p.getProductId()%>">Details</a>|
                            <a href="ProductController?action=delete&productId=<%=p.getProductId()%>">Delete</a>
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
