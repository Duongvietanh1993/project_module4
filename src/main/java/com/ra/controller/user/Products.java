package com.ra.controller.user;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class Products {
    @RequestMapping("/products")
    public String index(){
        return "user/products/products";
    }
}
