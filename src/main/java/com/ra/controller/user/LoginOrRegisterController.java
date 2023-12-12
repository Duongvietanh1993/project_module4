package com.ra.controller.user;

import com.ra.model.dto.user.UserRegisterDTO;
import com.ra.model.dto.user.response.UserResponesDTO;
import com.ra.model.entity.User;
import com.ra.model.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
    public String handleLogin(@ModelAttribute("user") User user) {
        UserResponesDTO authent = userService.checkLogin(user.getUserEmail(), user.getUserPassword());

        if (authent != null && authent.isUserRole()==true) {
            session.setAttribute("user", authent);
            return "redirect:/";
        } else {
            return "redirect:/?error=true";
        }
    }
    @GetMapping("/logout")
    public String handleLogout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.removeAttribute("user");
        redirectAttributes.addFlashAttribute("logoutMessage", "Đăng xuất thành công!");
        return "redirect:/?logout=true";
    }

    @GetMapping("/register")
    public String register(Model model){
        UserRegisterDTO user = new UserRegisterDTO();
        model.addAttribute("user",user);
        return "user/login/login";
    }

    @PostMapping("/register")
    public String handleRegister(@Valid @ModelAttribute("user") UserRegisterDTO user) {
            if (userService.register(user)) {
                return "redirect:/";
            }
        return "redirect:/register";
    }


    @GetMapping("/login-admin")
    public String loginAdmin() {
        return "admin/loginAdmin";
    }

    @PostMapping("/handle-login")
    public String handleLogin(@RequestParam("email") String email, @RequestParam("password") String password) {
        UserResponesDTO authent = userService.checkLogin(email, password);
        if (authent != null) {
            if (!authent.isUserRole()) {
                session.setAttribute("admin", authent);
                return "redirect:/admin/";
            }
        }
        return "redirect:/login-admin";
    }

}
