/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Product;

import Models.DAO.CategoryDAO;
import Models.DAO.ProductDAO;
import Models.Entity.Category;
import Models.Entity.Product;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
@WebServlet(name = "SearchProductController", urlPatterns = {"/SearchProductController"})
public class SearchProductController extends HttpServlet {

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

        String searchValue = request.getParameter("txtSearchValue");
        PrintWriter out = response.getWriter();
        List<Product> result = new ArrayList();
        CategoryDAO categoryDao = new CategoryDAO();
        HttpSession session = request.getSession();

        try {
            ProductDAO productDao = new ProductDAO();

            if (searchValue == null) {
                result = (List<Product>) session.getAttribute("productList");
            } else {
                result = productDao.getProductListByCaterory(searchValue, (List<Product>) session.getAttribute("productList"));
            }
//            if (searchValue == null) {
//                result = (List<Product>) session.getAttribute("productList");
//            } else {
//                result = productDao.getProductListByCaterory(searchValue, (List<Product>) session.getAttribute("productList"));
//            }
            for (int i = 0; i < result.size(); i++) {
                Category category = categoryDao.getCategoryById(result.get(i).getCategoryId());
                if (i % 3 == 0) {
                    out.println("<div class=\"row\">");
                }
                out.println("<div class=\"col-md-4 unit\">");
                out.println("<a href=\"ProductController?action=view&action1=view&productId=" + result.get(i).getProductId() + "\">");
                out.println("<img src=\"" + result.get(i).getProductImage() + "\" alt=\"" + result.get(i).getProductName() + "\" class=\"img-fluid\">"
                        + "<h3>" + result.get(i).getProductName() + "</h3>\n"
                        + "<h2>$"
                        + result.get(i).getUnitPrice()
                        + "</h2>\n"
                        + "<p>\n"
                        + "Category: " + category.getCategoryName()
                        + "</p>\n"
                        + "\n"
                        + "<p>\n"
                        + category.getDescription()
                        + "</p>\n");
                out.println("</a>");
                out.println("<form action=\"CartController?productId=" + result.get(i).getProductId() + "&action=Add\" method=\"post\">\n"
                        + "                            <input value=\"Add to Cart\" type=\"submit\">\n"
                        + "                        </form>");

                out.println("</div>");
                if (i % 3 == 2 || i == result.size() - 1) {
                    out.println("</div>");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            out.close();
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
