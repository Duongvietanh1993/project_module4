package com.ra.model.service.orderDetail;

import com.ra.model.dao.orderDetail.OrderDetailDAO;
import com.ra.model.entity.admin.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderDetailServiceIMPL implements OrderDetailService{
    @Autowired
    private OrderDetailDAO orderDetailDAO;

    @Override
    public boolean save(OrderDetail orderDetail) {
        return false;
    }

    @Override
    public List<OrderDetail> findAllByIdOrder(int id) {
        return orderDetailDAO.findAllByIdOrder(id);
    }
}
