package com.ra.controller.admin;

import com.ra.model.entity.admin.Category;
import com.ra.model.entity.admin.User;
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
        return "admin/category/index_category";
    }

    @GetMapping("/create-category")
    public String create(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "admin/category/create_category";
    }

    @PostMapping("/create-category")
    public String handleCreate(@RequestParam("imageCategory") MultipartFile file,
                               @ModelAttribute("category") Category category) {
        if (file.isEmpty()) {
            // Xử lý khi không có tệp tin được chọn
            // Ví dụ: Hiển thị thông báo lỗi hoặc chuyển hướng đến trang lỗi
            return "error-page";
        }

        String fileName = file.getOriginalFilename();
        File destination = new File(pathCategory + fileName);

        try {
            // Kiểm tra sự tồn tại của tệp tin đích trước khi ghi tệp tin mới
            if (destination.exists()) {
                // Xử lý khi tệp tin đích đã tồn tại
                // Ví dụ: Hiển thị thông báo lỗi hoặc chuyển hướng đến trang lỗi
                return "error-page";
            }

            file.transferTo(destination);
            category.setCategoryImage("http://localhost:8080/upload/category/" + fileName);
            categoryService.saveOrUpdate(category);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/admin/category";
    }

    @GetMapping("/update-category/{id}")
    public String update(@PathVariable("id")Integer id,Model model){
        Category category=categoryService.findById(id);
        model.addAttribute("category", category);
        return "admin/category/update_category";
    }
    @PostMapping("/update-category")
    public String handleUpdate(@RequestParam("imageCategory") MultipartFile file,
                               @ModelAttribute("category") Category category) {


        String fileName = file.getOriginalFilename();
        File destination = new File(pathCategory + fileName);

        try {
           if (!fileName.isEmpty()){
               file.transferTo(destination);
               category.setCategoryImage("http://localhost:8080/upload/category/" + fileName);
           }

            categoryService.saveOrUpdate(category);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/admin/category";
    }
    @RequestMapping("/category/{id}")
    public String updateUserStatus(@PathVariable("id") Integer id,
                                   @RequestParam("status")  Integer status,
                                   Model model){
        boolean newStatus = (status == 1);
        boolean updated = categoryService.updateStatus(id, newStatus);
        if (updated) {
            List<Category> list = categoryService.findAll();
            model.addAttribute("categoryList", list);
            return "admin/category/index_category";
        }
        return null;
    }

}
