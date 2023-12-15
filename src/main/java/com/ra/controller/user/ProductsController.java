package com.ra.controller.user;

import com.ra.model.entity.admin.Product;
import com.ra.model.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class ProductsController {
    @Autowired
    ProductService productService;

    @GetMapping("/product-list/{id}")
    public String productList(@PathVariable("id") Integer id, Model model) {
        List<Product> productList = productService.findAllByCategoryId(id);
        model.addAttribute("products", productList);
        return "user/products/products";
    }


}
