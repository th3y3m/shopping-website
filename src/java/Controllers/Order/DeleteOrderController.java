/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Order;

import Models.DAO.OrderDAO;
import Models.DAO.OrderDetailsDAO;
import Models.Entity.Account;
import java.io.IOException;
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
@WebServlet(name = "DeleteOrderController", urlPatterns = {"/DeleteOrderController"})
public class DeleteOrderController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final String displayMessage = "DisplayMessage.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String orderId = request.getParameter("orderId");
        String message = "Something went wrong", url = displayMessage;
        HttpSession session = request.getSession();
        Account a = (Account) session.getAttribute("account");
        try {
            if (orderId != null) {
                OrderDAO orderDao = new OrderDAO();
                OrderDetailsDAO orderDetailsDao = new OrderDetailsDAO();
                if (orderDetailsDao.deleteOrderDetails(orderId)) {
                    if (orderDao.deleteOrder(orderId)) {
                        message = "The order " + orderId + " has been deleted successfully";

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (a.isType()) {
                request.setAttribute("page", "ManageOrder.jsp");

            } else {
                request.setAttribute("page", "OrderHistory.jsp");

            }
            request.setAttribute("action", "Deleted Order");
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
