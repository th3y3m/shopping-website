<%-- 
    Document   : Home
    Created on : Mar 5, 2024, 1:07:40 AM
    Author     : admin
--%>

<%@page import="Models.Entity.Customer"%>
<%@page import="Models.Entity.Account"%>
<%@page import="Models.DAO.CustomerDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Models.Entity.Category"%>
<%@page import="Models.DAO.CategoryDAO"%>
<%@page import="java.util.List"%>
<%@page import="Models.Entity.Product"%>
<%@page import="Models.DAO.ProductDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Menu Pizza</title>
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
            }

            .mainPart{
                background-color: #e5edff
            }
            img{
                width:  100%;
            }

            .row{
                padding-bottom: 30px
            }
            h2{
                float: right
            }
            input[type='submit']{
                background-color: #2d63ce;
                color: white;
                border-radius: 5px;
                width: 40%;
                height: 30px
            }
            .sizesearch{
                width: 380px;
            }

            .unit:hover{
                background-color: #cad1d5;
            }
            .unit a:hover{
                text-decoration: none
            }

            .unit a{
                color: black
            }
        </style>
    </head>

    <body class="mainPart">
        <jsp:include page="NavigationBar.jsp"></jsp:include>
            <div class="container">

            <%
                List<Product> productList = new ArrayList();
                ProductDAO productDao = new ProductDAO();
                String category = (String) session.getAttribute("categorySelected");
//                String lastMin = (String) request.getAttribute("lastMin");
//                String lastMax = (String) request.getAttribute("lastMax");
                if (request.getAttribute("productByPrice") != null) {
                    productList = (List<Product>) request.getAttribute("productByPrice");
                } else if (session.getAttribute("productList") != null) {
                    productList = (List<Product>) session.getAttribute("productList");
                } else {
                    productList = productDao.getProductList();
                }
            %>


            <h1>Pizza Menu</h1>
            <div class="container-fluid">
                <div class="header-search col-md-6">
                    <div class="form-group has-feedback has-search sizesearch">
                        <span class="glyphicon glyphicon-search form-control-feedback"></span>
                        <input type="text" class="form-control sizesearch" placeholder="Product Name" name="txtSearchValue" oninput="searchByName(this)">
                    </div>
                </div>
                <div class="header-search col-md-6">
                    <form action="ProductController?action=searchByPrice" class="form-horizontal" method="post">
                        <div class="form-group has-feedback has-search sizesearch">
                            <span class="glyphicon glyphicon-search form-control-feedback"></span>
                            <input type="text" class="form-control sizesearch" placeholder="Min Price" name="minPrice" <%--value="<%=lastMin == null ? "": lastMin%>"--%>>
                            <span class="glyphicon glyphicon-search form-control-feedback"></span>
                            <input type="text" class="form-control sizesearch" placeholder="Max Price" name="maxPrice" <%--value="<%=lastMax == null ? "": lastMax%>"--%>>
                            <button type="submit" class="btn btn-success">Search</button>
                        </div>
                    </form>
                </div>
            </div>

            <h3 style="padding:  50px ">All Pizza: <%=category == null ? "All category" : category%></h3>

            <div class="container-fluid" id="content">
                <%
                    CategoryDAO categoryDao = new CategoryDAO();
                    Category c;

                    for (int i = 0; i < productList.size(); i++) {
                        if (i % 3 == 0) {
                %>
                <div class="row">
                    <%
                        }
                        Product p = productList.get(i);
                        c = categoryDao.getCategoryById(p.getCategoryId());
                    %>
                    <div class="col-md-4 unit">
                        <a href="ProductController?action=view&action1=view&productId=<%=p.getProductId()%>">
                            <img src="<%=p.getProductImage()%>" class="img-fluid">
                            <h3><%=p.getProductName()%></h3>
                            <h2>$<%=p.getUnitPrice()%></h2>
                            <p>Category: <%=c.getCategoryName()%></p>
                            <p><%=c.getDescription()%></p>
                        </a>
                        <%
                            if (session.getAttribute("account") != null) {
                                CustomerDAO customerDao = new CustomerDAO();

                        %>
                        <form action="CartController?productId=<%=p.getProductId()%>&action=Add" method="post">
                            <input value="Add to Cart" type="submit">
                        </form>

                        <%
                        } else {
                        %>
                        <form action="Login.jsp" method="post">
                            <input value="Add to Cart" type="submit">
                        </form>
                        <%
                            }
                        %>


                    </div>
                    <%
                        if ((i + 1) % 3 == 0 || i == productList.size() - 1) {
                    %>
                </div>
                <%
                        }
                    }

                %>
            </div>

        </div>
        <jsp:include page="Footer.jsp"></jsp:include>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script>
            function searchByName(param) {
                var value = param.value;

                $.ajax({
                    url: "/Assignment_ShoppingWebsite/SearchProductController",
                    type: "get", //send it through get method
                    data: {
                        txtSearchValue: value
                    },
                    success: function (response) {
                        //Do Something
                        var content = document.getElementById("content");
                        content.innerHTML = response;
                    },
                    error: function (xhr) {
                    }
                });
            }

        </script>
    </body>
</html>
