/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Order;

import Controllers.CartItem.CartUtil;
import Models.DAO.CustomerDAO;
import Models.DAO.OrderDAO;
import Models.DAO.OrderDetailsDAO;
import Models.Entity.Account;
import Models.Entity.CartItem;
import Models.Entity.Customer;
import Models.Entity.Order;
import Models.Entity.OrderDetails;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author admin
 */
@WebServlet(name = "AddOrderController", urlPatterns = {"/AddOrderController"})
public class AddOrderController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();

        CartUtil cartUtil = new CartUtil();
        Account a = (Account) session.getAttribute("account");

        cartUtil.deleteCartToCookie(request, response, a.getAccountId());
        OrderDAO orderDao = new OrderDAO();
        OrderDetailsDAO orderDetailsDao = new OrderDetailsDAO();
        HashMap<String, CartItem> cart = (HashMap<String, CartItem>) session.getAttribute("Cart");
        List<CartItem> itemsInCart = new ArrayList<CartItem>(cart.values());
        String dateTime = request.getParameter("dateTime"), orderId;
        int quantity = 0;
        Order order = null;
        
        try {
            quantity = itemsInCart.stream().map((cartItem) -> cartItem.getQuantity()).reduce(quantity, Integer::sum);
            Customer c = (Customer) session.getAttribute("customer");

            if (session.getAttribute("orderId") == null) {
                orderId = "O" + String.format("%03d", orderDao.getNextOrderId());
            } else{
                orderId = (String) session.getAttribute("orderId");
            }
             
            String customerId = c.getCustomerId();
            Timestamp orderDate = new Timestamp(System.currentTimeMillis());
            String formattedDateTime = dateTime.replace("T", " ") + ":00";
            Timestamp requiredDate = Timestamp.valueOf(formattedDateTime);
            Timestamp shippedDate = new Timestamp(requiredDate.getTime() - (15 * 60 * 1000));
            double freight = 5;
            String shipAddress = c.getAddress();
            order = new Order(orderId, customerId, orderDate, requiredDate, shippedDate, freight, shipAddress);

            if (orderDao.addOrder(order)) {
                for (CartItem cartItem : itemsInCart) {
                    orderDetailsDao.addOrderDetails(new OrderDetails(orderId, cartItem.getItemId(), cartItem.getUnitPrice(), cartItem.getQuantity()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session.getAttribute("Cart") != null) {
                session.removeAttribute("Cart");
            }
            if (session.getAttribute("orderId") != null) {
                session.removeAttribute("orderId");
            }
            request.setAttribute("order", order);
            request.getRequestDispatcher("OrderDetails.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
