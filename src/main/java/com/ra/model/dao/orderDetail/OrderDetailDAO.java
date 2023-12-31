package com.ra.model.dao.orderDetail;

import com.ra.model.dao.IGenericDAO;
import com.ra.model.entity.admin.OrderDetail;
import com.ra.model.service.IGenericService;

import java.util.List;

public interface OrderDetailDAO {
    boolean save(OrderDetail orderDetail);
    List<OrderDetail> findAllByIdOrder(int id);
}
