package com.ra.model.service.order;

import com.ra.model.dao.order.OrderDAO;
import com.ra.model.dao.orderDetail.OrderDetailDAO;
import com.ra.model.entity.admin.Order;
import com.ra.model.entity.admin.OrderDetail;
import com.ra.model.entity.user.CartItem;
import com.ra.model.service.user.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderServiceIMPL implements OrderService{
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderDetailDAO orderDetailDAO;
    @Override
    public List<Order> findAll() {
        return orderDAO.findAll();
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
    public Boolean order(Order order) {
        try{
            Order newOrder =  orderDAO.save(order);
            List<CartItem> list = cartService.getCartItems();
            for (CartItem item : list) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(newOrder);
                orderDetail.setProduct(item.getProduct());
                orderDetail.setPrice(item.getProduct().getProductPrice());
                orderDetail.setQuantity(item.getQuantity());
                orderDetailDAO.save(orderDetail);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }
}
