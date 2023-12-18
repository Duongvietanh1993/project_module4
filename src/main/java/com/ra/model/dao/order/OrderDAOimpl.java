package com.ra.model.dao.order;

import com.ra.model.dao.user.UserDAO;
import com.ra.model.entity.admin.Category;
import com.ra.model.entity.admin.Order;
import com.ra.util.ConnectionDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDAOimpl implements OrderDAO {
    @Autowired
    private UserDAO userDAO;

    @Override
    public List<Order> findAll() {
        Connection connection = null;
        List<Order> orderList = new ArrayList<>();

        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_SHOW_ORDER()}");
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("id"));
                order.setUser(userDAO.findById(resultSet.getInt("user_id")));
                order.setOrderDate(Date.valueOf(resultSet.getString("order_date")));
                order.setStatus(Order.OrderStatus.valueOf(resultSet.getString("order_status")));
                order.setTotal(resultSet.getFloat("total"));
                order.setAddress(resultSet.getString("address"));
                order.setPhone(resultSet.getString("phone"));
                orderList.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }

        return orderList;
    }

    @Override
    public boolean saveOrUpdate(Order order) {
        return false;
    }

    @Override
    public Order findById(Integer integer) {
        return null;
    }

    @Override
    public Order save(Order order) {
        Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        CallableStatement callableStatement = null;
        try {
            callableStatement = connection.prepareCall("CALL PROC_CREATE_ORDER(?,?,?,?,?)");

            callableStatement.setInt(1, order.getUser().getUserId());
            callableStatement.setFloat(2, order.getTotal());
            callableStatement.setString(3, order.getAddress());
            callableStatement.setString(4, order.getPhone());
            callableStatement.registerOutParameter(5,Types.INTEGER);

            int check = callableStatement.executeUpdate();
            if (check > 0) {
                order.setOrderId(callableStatement.getInt(5));
                return order;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return null;
    }
}
