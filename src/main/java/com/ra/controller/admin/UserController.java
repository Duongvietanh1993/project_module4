package com.ra.controller.admin;

import com.ra.model.entity.User;
import com.ra.model.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("user")
    public String table(Model model){
        List<User> list = userService.findAll();
        model.addAttribute("listUser", list);
        return "admin/user/tables";
    }
}
