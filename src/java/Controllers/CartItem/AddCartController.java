/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.CartItem;

import Models.DAO.ProductDAO;
import Models.Entity.CartItem;
import Models.Entity.Product;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
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
@WebServlet(name = "AddCartController", urlPatterns = {"/AddCartController"})
public class AddCartController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final String menuPage = "Menu.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = null;
        String message, productId;
        Product selectedProduct = null;
        CartItem item = null;
        HashMap<String, CartItem> itemsInCart = null;
        try {
            /* TODO output your page here. You may use following sample code. */
            ProductDAO productDao = new ProductDAO();
            HttpSession session = request.getSession();
            itemsInCart = (HashMap<String, CartItem>) session.getAttribute("Cart");
            productId = request.getParameter("productId");
            selectedProduct = productDao.getProductByProductId(productId);
            if (itemsInCart == null) {
                itemsInCart = new HashMap<String, CartItem>();
                session.setAttribute("Cart", itemsInCart);
            }
            item = itemsInCart.get(selectedProduct.getProductId());
            if (item == null) {
                item = new CartItem(selectedProduct.getProductId(), selectedProduct.getProductName(), 1, selectedProduct.getUnitPrice());
                itemsInCart.put(item.getItemId(), item);
                message = "The pizza " + item.getItemName() + " has been added to cart";
            } else {
                item.setQuantity(item.getQuantity() + 1);
                message = "The cart has been updated successfully";
            }
            url = menuPage;
            request.setAttribute("Message", "<h4>" + message + "</h4>");
        } catch (Exception e) {
            log("AddCartController has error: " + e.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
