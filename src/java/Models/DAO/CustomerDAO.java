/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.DAO;

import Models.Entity.Account;
import Models.Entity.Customer;
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
public class CustomerDAO {

    public Connection getConnection() throws Exception {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection cnn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=PizzaStore", "sa", "12345");
            return cnn;
        } catch (ClassNotFoundException | SQLException e) {
            throw e;
        }
    }

    public Customer login(String customerId, String password) throws Exception {
        Customer customer = null;
        Connection cnn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        String contactName, address, phone;

        try {
            cnn = getConnection();
            String sql = "select contactName, address, phone from Customers where [customerId] = ? and [password] = ?";
            preStm = cnn.prepareStatement(sql);
            preStm.setString(1, customerId);
            preStm.setString(2, password);
            rs = preStm.executeQuery();
            while (rs.next()) {
                contactName = rs.getString(1);
                address = rs.getString(2);
                phone = rs.getString(3);
                customer = new Customer(customerId, password, contactName, address, phone);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (preStm != null) {
                preStm.close();
            }
            if (cnn != null) {
                cnn.close();
            }
        }
        return customer;
    }

    public Customer getCustomerById(Account a) throws Exception {
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Customer customer = null;
        boolean type;
        String contactName, password, address, phone;
        try {
            cnn = getConnection();
            String sql = "Select password, contactName, address, phone from Customers where customerId = ?";
            ps = cnn.prepareStatement(sql);
            ps.setString(1, a.getAccountId());
            rs = ps.executeQuery();
            while (rs.next()) {
                password = rs.getString(1);
                contactName = rs.getString(2);
                address = rs.getString(3);
                phone = rs.getString(4);
                customer = new Customer(a.getAccountId(), password, contactName, address, phone);

            }
        } catch (Exception e) {
            throw e;
        }
        return customer;
    }

    public Customer getCustomerById(String id) throws Exception {
        Customer customer = getCustomerList().stream().filter(b -> b.getCustomerId().trim().
                equals(id.trim())).findAny().orElse(null);
        return customer;
    }

    public boolean addCustomer(Customer customer) throws Exception {
        Connection cnn = null;
        PreparedStatement ps = null;
        try {
            cnn = getConnection();
            String sql = "Insert Customers values (?, ?, ?, ?, ?)";
            ps = cnn.prepareStatement(sql);
            ps.setString(1, customer.getCustomerId());
            ps.setString(2, customer.getPassword());
            ps.setString(3, customer.getContactName());
            ps.setString(4, customer.getAddress());
            ps.setString(5, customer.getPhone());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw e;
        } finally {
            if (cnn != null) {
                cnn.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    public boolean deleteCustomer(String customerId) throws Exception {
        Connection cnn = null;
        PreparedStatement ps = null;
        try {
            cnn = getConnection();
            String sql = "Delete Customers where customerId = ?";
            ps = cnn.prepareStatement(sql);
            ps.setString(1, customerId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw e;
        } finally {
            if (cnn != null) {
                cnn.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    public boolean updateCustomer(Customer customer) throws Exception {
        Connection cnn = null;
        PreparedStatement ps = null;
        try {
            cnn = getConnection();
            String sql = "UPDATE Customers set password = ?, contactName = ?, address = ?, phone = ? where customerId = ?";
            ps = cnn.prepareStatement(sql);

            ps.setString(1, customer.getPassword());
            ps.setString(2, customer.getContactName());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getPhone());
            ps.setString(5, customer.getCustomerId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw e;
        } finally {
            if (cnn != null) {
                cnn.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    public List<Customer> getCustomerList() throws Exception {
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Customer> customerList = new ArrayList<>();
        Customer customer = null;
        String customerId = null, contactName, password, address, phone;

        try {
            cnn = getConnection();
            String sql = "Select * from Customers";
            ps = cnn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                customerId = rs.getString(1);
                password = rs.getString(2);
                contactName = rs.getString(3);
                address = rs.getString(4);
                phone = rs.getString(5);
                customer = new Customer(customerId, password, contactName, address, phone);
                customerList.add(customer);
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
        return customerList;
    }
}
