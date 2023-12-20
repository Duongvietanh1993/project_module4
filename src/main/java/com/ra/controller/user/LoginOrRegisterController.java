package com.ra.controller.user;

import com.ra.model.dto.user.UserRegisterDTO;
import com.ra.model.dto.user.response.UserResponesDTO;
import com.ra.model.entity.admin.User;
import com.ra.model.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class LoginOrRegisterController {
    @Value("${pathUser}")
    private String pathUser;
    @Autowired
    private HttpSession session;
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(Model model, @RequestHeader("referer") String referer) {
        session.setAttribute("previousUrl", referer);
        User user = new User();
        model.addAttribute("user", user);
        return "user/login/login";
    }

    @PostMapping("/login")
    public String handleLogin(@ModelAttribute("user") User user) {
        UserResponesDTO authent = userService.checkLogin(user.getUserEmail(), user.getUserPassword());

        if (authent != null && authent.isUserRole() == true) {
            session.setAttribute("user", authent);
            String previousUrl = (String) session.getAttribute("previousUrl");
            if (previousUrl != null) {
                session.removeAttribute("previousUrl");
                return "redirect:" + previousUrl;
            }
            return "redirect:/";
        } else {
            return "redirect:/?error=true";
        }
    }

    @GetMapping("/logout-user")
    public String handleLogout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/?logout=true";
    }

    @GetMapping("/register")
    public String register(Model model) {
        UserRegisterDTO user = new UserRegisterDTO();
        model.addAttribute("user", user);
        return "user/login/login";
    }

    @PostMapping("/register")
    public String handleRegister(@Valid @ModelAttribute("user") UserRegisterDTO user) {
        if (userService.register(user)) {
            return "redirect:/?regis=true";
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
                return "redirect:/admin/?login=true";
            }
        }
        return "redirect:/login-admin";
    }

    @GetMapping("/logout-admin")
    public String handleLogoutAdmin(HttpSession session) {
        session.removeAttribute("admin");
        return "redirect:/login-admin?logout=true";
    }

    @GetMapping("/update-user")
    public String update(Model model) {
        UserResponesDTO userDTO = (UserResponesDTO) session.getAttribute("user");
        User user = userService.findById(userDTO.getUserId());
        model.addAttribute("userLogin", user);
        return "user/account/account";
    }

    @PostMapping("/update-user")
    public String handleUpdate(@RequestParam("imageUser") MultipartFile file,
                               @ModelAttribute("userLogin") User user) {
        String fileName = file.getOriginalFilename();
        File destination = new File(pathUser + fileName);
        try {
            if (!fileName.isEmpty()) {
                file.transferTo(destination);
                user.setUserImage("http://localhost:8080/upload/user/" + fileName);
            }
            userService.saveOrUpdate(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/";
    }
}
