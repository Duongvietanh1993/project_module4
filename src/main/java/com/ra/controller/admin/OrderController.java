package com.ra.controller.admin;

import com.ra.model.entity.admin.Order;
import com.ra.model.entity.admin.OrderDetail;
import com.ra.model.service.order.OrderService;
import com.ra.model.service.orderDetail.OrderDetailService;
import com.ra.model.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private CartService cartService;

    @RequestMapping("/order")
    public String table(Model model) {
        List<Order> list = orderService.findAll();
        model.addAttribute("orderList", list);
        return "admin/order/index_order";
    }

    @GetMapping("/orderDetail/{id}")
    public String tableOrderDetail(@PathVariable("id") Integer id, Model model) {
        List<OrderDetail> list = orderDetailService.findAllByIdOrder(id);
        Order order = orderService.findById(id);
        double totalAmount = totalAmount(id);
        model.addAttribute("order", order);
        model.addAttribute("orderDetailList", list);
        model.addAttribute("totalAmount", totalAmount);
        return "admin/order/orderDetail";
    }

    public double totalAmount(int id) {
        List<OrderDetail> orderDetails = orderDetailService.findAllByIdOrder(id);
        double total = 0;

        for (OrderDetail item : orderDetails) {
            double price = item.getPrice();
            int quantity = item.getQuantity();
            total += price * quantity;
        }

        return total;
    }

    @RequestMapping("/order/{id}")
    public String updateOrderStatus(@PathVariable("id") Integer orderId,
                                    @RequestParam("status") String status,Model model) {
        Order.OrderStatus newStatus = Order.OrderStatus.valueOf(status.toUpperCase());
        boolean updated = orderService.updateOrderStatus(orderId, newStatus);
        if (updated) {
            List<Order> list = orderService.findAll();
            model.addAttribute("orderList", list);
            return "admin/order/index_order";
        }
        return null;
    }

}
