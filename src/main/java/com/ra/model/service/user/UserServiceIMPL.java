package com.ra.model.service.user;

import com.ra.model.dao.user.UserDAO;
import com.ra.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceIMPL implements UserService{
    @Autowired
    private UserDAO userDAO;
    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public boolean saveOrUpdate(User user) {
        return userDAO.saveOrUpdate(user);
    }

    @Override
    public User findById(Integer integer) {
        return userDAO.findById(integer);
    }

    @Override
    public User findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    public User checkLogin(String email, String password) {
        return userDAO.checkLogin(email,password);
    }
}
