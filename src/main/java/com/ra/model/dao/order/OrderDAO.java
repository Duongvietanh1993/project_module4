package com.ra.model.dao.order;

import com.ra.model.dao.IGenericDAO;
import com.ra.model.entity.admin.Order;

public interface OrderDAO extends IGenericDAO<Order, Integer> {
    Order save(Order order);
    boolean updateOrderStatus(Integer orderId, Order.OrderStatus newStatus);
}
