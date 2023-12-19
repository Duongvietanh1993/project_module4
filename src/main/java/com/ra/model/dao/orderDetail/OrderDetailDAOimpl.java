package com.ra.model.dao.orderDetail;

import com.ra.model.dao.order.OrderDAO;
import com.ra.model.dao.product.ProductDAO;
import com.ra.model.entity.admin.OrderDetail;
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
public class OrderDetailDAOimpl implements OrderDetailDAO {
    @Autowired
    OrderDAO orderDAO;
    @Autowired
    ProductDAO productDAO;

    @Override
    public boolean save(OrderDetail orderDetail) {
        Connection connection = null;

        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = null;
            callableStatement = connection.prepareCall("CALL PROC_CREATE_ORDER_DETAIL(?,?,?,?)");
            callableStatement.setInt(1, orderDetail.getOrder().getOrderId());
            callableStatement.setInt(2, orderDetail.getProduct().getProductId());
            callableStatement.setInt(3, orderDetail.getQuantity());
            callableStatement.setFloat(4, orderDetail.getPrice());
            int check = callableStatement.executeUpdate();
            if (check > 0) {
                return true;
            }
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return false;
    }

    @Override
    public List<OrderDetail> findAllByIdOrder(int id) {
        Connection connection = null;
        List<OrderDetail> orderDetailList = new ArrayList<>();
        connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL PROC_SHOW_ORDER_DETAIL(?)");
            callableStatement.setInt(1, id);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(orderDAO.findById(resultSet.getInt("order_id")));
                orderDetail.setProduct(productDAO.findById(resultSet.getInt("product_id")));
                orderDetail.setQuantity(resultSet.getInt("quanlity"));
                orderDetail.setPrice(resultSet.getInt("price"));
                orderDetailList.add(orderDetail);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return orderDetailList;
    }


}
