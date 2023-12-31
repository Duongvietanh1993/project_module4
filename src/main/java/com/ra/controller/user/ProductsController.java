package com.ra.controller.user;

import com.ra.model.entity.admin.Product;
import com.ra.model.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class ProductsController {
    @Autowired
    ProductService productService;


    @GetMapping("/product-list/{id}")
    public String productList(@PathVariable("id") Integer id, Model model) {

        List<Product> productList = productService.findAllByCategoryId(id).stream().filter(product -> product.isProductStatus()).collect(Collectors.toList());
        model.addAttribute("products", productList);
        return "user/products/products";
    }
    @GetMapping("/product-all")
    public String productList( Model model) {
        List<Product> productList = productService.findAll().stream().filter(product -> product.isProductStatus()).collect(Collectors.toList());
        Collections.shuffle(productList);
        model.addAttribute("productAll", productList);
        return "user/products/productAll";
    }
    @GetMapping("/product-search")
    public String searchProduct(@RequestParam("keyword") String keyword, Model model) {
        List<Product> productList = productService.searchByName(keyword).stream()
                .filter(Product::isProductStatus)
                .collect(Collectors.toList());
        model.addAttribute("productAll", productList);
        return "user/products/productAll";
    }
}
