package com.ra.controller.user;

import com.ra.model.entity.admin.Category;
import com.ra.model.entity.admin.Product;
import com.ra.model.service.category.CategoryService;
import com.ra.model.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    HttpSession session;
    @RequestMapping("/")
    public String index(Model model){
        List<Category> categoryList = categoryService.findAll();
        List<Product> productList = productService.findAll();
        List<Product> productList1 = productService.findAll();
        List<Product> productList2 = productService.findAll();
        session.setAttribute("category", categoryList);
        Collections.shuffle(productList);
        model.addAttribute("productList", productList);

        Collections.shuffle(productList1);
        model.addAttribute("productList1", productList1);

        Collections.shuffle(productList2);
        model.addAttribute("productList2", productList2);
        return "user/home";
    }
}
