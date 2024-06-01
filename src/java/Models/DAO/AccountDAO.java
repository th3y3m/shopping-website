/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.DAO;

import Models.Entity.Account;
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
public class AccountDAO {

    public Connection getConnection() throws Exception {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection cnn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=PizzaStore", "sa", "12345");
            return cnn;
        } catch (ClassNotFoundException | SQLException e) {
            throw e;
        }
    }

    public Account login(String userName, String password) throws Exception {
        Account account = null;
        Connection cnn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        String fullName, accountId;
        boolean type;

        try {
            cnn = getConnection();
            String sql = "select accountId, fullName, type from Account where [userName] = ? and [password] = ?";
            preStm = cnn.prepareStatement(sql);
            preStm.setString(1, userName);
            preStm.setString(2, password);
            rs = preStm.executeQuery();
            while (rs.next()) {
                accountId = rs.getString(1);
                fullName = rs.getString(2);
                type = rs.getBoolean(3);
                account = new Account(accountId, userName, password, fullName, type);
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
        return account;
    }

    public Account getAccountByAccountId(String accountId) throws Exception {
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Account account = null;
        boolean type;
        String fullName = null, password, userName;
        try {
            cnn = getConnection();
            String sql = "Select userName, password, fullName, type from Account where accountId = ?";
            ps = cnn.prepareStatement(sql);
            ps.setString(1, accountId);
            rs = ps.executeQuery();
            while (rs.next()) {
                userName = rs.getString(1);
                password = rs.getString(2);
                fullName = rs.getString(3);
                type = rs.getBoolean(4);
                account = new Account(accountId, userName, password, fullName, type);

            }
        } catch (Exception e) {
            throw e;
        }
        return account;
    }

    public boolean addAccount(Account account) throws Exception {
        Connection cnn = null;
        PreparedStatement ps = null;
        List<Account> accountList = getAccountList();
        try {
            cnn = getConnection();
            String sql = "Insert Account values (?, ?, ?, ?, ?)";
            ps = cnn.prepareStatement(sql);
            ps.setString(1, account.getAccountId());
            ps.setString(2, account.getUserName());
            ps.setString(3, account.getPassword());
            ps.setString(4, account.getFullName());
            ps.setBoolean(5, account.isType());
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

    public boolean deleteAccount(String accountId) throws Exception {
        Connection cnn = null;
        PreparedStatement ps = null;
        try {
            cnn = getConnection();
            String sql = "Delete Account where accountId = ?";
            ps = cnn.prepareStatement(sql);
            ps.setString(1, accountId);
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

    public boolean updateAccount(Account account) throws Exception {
        Connection cnn = null;
        PreparedStatement ps = null;
        try {
            cnn = getConnection();
            String sql = "UPDATE Account set userName = ?, password = ?, fullName = ?, type = ? where accountId = ?";
            ps = cnn.prepareStatement(sql);

            ps.setString(1, account.getUserName());
            ps.setString(2, account.getPassword());
            ps.setString(3, account.getFullName());
            ps.setBoolean(4, account.isType());
            ps.setString(5, account.getAccountId());
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

    public List<Account> getAccountList() throws SQLException {
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Account> accountList = new ArrayList<>();
        Account account = null;
        String accountId = null, userName, password;
        String fullName = null;
        boolean type;
        try {
            cnn = getConnection();
            String sql = "Select * from Account";
            ps = cnn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                accountId = rs.getString(1);
                userName = rs.getString(2);
                password = rs.getString(3);
                fullName = rs.getString(4);
                type = rs.getBoolean(5);
                account = new Account(accountId, userName, password, fullName, type);
                accountList.add(account);
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
        return accountList;
    }
    //Axxx
    public int getNextAccountId() throws SQLException{
        int id;
        List<Account> list = getAccountList();
        String s = list.get(list.size() - 1).getAccountId().substring(1, 4);
        id = Integer.parseInt(s) + 1;
        return id;
    }
}
