<%-- 
    Document   : Home
    Created on : Mar 7, 2024, 1:53:48 PM
    Author     : admin
--%>

<%@page import="Models.Entity.Product"%>
<%@page import="Models.DAO.ProductDAO"%>
<%@page import="Models.DAO.OrderDetailsDAO"%>
<%@page import="Models.Entity.OrderDetails"%>
<%@page import="java.util.List"%>
<%@page import="Models.Entity.Order"%>
<%@page import="Models.Entity.Account"%>
<%@page import="Models.DAO.CustomerDAO"%>
<%@page import="Models.Entity.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order Details</title>
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
            .orderDetails {
                margin: 20px auto;
                padding: 20px;
                border: 1px solid #ccc;
                border-radius: 5px;
                max-width: 800px;
            }

            h1 {
                border-bottom: 1px solid #ccc;
                width: 400px;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }

            th, td {
                padding: 10px;
                text-align: left;
            }

            th {
                background-color: #f2f2f2;
            }

            tr:nth-child(even) {
                background-color: #f9f9f9;
            }
        </style>
    </head>
    <body>
        <jsp:include page="NavigationBar.jsp"></jsp:include>
            <div class="container orderDetails">
            <%
                Customer c = null;
                if (request.getAttribute("customerSelected") != null) {
                    c = (Customer) request.getAttribute("customerSelected");
                } else {
                    c = (Customer) session.getAttribute("customer");
                }
                Order order = (Order) request.getAttribute("order");
                OrderDetailsDAO orderDetailsDao = new OrderDetailsDAO();
                ProductDAO productDao = new ProductDAO();
                List<OrderDetails> orderDetails = orderDetailsDao.getOrderDetailsByOrderId(order.getOrderId());

            %>
            <h1 style="border-bottom: 1px solid #ccc; width: 500px">Order Details</h1>
            <h3>Account ID: <%=c.getCustomerId()%></h3>
            <h3>Full Name: <%=c.getContactName()%></h3>
            <h3>Address: <%=c.getAddress()%></h3>
            <h3>Phone: <%=c.getPhone()%></h3>
            <div style="border-bottom: 1px solid #ccc; width: 400px"></div>
            <h3>Order ID: <%=order.getOrderId()%></h3>
            <h3>Address: <%=order.getShipAddress()%></h3>
            <h3>Order Date: <%=order.getOrderDate()%></h3>
            <h3>Required Date: <%=order.getRequiredDate()%></h3>
            <h3>Shipped Date: <%=order.getShippedDate()%></h3>
            <h3>Freight: $<%=order.getFreight()%></h3>
            <table border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Id</th>
                        <th>PizzaName</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>SubTotal</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        int count = 0, numberOfPizzas = 0;
                        double subTotal = 0, totalAmount = 0;
                        for (OrderDetails item : orderDetails) {
                            Product p = productDao.getProductByProductId(item.getProductId());
                            numberOfPizzas += item.getQuantity();
                            subTotal += item.getSubTotal();
                    %>
                    <tr>
                        <td><%=(++count)%></td>
                        <td><%=item.getProductId()%></td>
                        <td><%=p.getProductName()%></td>
                        <td>$<%=item.getUnitPrice()%></td>
                        <td><%=item.getQuantity()%></td>
                        <td>$<%=String.format("%.2f", item.getSubTotal())%></td>
                    </tr>
                    <%
                        }
                    %>
                    <tr>
                        <td colspan="5" style="text-align: right"><b>Sub Total</b></td>
                        <td>$<%=String.format("%.2f", subTotal)%></td>
                    </tr>
                    <%if (numberOfPizzas >= 3) {
                            totalAmount = subTotal * 9 / 10;
                    %>
                    <tr>
                        <td colspan="5" style="text-align: right"><b>Discount: </b></td>
                        <td>10%</td>
                    </tr>
                    <tr>
                        <td colspan="5" style="text-align: right"><b>Total Amount:</b></td>
                        <td>$<%=String.format("%.2f", totalAmount)%></td>
                    </tr>

                    <%
                        } else {
                            totalAmount = subTotal;
                        }
                    %>
                    <tr>
                        <td colspan="5" style="text-align: right"><b>Freight:</b></td>
                        <td>$5</td>
                    </tr>
                    <tr>
                        <td colspan="5" style="text-align: right"><b>Total: </b></td>
                        <%
                            totalAmount += 5;
                        %>
                        <td>$<%=String.format("%.2f", totalAmount)%></td>
                    </tr>
                </tbody>
            </table>

        </div>

        <jsp:include page="Footer.jsp"></jsp:include>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    </body>
</html>
