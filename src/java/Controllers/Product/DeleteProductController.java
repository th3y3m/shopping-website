/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Product;

import Models.DAO.OrderDAO;
import Models.DAO.OrderDetailsDAO;
import Models.DAO.ProductDAO;
import Models.Entity.OrderDetails;
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
@WebServlet(name = "DeleteProductController", urlPatterns = {"/DeleteProductController"})
public class DeleteProductController extends HttpServlet {

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
        String productId = request.getParameter("productId");

        try {
            if (productId != null) {
                ProductDAO productDao = new ProductDAO();
                OrderDetailsDAO orderDetailsDao = new OrderDetailsDAO();
                OrderDAO orderDao = new OrderDAO();

                List<OrderDetails> orderList = orderDetailsDao.getOrderDetailsByProductId(productId);

                for (OrderDetails orderDetails : orderList) {
                   orderDetailsDao.deleteOrderDetails(orderDetails.getOrderId());  
                }
                for (OrderDetails orderDetails : orderList) {
                    orderDao.deleteOrder(orderDetails.getOrderId());
                }
                if (productDao.deleteProduct(productId)) {
                    message = "The product ID " + productId + " has been deleted successfully";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.setAttribute("action", "Deleted Product");
            request.setAttribute("page", "ManageProduct.jsp");
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
