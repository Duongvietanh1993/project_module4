package com.ra.model.dao.product;

import com.ra.model.dao.category.CategoryDAO;
import com.ra.model.entity.admin.Category;
import com.ra.model.entity.admin.Product;
import com.ra.util.ConnectionDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class ProductDAOimpl implements ProductDAO{
    private static int totalPage;
    @Autowired
    private CategoryDAO categoryDAO;
    @Override
    public List<Product> findAll() {
        Connection connection = null;
        List<Product> products = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_SHOW_PRODUCT()}");
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("name"));
                product.setImageUrl(resultSet.getString("url_image"));
                product.setProductDescription(resultSet.getString("description"));
                product.setProductPrice(resultSet.getFloat("price"));
                product.setProductStock(resultSet.getInt("stock"));
                product.setProductStatus(resultSet.getBoolean("status"));
                product.setCategory(categoryDAO.findById(resultSet.getInt("category_id")));
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return products;
    }

    @Override
    public boolean saveOrUpdate(Product product) {
        Connection connection = null;
        int check;
        try {
            connection = ConnectionDatabase.openConnection();
            if (product.getProductId() == 0) {
                CallableStatement callableStatement = connection.prepareCall("{CALL PROC_CREATE_PRODUCT(?,?,?,?,?,?,?)}");
                callableStatement.setString(1, product.getProductName());
                callableStatement.setString(2, product.getImageUrl());
                callableStatement.setString(3, product.getProductDescription());
                callableStatement.setFloat(4,product.getProductPrice());
                callableStatement.setInt(5,product.getProductStock());
                callableStatement.setBoolean(6, product.isProductStatus());
                callableStatement.setInt(7, product.getCategory().getCategoryId());
                check = callableStatement.executeUpdate();
            } else {
                CallableStatement callableStatement = connection.prepareCall("{CALL PROC_UPDATE_PRODUCT(?,?,?,?,?,?,?,?)}");
                callableStatement.setInt(1, product.getProductId());
                callableStatement.setString(2, product.getProductName());
                callableStatement.setString(3, product.getImageUrl());
                callableStatement.setString(4, product.getProductDescription());
                callableStatement.setFloat(5,product.getProductPrice());
                callableStatement.setInt(6,product.getProductStock());
                callableStatement.setBoolean(7, product.isProductStatus());
                callableStatement.setInt(8, product.getCategory().getCategoryId());
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
    public Product findById(Integer id) {
        Connection connection = null;
        Product product = new Product();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_FIND_PRODUCT_BY_ID(?)}");
            callableStatement.setInt(1, id);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                product.setProductId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("name"));
                product.setImageUrl(resultSet.getString("url_image"));
                product.setProductDescription(resultSet.getString("description"));
                product.setProductPrice(resultSet.getFloat("price"));
                product.setProductStock(resultSet.getInt("stock"));
                product.setProductStatus(resultSet.getBoolean("status"));
                product.setCategory(categoryDAO.findById(resultSet.getInt("category_id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    @Override
    public List<Product> findAllByCategoryId(int id) {
        Connection connection = null;
        List<Product> products = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_SHOW_PRODUCT_BY_CATEGORY(?)}");
            callableStatement.setInt(1,id);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("name"));
                product.setImageUrl(resultSet.getString("url_image"));
                product.setProductDescription(resultSet.getString("description"));
                product.setProductPrice(resultSet.getFloat("price"));
                product.setProductStock(resultSet.getInt("stock"));
                product.setProductStatus(resultSet.getBoolean("status"));
                product.setCategory(categoryDAO.findById(resultSet.getInt("category_id")));
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return products;
    }
}
