package com.ra.model.service.order;

import com.ra.model.entity.admin.Order;
import com.ra.model.service.IGenericService;

public interface OrderService extends IGenericService<Order,Integer> {
    Boolean order(Order order);
    boolean updateOrderStatus(Integer orderId, Order.OrderStatus newStatus);
}
