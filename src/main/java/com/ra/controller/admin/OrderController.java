package com.ra.controller.admin;

import com.ra.model.entity.admin.Order;
import com.ra.model.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("/order")
    public String table(Model model){
        List<Order> list = orderService.findAll();
        model.addAttribute("orderList", list);
        return "admin/order/index_order";
    }
}
