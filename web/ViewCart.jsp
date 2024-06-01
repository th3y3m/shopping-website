<%-- 
    Document   : ViewCart
    Created on : Mar 7, 2024, 11:53:41 PM
    Author     : admin
--%>

<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="Models.Entity.Customer"%>
<%@page import="Models.DAO.CustomerDAO"%>
<%@page import="Models.Entity.Account"%>
<%@page import="Models.Entity.CartItem"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <link rel="icon" type="image/x-icon" href="image/favicon.ico">

        <title>Cart</title>
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
            input[type=number]{
                background-color: #e5edff;
                border: none
            }
            tr td{
                padding: 5px
            }
            .order{
                text-align: center;
                font-size: 30px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="NavigationBar.jsp"></jsp:include>
            <div class="container">
                <h1>Your Cart</h1>
            <%
                double totalAmount = 0, subTotal = 0;
                int numberOfPizzas = 0;
                List<CartItem> itemsInCart = (List<CartItem>) request.getAttribute("Cart");
            %>
            <%
                if (itemsInCart == null || itemsInCart.size() == 0) {
            %>
            <h3>Cart is empty!!!</h3>
            <%
            } else if (itemsInCart != null) {
            %>
            <table border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Id</th>
                        <th>PizzaName</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>SubTotal</th>
                        <th colspan="2">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        int count = 0;
                        for (CartItem item : itemsInCart) {
                            subTotal += item.getSubTotal();
                            numberOfPizzas += item.getQuantity();
                    %>
                <form action="CartController" method="post">
                    <tr>
                        <td><%=(++count)%></td>
                        <td><%=item.getItemId()%>
                            <input type="hidden" value="<%=item.getItemId()%>" name="ItemId">
                        </td>
                        <td><%=item.getItemName()%></td>
                        <td>$<%=item.getUnitPrice()%></td>
                        <td>
                            <input type="number" min="1" name="quantity" value="<%=item.getQuantity()%>">
                        </td>
                        <td>$<%=String.format("%.2f", item.getSubTotal())%></td>
                        <td style="text-align: center">
                            <input type="submit" value="remove" name="action">
                        </td>
                        <td style="text-align: center">
                            <input type="submit" value="update" name="action">
                        </td>
                    </tr>
                </form>
                <%
                    }
                %>
                <tr>
                    <td colspan="5" style="text-align: right"><b>Sub Total</b></td>
                    <td>$<%=String.format("%.2f", subTotal)%></td>
                </tr>
                <%if (numberOfPizzas >= 3) {
                %>
                <tr>
                    <td colspan="5" style="text-align: right"><b>Discount: </b></td>
                    <td>10%</td>
                </tr>
                <tr>
                    <td colspan="5" style="text-align: right"><b>Total Amount:</b></td>
                    <%
                        totalAmount = subTotal * 9 / 10;
                    %>
                    <td>$<%=String.format("%.2f", totalAmount)%></td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>

            <h3>Number of pizzas in cart: <%=numberOfPizzas%></h3>
            <%

                }
            %>
            <form method="post">
                <input type="submit" formaction="CartController?action=save" value="Save Cart">
            </form>
            <%
                if (request.getAttribute("Message") != null) {
            %>
            <%=request.getAttribute("Message")%>
            <%}%>
            <%
                if (numberOfPizzas > 0) {
                    Customer c = null;
                    
                    if (session.getAttribute("customer") == null) {
                        c = (Customer) request.getAttribute("customer");
                    } else {
                        c = (Customer) session.getAttribute("customer");
                    }

                    if ((c.getAddress() != null) && (c.getPhone() != null)) {
            %>
            <div class="order">
                <form action="OrderController?action=add" method="post">
                    Required Date:
                    <%
                        LocalDateTime now = LocalDateTime.now();
                        LocalDateTime oneHourFromNow = now.plusHours(1);
                        LocalDateTime oneHour5FromNow = now.plusHours(1).plusMinutes(5);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                        String formattedDate = oneHourFromNow.format(formatter);
                        String formattedDateMin = oneHour5FromNow.format(formatter);
                    %>
                    <input type="datetime-local" name="dateTime" value="<%=formattedDateMin%>" min="<%= formattedDate%>"><br/>
                    <br/><input type="submit" value="Place Order" class="btn btn-success">
                </form>
            </div>
            <%
            } else {
            %>
            <div class="order">
                <form action="PersonalProfile.jsp" method="post">
                    <input type="submit" value="Place Order" class="btn btn-success">
                </form>
            </div>
            <%
                    }
                }

            %>
        </div>
        <jsp:include page="Footer.jsp"></jsp:include>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    </body>
</html>
