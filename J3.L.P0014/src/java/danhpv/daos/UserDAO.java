/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.daos;

import danhpv.db.MyConnection;
import danhpv.dtos.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author DELL
 */
public class UserDAO {

    private Connection connection;
    private PreparedStatement preStm;
    private ResultSet resultSet;

    private void closeConnection() throws Exception {
        if (resultSet != null) {
            resultSet.close();
        }
        if (preStm != null) {
            preStm.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    public UserDAO() {
    }

    public User checkEmailExisted(String email) throws Exception {
        User user = null;
        String sql = "select name, roleId from Users where email = ?";
        try {
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, email);
                resultSet = preStm.executeQuery();
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String roleId = resultSet.getString("roleId");
                    user = new User();
                    user.setEmail(email);
                    user.setName(name);
                    user.setRoleId(roleId);
                }
            }
        } finally {
            closeConnection();
        }
        return user;
    }
    
    public User checkLogin(String email, String password) throws Exception {
        User user = null;
        String sql = "select name, roleId, status from Users where email = ? and password = ?";
        try {
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, email);
                preStm.setString(2, password);
                resultSet = preStm.executeQuery();
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String roleId = resultSet.getString("roleId");
                    int status = resultSet.getInt("status");
                    user = new User();
                    user.setEmail(email);
                    user.setName(name);
                    user.setRoleId(roleId);
                    user.setStatus(status);
                }
            }
        } finally {
            closeConnection();
        }
        return user;
    }

    public boolean createNewAccount(User newUser, String hashPass) throws Exception {
        boolean checkSuccess = false;
        String sql = "insert into Users(email, roleId, name, password, status)\n"
                + " values(?, ?, ?, ?, ?)";
        try {
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, newUser.getEmail());
                preStm.setString(2, newUser.getRoleId());
                preStm.setString(3, newUser.getName());
                preStm.setString(4, hashPass);
                preStm.setInt(5, newUser.getStatus());
                checkSuccess = preStm.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return checkSuccess;
    }
}
