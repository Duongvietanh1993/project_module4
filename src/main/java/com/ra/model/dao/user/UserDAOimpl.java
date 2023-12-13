package com.ra.model.dao.user;

import com.ra.model.entity.admin.User;
import com.ra.util.ConnectionDatabase;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAOimpl implements UserDAO {
    @Override
    public List<User> findAll() {
        Connection connection = null;
        List<User> userList = new ArrayList<>();

        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_SHOW_USER()}");
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("user_name"));
                user.setUserEmail(resultSet.getString("email"));
                user.setUserPassword(resultSet.getString("password"));
                user.setUserImage(resultSet.getString("image"));
                user.setUserPhone(resultSet.getString("phone"));
                user.setUserAddress(resultSet.getString("address"));
                user.setUserStatus(resultSet.getBoolean("status"));
                user.setUserRole(resultSet.getBoolean("role"));
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }

        return userList;
    }

    @Override
    public boolean saveOrUpdate(User user) {
        Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        int check;
        try {
            if (user.getUserId() == 0) {
                CallableStatement callableStatement = connection.prepareCall("{CALL PROC_CREATE_USER(?,?,?)}");
                callableStatement.setString(1, user.getUserName());
                callableStatement.setString(2, user.getUserEmail());
                callableStatement.setString(3, user.getUserPassword());
                check = callableStatement.executeUpdate();
            } else {
                CallableStatement callableStatement = connection.prepareCall("{CALL PROC_UPDATE_USER(?,?,?,?,?,?,?,?,?)}");
                callableStatement.setInt(1, user.getUserId());
                callableStatement.setString(2, user.getUserName());
                callableStatement.setString(3, user.getUserEmail());
                callableStatement.setString(4, user.getUserPassword());
                callableStatement.setString(5, user.getUserImage());
                callableStatement.setString(6, user.getUserPhone());
                callableStatement.setString(7, user.getUserAddress());
                callableStatement.setBoolean(8, user.isUserStatus());
                callableStatement.setBoolean(9, user.isUserRole());
                check = callableStatement.executeUpdate();
            }
            if (check > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return false;
    }

    @Override
    public User findById(Integer id) {
        Connection connection = null;
        User user = new User();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_FIND_USER_BY_ID(?)}");
            callableStatement.setInt(1, id);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                user.setUserId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("user_name"));
                user.setUserEmail(resultSet.getString("email"));
                user.setUserPassword(resultSet.getString("password"));
                user.setUserImage(resultSet.getString("image"));
                user.setUserPhone(resultSet.getString("phone"));
                user.setUserAddress(resultSet.getString("address"));
                user.setUserStatus(resultSet.getBoolean("status"));
                user.setUserRole(resultSet.getBoolean("role"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return user;
    }

    @Override
    public User findByEmail(String email) {
        Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        User user = new User();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL PROC_FIND_USER_BY_EMAIL(?)");
            callableStatement.setString(1, email);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                user.setUserId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("user_name"));
                user.setUserEmail(resultSet.getString("email"));
                user.setUserPassword(resultSet.getString("password"));
                user.setUserImage(resultSet.getString("image"));
                user.setUserPhone(resultSet.getString("phone"));
                user.setUserAddress(resultSet.getString("address"));
                user.setUserStatus(resultSet.getBoolean("status"));
                user.setUserRole(resultSet.getBoolean("role"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return user;
    }

    @Override
    public User checkLogin(String email, String password) {
        User user = findByEmail(email);
        if (user != null && BCrypt.checkpw(password, user.getUserPassword())) {
            return user;
        }
        return null;
    }
}
