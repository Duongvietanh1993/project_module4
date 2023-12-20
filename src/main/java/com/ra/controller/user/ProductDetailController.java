package com.ra.controller.user;

import com.ra.model.entity.admin.Product;
import com.ra.model.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class ProductDetailController {
    @Autowired
    private ProductService productService;

    @RequestMapping("/product-detail/{id}")
    public String index(@PathVariable("id") Integer id, Model model){
        Product product = productService.findById(id);
        model.addAttribute("detail", product);
        return "user/product-detail/product-detail";
    }
}
