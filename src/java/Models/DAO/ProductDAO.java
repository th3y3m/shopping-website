/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.DAO;

import Models.Entity.Product;
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
public class ProductDAO {

    public Connection getConnection() throws Exception {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection cnn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=PizzaStore", "sa", "12345");
            return cnn;
        } catch (ClassNotFoundException | SQLException e) {
            throw e;
        }
    }

    public Product getProductByProductId(String productId) throws Exception {
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Product product = null;
        String productName, supplierId, categoryId, productImage;
        int quantityPerUnit;
        double unitPrice;
        try {
            cnn = getConnection();
            String sql = "Select productName, supplierId, categoryId, quantityPerUnit, unitPrice, productImage from Products where productId = ?";
            ps = cnn.prepareStatement(sql);
            ps.setString(1, productId);
            rs = ps.executeQuery();
            while (rs.next()) {
                productName = rs.getString(1);
                supplierId = rs.getString(2);
                categoryId = rs.getString(3);
                quantityPerUnit = rs.getInt(4);
                unitPrice = rs.getDouble(5);
                productImage = rs.getString(6);
                product = new Product(productId, productName, supplierId, categoryId, quantityPerUnit, unitPrice, productImage);

            }
        } catch (Exception e) {
            throw e;
        }
        return product;
    }

    public Product getNewestProduct() throws Exception {
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Product product = null;
        String productName, productId, supplierId, categoryId, productImage;
        int quantityPerUnit;
        double unitPrice;
        try {
            cnn = getConnection();
            String sql = "SELECT TOP 1 * FROM Products p\n"
                    + "ORDER BY p.ProductID desc";
            ps = cnn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                productId = rs.getString(1);
                productName = rs.getString(2);
                supplierId = rs.getString(3);
                categoryId = rs.getString(4);
                quantityPerUnit = rs.getInt(5);
                unitPrice = rs.getDouble(6);
                productImage = rs.getString(7);
                product = new Product(productId, productName, supplierId, categoryId, quantityPerUnit, unitPrice, productImage);

            }
        } catch (Exception e) {
            throw e;
        }
        return product;
    }

    public boolean addProduct(Product product) throws Exception {
        Connection cnn = null;
        PreparedStatement ps = null;
        try {
            cnn = getConnection();
            String sql = "Insert Products values (?, ?, ?, ?, ?, ?, ?)";
            ps = cnn.prepareStatement(sql);
            ps.setString(1, product.getProductId());
            ps.setString(2, product.getProductName());
            ps.setString(3, product.getSupplierId());
            ps.setString(4, product.getCategoryId());
            ps.setInt(5, product.getQuantityPerUnit());
            ps.setDouble(6, product.getUnitPrice());
            ps.setString(7, product.getProductImage());
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

    public boolean deleteProduct(String productId) throws Exception {
        Connection cnn = null;
        PreparedStatement ps = null;
        try {
            cnn = getConnection();
            String sql = "Delete Products where productId = ?";
            ps = cnn.prepareStatement(sql);
            ps.setString(1, productId);
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

    public boolean updateProduct(Product product) throws Exception {
        Connection cnn = null;
        PreparedStatement ps = null;
        try {
            cnn = getConnection();
            String sql = "UPDATE Products set [ProductName] = ?, [SupplierID] = ?, [CategoryID] = ?, [QuantityPerUnit] = ?, [UnitPrice] = ?, [ProductImage] = ? "
                    + "where ProductID = ?";
            ps = cnn.prepareStatement(sql);

            ps.setString(1, product.getProductName());
            ps.setString(2, product.getSupplierId());
            ps.setString(3, product.getCategoryId());
            ps.setInt(4, product.getQuantityPerUnit());
            ps.setDouble(5, product.getUnitPrice());
            ps.setString(6, product.getProductImage());
            ps.setString(7, product.getProductId());

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

    public List<Product> getProductList() throws SQLException {
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Product> productList = new ArrayList<>();
        Product product = null;
        String productName, supplierId, categoryId, productImage, productId;
        int quantityPerUnit;
        double unitPrice;
        try {
            cnn = getConnection();
            String sql = "Select * from Products";
            ps = cnn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                productId = rs.getString(1);
                productName = rs.getString(2);
                supplierId = rs.getString(3);
                categoryId = rs.getString(4);
                quantityPerUnit = rs.getInt(5);
                unitPrice = rs.getDouble(6);
                productImage = rs.getString(7);
                product = new Product(productId, productName, supplierId, categoryId, quantityPerUnit, unitPrice, productImage);
                productList.add(product);
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
        return productList;
    }

    public List<Product> getProductListByName(String searchValue) throws SQLException {
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Product> productList = new ArrayList<>();
        Product product = null;
        String productName, supplierId, categoryId, productImage, productId;
        int quantityPerUnit;
        double unitPrice;
        try {
            cnn = getConnection();
            String sql = "Select * from Products where productName LIKE ?";
            ps = cnn.prepareStatement(sql);
            ps.setString(1, "%" + searchValue + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                productId = rs.getString(1);
                productName = rs.getString(2);
                supplierId = rs.getString(3);
                categoryId = rs.getString(4);
                quantityPerUnit = rs.getInt(5);
                unitPrice = rs.getDouble(6);
                productImage = rs.getString(7);
                product = new Product(productId, productName, supplierId, categoryId, quantityPerUnit, unitPrice, productImage);
                productList.add(product);
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
        return productList;
    }

    public List<Product> getProductListByCaterory(String categoryName) throws SQLException {
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Product> productList = new ArrayList<>();
        Product product = null;
        String productName, supplierId, categoryId, productImage, productId;
        int quantityPerUnit;
        double unitPrice;
        try {
            cnn = getConnection();
            String sql = "Select p.ProductID, p.ProductName, p.SupplierID, p.CategoryID, p.QuantityPerUnit, p.UnitPrice, p.ProductImage from Products p join Categories c on p.CategoryID = c.CategoryID\n"
                    + "where c.CategoryName = ?";
            ps = cnn.prepareStatement(sql);
            ps.setString(1, categoryName);
            rs = ps.executeQuery();
            while (rs.next()) {
                productId = rs.getString(1);
                productName = rs.getString(2);
                supplierId = rs.getString(3);
                categoryId = rs.getString(4);
                quantityPerUnit = rs.getInt(5);
                unitPrice = rs.getDouble(6);
                productImage = rs.getString(7);
                product = new Product(productId, productName, supplierId, categoryId, quantityPerUnit, unitPrice, productImage);
                productList.add(product);
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
        return productList;
    }

    public List<Product> getProductListByCaterory(String searchValue, List<Product> list) throws SQLException {

        List<Product> productList = new ArrayList<>();

        try {
            for (Product p : list) {
                if (p.getProductName().toLowerCase().contains(searchValue.toLowerCase())
                        || p.getProductId().toLowerCase().contains(searchValue.toLowerCase())) {
                    productList.add(p);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }

    public List<Product> getProductListByPrice(String minPrice, String maxPrice, List<Product> list) {
        List<Product> productList = new ArrayList<>();

        try {

            if (!minPrice.isEmpty() && !maxPrice.isEmpty()) {
                for (Product p : list) {
                    if (p.getUnitPrice() >= Double.parseDouble(minPrice) && p.getUnitPrice() <= Double.parseDouble(maxPrice)) {
                        productList.add(p);
                    }
                }
            } else if (!minPrice.isEmpty() && maxPrice.isEmpty()) {
                for (Product p : list) {
                    if (p.getUnitPrice() >= Double.parseDouble(minPrice)) {
                        productList.add(p);
                    }
                }
            } else if (minPrice.isEmpty() && !maxPrice.isEmpty()) {
                for (Product p : list) {
                    if (p.getUnitPrice() <= Double.parseDouble(maxPrice)) {
                        productList.add(p);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }

    public int getNextProductId() throws SQLException {
        int id;
        List<Product> list = getProductList();
        String s = list.get(list.size() - 1).getProductId().substring(1, 4);
        id = Integer.parseInt(s) + 1;
        return id;
    }

    public String getProductNameById(String id) throws Exception {
        Product product = getProductList().stream().filter(b -> b.getProductId().trim().
                equals(id.trim())).findAny().orElse(null);
        return product.getProductName();
    }
}
