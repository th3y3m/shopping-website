/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Order;

import Models.DAO.CustomerDAO;
import Models.DAO.OrderDAO;
import Models.DAO.OrderDetailsDAO;
import Models.DAO.ProductDAO;
import Models.Entity.CartItem;
import Models.Entity.Order;
import Models.Entity.OrderDetails;
import java.io.IOException;
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
@WebServlet(name = "ViewOrderDetailsController", urlPatterns = {"/ViewOrderDetailsController"})
public class ViewOrderDetailsController extends HttpServlet {

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

        String orderId = request.getParameter("orderId");
        HashMap<String, CartItem> cart = new HashMap<String, CartItem>();
        OrderDetailsDAO orderDetailsDao = new OrderDetailsDAO();
        OrderDAO orderDao = new OrderDAO();
        ProductDAO productDao = new ProductDAO();
        HttpSession session = request.getSession();
        CustomerDAO customerDao = new CustomerDAO();

        try {
            Order o = orderDao.getOrderByOrderId(orderId);

            List<OrderDetails> orderDetailsList = orderDetailsDao.getOrderDetailsByOrderId(orderId);
            for (OrderDetails oD : orderDetailsList) {
                cart.put(oD.getProductId(), new CartItem(oD.getProductId(), productDao.getProductNameById(oD.getProductId()), oD.getQuantity(), oD.getUnitPrice()));
            }
            session.setAttribute("Cart", cart);
            session.setAttribute("customer", customerDao.getCustomerById(o.getCustomerId()));
            session.setAttribute("orderId", orderId);
            
            orderDetailsDao.deleteOrderDetails(orderId);
            orderDao.deleteOrder(orderId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher("CartController?action=view").forward(request, response);
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
