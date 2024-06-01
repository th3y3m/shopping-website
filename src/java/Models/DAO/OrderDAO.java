/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.DAO;

import Models.Entity.Order;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class OrderDAO {

    public Connection getConnection() throws Exception {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection cnn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=PizzaStore", "sa", "12345");
            return cnn;
        } catch (ClassNotFoundException | SQLException e) {
            throw e;
        }
    }

    public Order getOrderByOrderId(String orderId) throws Exception {
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Order order = null;
        String customerId, shipAddress;
        Timestamp orderTimestamp, requiredTimestamp, shippedTimestamp;
        double freight;

        try {
            cnn = getConnection();
            String sql = "Select customerId, orderDate, requiredDate, shippedDate, freight, shipAddress from Orders where orderId = ?";
            ps = cnn.prepareStatement(sql);
            ps.setString(1, orderId);
            rs = ps.executeQuery();
            while (rs.next()) {
                customerId = rs.getString(1);
                orderTimestamp = rs.getTimestamp(2);
                requiredTimestamp = rs.getTimestamp(3);
                shippedTimestamp = rs.getTimestamp(4);
                freight = rs.getDouble(5);
                shipAddress = rs.getString(6);
                order = new Order(orderId, customerId, orderTimestamp, requiredTimestamp, shippedTimestamp, freight, shipAddress);

            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (cnn != null) {
                cnn.close();
            }
        }
        return order;
    }

    public List<Order> getOrderByCustomerId(String customerId) throws Exception {
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Order> orderList = new ArrayList<>();
        Order order = null;
        String orderId, shipAddress;
        Timestamp orderTimestamp, requiredTimestamp, shippedTimestamp;
        double freight;

        try {
            cnn = getConnection();
            String sql = "Select OrderId, orderDate, requiredDate, shippedDate, freight, shipAddress from Orders where customerId = ?";
            ps = cnn.prepareStatement(sql);
            ps.setString(1, customerId);
            rs = ps.executeQuery();
            while (rs.next()) {
                orderId = rs.getString(1);
                orderTimestamp = rs.getTimestamp(2);
                requiredTimestamp = rs.getTimestamp(3);
                shippedTimestamp = rs.getTimestamp(4);
                freight = rs.getDouble(5);
                shipAddress = rs.getString(6);
                order = new Order(orderId, customerId, orderTimestamp, requiredTimestamp, shippedTimestamp, freight, shipAddress);
                orderList.add(order);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (cnn != null) {
                cnn.close();
            }
        }
        return orderList;
    }

    public boolean addOrder(Order order) throws Exception {
        Connection cnn = null;
        PreparedStatement ps = null;

        try {
            cnn = getConnection();
            String sql = "Insert Orders values (?, ?, ?, ?, ?, ?, ?)";
            ps = cnn.prepareStatement(sql);
            ps.setString(1, order.getOrderId());
            ps.setString(2, order.getCustomerId());
            ps.setTimestamp(3, order.getOrderDate());
            ps.setTimestamp(4, order.getRequiredDate());
            ps.setTimestamp(5, order.getShippedDate());
            ps.setDouble(6, order.getFreight());
            ps.setString(7, order.getShipAddress());
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

    public boolean deleteOrder(String orderId) throws Exception {
        Connection cnn = null;
        PreparedStatement ps = null;
        try {
            cnn = getConnection();
            String sql = "Delete Orders where orderId = ?";
            ps = cnn.prepareStatement(sql);
            ps.setString(1, orderId);
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

    public boolean updateOrder(Order order) throws Exception {
        Connection cnn = null;
        PreparedStatement ps = null;
        try {
            cnn = getConnection();
            String sql = "UPDATE Orders set customerId = ?, orderDate = ?, requiredDate = ?, shippedDate = ?, freight = ?, shipAddress = ? where orderId = ?";
            ps = cnn.prepareStatement(sql);

            ps.setString(1, order.getCustomerId());
            ps.setTimestamp(2, order.getOrderDate());
            ps.setTimestamp(3, order.getRequiredDate());
            ps.setTimestamp(4, order.getShippedDate());
            ps.setDouble(5, order.getFreight());
            ps.setString(6, order.getShipAddress());
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

    public List<Order> getOrderList() throws SQLException {
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Order> orderList = new ArrayList<>();
        Order order = null;
        String customerId, shipAddress, orderId;
        Timestamp orderTimestamp, requiredTimestamp, shippedTimestamp;
        double freight;

        try {
            cnn = getConnection();
            String sql = "Select * from Orders";
            ps = cnn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                orderId = rs.getString(1);
                customerId = rs.getString(2);
                orderTimestamp = rs.getTimestamp(3);
                requiredTimestamp = rs.getTimestamp(4);
                shippedTimestamp = rs.getTimestamp(5);
                freight = rs.getDouble(6);
                shipAddress = rs.getString(7);
                order = new Order(orderId, customerId, orderTimestamp, requiredTimestamp, shippedTimestamp, freight, shipAddress);
                orderList.add(order);
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
        return orderList;
    }

    public List<Order> searchByDate(Date t1, Date t2) throws SQLException {
        LocalDate localDate = t2.toLocalDate();

        LocalDate t3LocalDate = localDate.plusDays(1);

        Date t3 = Date.valueOf(t3LocalDate);
        List<Order> orderList = new ArrayList<>();
        List<Order> orderFullList = getOrderList();
        orderFullList.stream().filter((o) -> (o.getOrderDate().after(t1) && o.getOrderDate().before(t3))).forEachOrdered((o) -> {
            orderList.add(o);
        });

        return orderList;
    }

    public List<Order> searchByDateSql(Date t1, Date t2) throws SQLException {
        LocalDate localDate = t2.toLocalDate();

        LocalDate t3LocalDate = localDate.plusDays(1);

        Date t3 = Date.valueOf(t3LocalDate);
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Order> orderList = new ArrayList<>();
        Order order = null;
        String orderId, shipAddress, customerId;
        Timestamp orderTimestamp, requiredTimestamp, shippedTimestamp;
        double freight;

        try {
            cnn = getConnection();
            String sql = "SELECT * FROM Orders WHERE orderDate >= ? AND orderDate < ?";
            ps = cnn.prepareStatement(sql);
            ps.setDate(1, t1);
            ps.setDate(2, t3);
            rs = ps.executeQuery();
            while (rs.next()) {
                orderId = rs.getString(1);
                customerId = rs.getString(2);
                orderTimestamp = rs.getTimestamp(3);
                requiredTimestamp = rs.getTimestamp(4);
                shippedTimestamp = rs.getTimestamp(5);
                freight = rs.getDouble(5);
                shipAddress = rs.getString(7);
                order = new Order(orderId, customerId, orderTimestamp, requiredTimestamp, shippedTimestamp, freight, shipAddress);
                orderList.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (cnn != null) {
                cnn.close();
            }
        }
        return orderList;
    }
    public int getNextOrderId() throws SQLException{
        int id;
        List<Order> list = getOrderList();
        String s = list.get(list.size() - 1).getOrderId().substring(1, 4);
        id = Integer.parseInt(s) + 1;
        return id;
    }
}
