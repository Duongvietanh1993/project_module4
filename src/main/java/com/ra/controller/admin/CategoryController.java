package com.ra.controller.admin;

import com.ra.model.entity.Category;
import com.ra.model.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class CategoryController {
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
    public String handleCreate(@ModelAttribute("category") Category category){
        categoryService.saveOrUpdate(category);
        return "redirect:/admin/category";
    }

}
