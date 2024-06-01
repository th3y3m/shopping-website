/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.DAO;

import Models.Entity.Category;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class CategoryDAO {

    public Connection getConnection() throws Exception {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection cnn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=PizzaStore", "sa", "12345");
            return cnn;
        } catch (ClassNotFoundException | SQLException e) {
            throw e;
        }
    }

    public List<Category> getCategoryList() throws Exception {
        List<Category> categoryList = new ArrayList();
        String categoryId, categoryName, description;
        PreparedStatement ps = null;
        Connection cnn = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM Categories";
            cnn = getConnection();
            ps = cnn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                categoryId = rs.getString(1);
                categoryName = rs.getString(2);
                description = rs.getString(3);
                Category category = new Category(categoryId, categoryName, description);
                categoryList.add(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cnn != null) {
                cnn.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return categoryList;
    }

    public Category getCategoryById(String id) throws Exception {
        Category category = getCategoryList().stream().filter(b -> b.getCategoryId().trim().
                equals(id.trim())).findAny().orElse(null);
        return category;
    }
}
