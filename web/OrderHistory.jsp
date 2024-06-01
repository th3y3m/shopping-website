<%@page import="java.sql.Timestamp"%>
<%@page import="Models.Entity.Account"%>
<%@page import="Models.Entity.Category"%>
<%@page import="Models.DAO.CategoryDAO"%>
<%@page import="Models.Entity.Order"%>
<%@page import="java.util.List"%>
<%@page import="Models.DAO.OrderDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Order</title>
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

            th {
                background-color: #f2f2f2;
                text-align: left;
                padding: 15px;
            }

            td {
                border-bottom: 1px solid #ddd;
                padding: 15px;
            }

            tr{
                background-color: white;
            }

            tr:hover {
                background-color: #ddd;
            }

            .action-links a {
                color: blue;
                text-decoration: none;
                margin-right: 5px;
            }

            .btn {
                margin-top: 10px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="NavigationBar.jsp"></jsp:include>
            <div class="container">
                <h1>Orders</h1>
                <table>
                    <thead>
                        <tr>
                            <th>Order ID</th>
                            <th>Customer ID</th>
                            <th>Order Date</th>
                            <th>Required Date</th>
                            <th>Shipped Date</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                    <%
                        OrderDAO orderDao = new OrderDAO();
                        Account a = (Account) session.getAttribute("account");
                        List<Order> orderList = orderDao.getOrderByCustomerId(a.getAccountId());
                        for (Order p : orderList) {
                    %>
                    <tr>
                        <td><%=p.getOrderId()%></td>
                        <td><%=p.getCustomerId()%></td>
                        <td><%=p.getOrderDate()%></td>
                        <td><%=p.getRequiredDate()%></td>
                        <td><%=p.getShippedDate()%></td>
                        <td>
                            <%
                                Timestamp current = new Timestamp(System.currentTimeMillis());
                                if (current.before(new Timestamp(p.getOrderDate().getTime() + (1 * 60 * 1000)))) {
                            %>
                            <b>Pending</b>
                            <%
                            } else if (current.before(p.getRequiredDate())) {
                            %>
                            <b>Processing</b>
                            <%
                            } else {
                            %>
                            <b>Delivered</b>
                            <%
                                }
                            %>
                        </td>
                        <td>
                            <%
                                if (current.before(new Timestamp(p.getOrderDate().getTime() + (1 * 60 * 1000)))) {
                            %>
                            <a href="OrderController?action=viewDetails&orderId=<%=p.getOrderId()%>">Edit</a>|
                            <a href="OrderController?action=delete&orderId=<%=p.getOrderId()%>">Delete</a>|
                            <%
                                }
                            %>
                            <a href="OrderController?action=view&orderId=<%=p.getOrderId()%>">Details</a>
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
