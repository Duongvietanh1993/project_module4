package com.ra.model.dao.category;

import com.ra.model.entity.admin.Category;
import com.ra.util.ConnectionDatabase;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryDAOimpl implements CategoryDAO{
    @Override
    public List<Category> findAll() {
        Connection connection = null;
        List<Category> categoryList = new ArrayList<>();

        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_SHOW_CATEGORY()}");
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setCategoryId(resultSet.getInt("id"));
                category.setCategoryName(resultSet.getString("name"));
                category.setCategoryImage(resultSet.getString("image"));
                category.setCategoryDescription(resultSet.getString("description"));
                category.setCategoryStatus(resultSet.getBoolean("status"));
                categoryList.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }

        return categoryList;
    }

    @Override
    public boolean saveOrUpdate(Category category) {
        Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        int check;
        try {
            if (category.getCategoryId() == 0) {
                CallableStatement callableStatement = connection.prepareCall("{CALL PROC_CREATE_CATEGORY(?,?,?)}");
                callableStatement.setString(1, category.getCategoryName());
                callableStatement.setString(2, category.getCategoryImage());
                callableStatement.setString(3, category.getCategoryDescription());
                check = callableStatement.executeUpdate();
            } else {
                CallableStatement callableStatement = connection.prepareCall("{CALL PROC_UPDATE_CATEGORY(?,?,?,?,?)}");
                callableStatement.setInt(1, category.getCategoryId());
                callableStatement.setString(2, category.getCategoryName());
                callableStatement.setString(3, category.getCategoryImage());
                callableStatement.setString(4,category.getCategoryDescription());
                callableStatement.setBoolean(5, category.isCategoryStatus());
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
    public Category findById(Integer id) {
        Connection connection = null;
        Category category = new Category();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_FIND_CATEGORY_BY_ID(?)}");
            callableStatement.setInt(1,id);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                category.setCategoryId(resultSet.getInt("id"));
                category.setCategoryName(resultSet.getString("name"));
                category.setCategoryImage(resultSet.getString("image"));
                category.setCategoryDescription(resultSet.getString("description"));
                category.setCategoryStatus(resultSet.getBoolean("status"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return category;
    }

    @Override
    public boolean updateStatus(Integer id, boolean newStatus) {
        Connection connection = null;
        int check;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_UPDATE_STATUS_CATEGORY(?, ?)}");
            callableStatement.setInt(1, id);
            callableStatement.setBoolean(2, newStatus);
            check = callableStatement.executeUpdate();
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
}
