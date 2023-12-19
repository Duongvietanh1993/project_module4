package com.ra.controller.admin;

import com.ra.model.entity.admin.User;
import com.ra.model.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    HttpSession session;

    @RequestMapping("user")
    public String table(Model model) {
        List<User> list = userService.findAll();
        model.addAttribute("listUser", list);
        return "admin/user/tables";
    }

    @RequestMapping("/user/{id}")
    public String updateUserStatus(@PathVariable("id") Integer id,
                                   @RequestParam("status")  Integer status,
                                   Model model){
        boolean newStatus = (status == 1);
        boolean updated = userService.updateStatus(id, newStatus);
        if (updated) {
            List<User> list = userService.findAll();
            model.addAttribute("listUser", list);
            return "admin/user/tables";
        }
        return null;
    }
}
