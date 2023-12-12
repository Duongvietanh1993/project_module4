package com.ra.config;

import com.ra.model.dto.user.response.UserResponesDTO;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthInterceptor implements HandlerInterceptor  {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        UserResponesDTO admin = (UserResponesDTO) session.getAttribute("admin");
        if(admin != null){
            return true;
        }
        response.sendRedirect("/login-admin");
        return  false;
    }
}
