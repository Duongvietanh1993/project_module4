package com.ra.controller.admin;

import com.ra.model.entity.admin.Category;
import com.ra.model.entity.admin.Product;
import com.ra.model.service.category.CategoryService;
import com.ra.model.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")

public class ProductController {
    @Value("${pathProduct}")
    private String pathProduct;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("product")
    public String table(Model model) {
        List<Product> list = productService.findAll();
        model.addAttribute("productList", list);
        return "admin/product/index_product";
    }

    @GetMapping("/create-product")
    public String create(Model model) {
        List<Category> categoryList = categoryService.findAll();
        Product product = new Product();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("product", product);
        return "admin/product/create_product";
    }

    @PostMapping("/create-product")
    public String handleCreate(@RequestParam("imageProduct") MultipartFile file,
                               @ModelAttribute("product") Product product) {
        if (file.isEmpty()) {
            return "error";
        }
        String fileName = file.getOriginalFilename();
        File destination = new File(pathProduct + fileName);
        try {
            if (destination.exists()) {
                return "error";
            }
            file.transferTo(destination);
            product.setImageUrl("http://localhost:8080/upload/product/"+fileName);
            productService.saveOrUpdate(product);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/admin/product";
    }
}
