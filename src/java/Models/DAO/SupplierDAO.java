/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.DAO;

import Models.Entity.Supplier;
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
public class SupplierDAO {

    public Connection getConnection() throws Exception {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection cnn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=PizzaStore", "sa", "12345");
            return cnn;
        } catch (ClassNotFoundException | SQLException e) {
            throw e;
        }
    }

    public List<Supplier> getSupplierList() throws Exception {
        List<Supplier> supplierList = new ArrayList();
        String supplierId, companyName, address, phone;
        PreparedStatement ps = null;
        Connection cnn = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM Suppliers";
            cnn = getConnection();
            ps = cnn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                supplierId = rs.getString(1);
                companyName = rs.getString(2);
                address = rs.getString(3);
                phone = rs.getString(4);
                Supplier supplier = new Supplier(supplierId, companyName, address, phone);
                supplierList.add(supplier);
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
        return supplierList;
    }

    public Supplier getSupplierById(String id) throws Exception {
        Supplier supplier = getSupplierList().stream().filter(b -> b.getSupplierId().trim().
                equals(id.trim())).findAny().orElse(null);
        return supplier;
    }
}
