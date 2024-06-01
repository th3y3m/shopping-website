/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Account;

import Models.DAO.AccountDAO;
import Models.DAO.CustomerDAO;
import Models.Entity.Account;
import Models.Entity.Customer;
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
@WebServlet(name = "AddAccountController", urlPatterns = {"/AddAccountController"})
public class AddAccountController extends HttpServlet {

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
    private final String loginPage = "Login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String url = messagePage, message = "UserName has been existed. Please try again", accountId;
        String userName = request.getParameter("txtUserName");
        String password = request.getParameter("txtPassword");
        String fullName = request.getParameter("txtFullName");
        String type = request.getParameter("chkIsAdmin");
        HttpSession session = request.getSession();
        Account account = null;
        AccountDAO accountDao = new AccountDAO();
        try {
            if (userName != null && password != null && fullName != null) {
                accountId = "C" + String.format("%03d", accountDao.getNextAccountId());
                account = new Account(accountId, userName, password, fullName, (type != null));

                if (accountDao.addAccount(account)) {
                    session.setAttribute("accountSignUp", account);
                    if (type == null) {

                        CustomerDAO customerDao = new CustomerDAO();
                        customerDao.addCustomer(new Customer(accountId, password, fullName));
                    }
                    message = "The account username " + userName + " has been created successfully";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.setAttribute("action", "Create new Account");
            request.setAttribute("page", loginPage);
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
