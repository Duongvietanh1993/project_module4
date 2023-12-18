package com.ra.model.dao.orderDetail;

import com.ra.model.entity.admin.OrderDetail;
import com.ra.util.ConnectionDatabase;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
@Repository
public class OrderDetailDAOimpl implements OrderDetailDAO{
    @Override
    public void save(OrderDetail orderDetail) {
        Connection connection = null;

        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = null;
            callableStatement = connection.prepareCall("CALL PROC_CREATE_ORDER_DETAIL(?,?,?,?)");
            callableStatement.setInt(1, orderDetail.getOrder().getOrderId());
            callableStatement.setInt(2, orderDetail.getProduct().getProductId());
            callableStatement.setInt(3, orderDetail.getQuantity());
            callableStatement.setFloat(4, orderDetail.getPrice());
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }
}
