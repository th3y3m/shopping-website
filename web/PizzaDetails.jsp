<%-- 
    Document   : PizzaDetails
    Created on : Mar 8, 2024, 12:52:39 AM
    Author     : admin
--%>

<%@page import="Models.DAO.SupplierDAO"%>
<%@page import="Models.DAO.CategoryDAO"%>
<%@page import="Models.DAO.ProductDAO"%>
<%@page import="Models.Entity.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <link rel="icon" type="image/x-icon" href="image/favicon.ico">

        <title>Pizza Details</title>
        <style>
            html {
                position: relative;
                min-height: 100%;
            }
            input[type='submit']{
                background-color: #2d63ce;
                color: white;
                border-radius: 5px;
                width: 40%;

            }
            body {
                margin: 0;
                margin-bottom: 250px;
                background-color: #e5edff
            }
        </style>
    </head>
    <body>
        <jsp:include page="NavigationBar.jsp"></jsp:include>
        <%
            Product p = (Product) request.getAttribute("productDetails");
            CategoryDAO categoryDao = new CategoryDAO();
            SupplierDAO supplierDao = new SupplierDAO();
        %>
        <div class="container">
            <h1>Pizza Details</h1>
            <div class="col-md-6">
                <img src="<%=p.getProductImage()%>" style="height: 100%; width: 100%">
            </div>
            <div class="col-md-6" style="font-size: 25px">
                <table width="600px">
                    <tbody>
                        <tr>
                            <td>Product ID:</td>
                            <td><%=p.getProductId()%></td>
                        </tr>
                        <tr>
                            <td>Product Name:</td>
                            <td><%=p.getProductName()%></td>
                        </tr>
                        <tr>
                            <td>Category:</td>
                            <td><%=categoryDao.getCategoryById(p.getCategoryId()).getCategoryName()%></td>
                        </tr>
                        <tr>
                            <td>Supplier:</td>
                            <td><%=supplierDao.getSupplierById(p.getSupplierId()).getCompanyName()%></td>
                        </tr>
                        <tr>
                            <td>Quantity per Unit:</td>
                            <td><%=p.getQuantityPerUnit()%></td>
                        </tr>
                        <tr>
                            <td>Unit Price:</td>
                            <td>$<%=p.getUnitPrice()%></td>
                        </tr>
                    </tbody>

                </table>
                <%        if (session.getAttribute("account") != null) {
                %>
                <form action="CartController?productId=<%=p.getProductId()%>&action=Add" method="post">
                    <input value="Add to Cart" type="submit">
                </form>
                <%
                    }
                %>

            </div>

        </div>
        <jsp:include page="Footer.jsp"></jsp:include>

        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    </body>
</html>
