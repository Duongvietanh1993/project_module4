package com.ra.controller.admin;

import com.ra.model.entity.admin.Category;
import com.ra.model.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
@PropertySource("classpath:config.properties")
public class CategoryController {
    @Value("${pathCategory}")
    private String pathCategory;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("category")
    public String table(Model model) {
        List<Category> list = categoryService.findAll();
        model.addAttribute("categoryList", list);
        return "admin/category/tables";
    }

    @GetMapping("/create")
    public String create(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "admin/category/create";
    }

    @PostMapping("/create")
    public String handleCreate(@RequestParam("imageProduct") MultipartFile file,@ModelAttribute("category") Category category) {
        String fileName = file.getOriginalFilename();
        File destination = new File(pathCategory + fileName);

        try {
            file.transferTo(destination);
            category.setCategoryImage("http://localhost:8080/upload/category/" + fileName);
            categoryService.saveOrUpdate(category);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/admin/category";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id")Integer id,Model model){
        Category category=categoryService.findById(id);
        model.addAttribute("category", category);
        return "admin/category/update";
    }
    @PostMapping("/update")
    public String handleUpdate(@RequestParam("imageProduct") MultipartFile file, @ModelAttribute("category") Category category) {
        String fileName = file.getOriginalFilename();
        File destination = new File(pathCategory + fileName);

        try {
            file.transferTo(destination);
            category.setCategoryImage("http://localhost:8080/upload/category/" + fileName);
            categoryService.saveOrUpdate(category);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/admin/category";
    }

}
