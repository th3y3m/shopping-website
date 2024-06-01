/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Product;

import Models.DAO.ProductDAO;
import Models.Entity.Product;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
@WebServlet(name = "AddProductController", urlPatterns = {"/AddProductController"})
public class AddProductController extends HttpServlet {

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

        String productId = null;
        String productName = request.getParameter("txtProductName");
        String supplierId = request.getParameter("txtSupplierId");
        String categoryId = request.getParameter("txtCategoryId");
        String quantityPerUnit = request.getParameter("txtQuantityPerUnit");
        String unitPrice = request.getParameter("txtUnitPrice");
        String productImage = request.getParameter("txtProductImage");
        String url = messagePage, message = "Something went wrong";

        try {
            if (productName != null && supplierId != null && categoryId != null && quantityPerUnit != null && unitPrice != null && productImage != null) {
                ProductDAO productDao = new ProductDAO();
                productId = "P" + String.format("%03d", productDao.getNextProductId());

                Product product = new Product(productId, productName, supplierId, categoryId, Integer.parseInt(quantityPerUnit), Double.parseDouble(unitPrice), productImage);
                if (product != null) {
                    if (productDao.addProduct(product)) {
                        message = "The product " + productId + " has been added successfully";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.setAttribute("action", "Create new Product");
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
