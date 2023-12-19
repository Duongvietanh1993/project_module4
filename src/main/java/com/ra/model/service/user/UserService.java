package com.ra.model.service.user;

import com.ra.model.dto.user.UserRegisterDTO;
import com.ra.model.dto.user.response.UserResponesDTO;
import com.ra.model.entity.admin.User;
import com.ra.model.service.IGenericService;

public interface UserService extends IGenericService<User,Integer> {
    User findByEmail(String email);
    Boolean register(UserRegisterDTO user);
    UserResponesDTO checkLogin(String email , String password);
    boolean updateStatus(Integer id, boolean newStatus);
}
