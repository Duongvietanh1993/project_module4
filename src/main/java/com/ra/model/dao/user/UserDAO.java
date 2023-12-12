package com.ra.model.dao.user;

import com.ra.model.dao.IGenericDAO;
import com.ra.model.entity.User;

public interface UserDAO extends IGenericDAO<User,Integer> {
    User findByEmail(String email);
    User checkLogin(String email ,String password);
}
