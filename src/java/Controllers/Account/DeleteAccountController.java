/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Account;

import Models.DAO.AccountDAO;
import Models.DAO.CustomerDAO;
import Models.DAO.OrderDAO;
import Models.DAO.OrderDetailsDAO;
import Models.Entity.Order;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
@WebServlet(name = "DeleteAccountController", urlPatterns = {"/DeleteAccountController"})
public class DeleteAccountController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final String messagePage = "DisplayMessage.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String url = messagePage, message = "Something went wrong";
        String accountId = request.getParameter("customerId");

        try {
            if (accountId != null) {
                AccountDAO accountDao = new AccountDAO();
                CustomerDAO customerDao = new CustomerDAO();
                OrderDAO orderDao = new OrderDAO();
                OrderDetailsDAO orderDetailsDao = new OrderDetailsDAO();

                List<Order> orderList = orderDao.getOrderByCustomerId(accountId);
                for (Order order : orderList) {
                    orderDetailsDao.deleteOrderDetails(order.getOrderId());
                }
                for (Order order : orderList) {
                    orderDao.deleteOrder(order.getOrderId());
                }
                if (accountDao.deleteAccount(accountId) && customerDao.deleteCustomer(accountId)) {
                    message = "The account ID " + accountId + " has been deleted successfully";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.setAttribute("action", "Deleted Account");
            request.setAttribute("page", "ManageAccount.jsp");
            request.setAttribute("message", message);

            request.getRequestDispatcher(url).forward(request, response);
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
