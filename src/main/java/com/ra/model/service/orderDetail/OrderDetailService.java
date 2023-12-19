package com.ra.model.service.orderDetail;

import com.ra.model.entity.admin.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    boolean save(OrderDetail orderDetail);
    List<OrderDetail> findAllByIdOrder(int id);
}
