package com.ra.controller.user;

import com.ra.model.entity.User;
import com.ra.model.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class LoginOrRegisterController {
    @Autowired
    HttpSession session;
    @Autowired
    UserService userService;
    @GetMapping("/login")
    public String login(Model model){
        User user=new User();
        model.addAttribute("user",user);
        return "user/login/login";
    }
    @PostMapping("/login")
    public String handleLogin(@ModelAttribute("user") User user,Model model){
        User authent = userService.checkLogin(user.getUserEmail(), user.getUserPassword());

        if (authent != null) {
            model.addAttribute("user", authent);
            session.setAttribute("user",user);
            return "redirect:/";
        } else {
            return "redirect:/login";
        }
    }
    @GetMapping("/logout")
    public String logout(){
        session.removeAttribute("user");
        return "redirect:/";
    }

    @GetMapping("/register")
    public String register(Model model){
        User user=new User();
        model.addAttribute("user",user);
        return "user/login/login";
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("user") User user){
        userService.saveOrUpdate(user);
        return "user/login/login";
    }
}
