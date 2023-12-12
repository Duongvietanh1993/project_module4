package com.ra.model.service.user;

import com.ra.model.dao.user.UserDAO;
import com.ra.model.dto.user.UserRegisterDTO;
import com.ra.model.dto.user.response.UserResponesDTO;
import com.ra.model.entity.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceIMPL implements UserService {
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
    public Boolean register(UserRegisterDTO userDTO) {
        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setUserEmail(userDTO.getUserEmail());
        user.setUserPhone(userDTO.getUserPhone());
        user.setUserAddress(userDTO.getUserAddress());
        user.setUserImage(userDTO.getUserImage());
        user.setUserRole(userDTO.isUserRole());
        //mã hóa
        String hasPassword = BCrypt.hashpw(userDTO.getUserPassword(), BCrypt.gensalt(12));
        user.setUserPassword(hasPassword);
        return userDAO.saveOrUpdate(user);
    }

    @Override
    public UserResponesDTO checkLogin(String email, String password) {
        User user = userDAO.findByEmail(email);
        if (user != null) {
            if (BCrypt.checkpw(password, user.getUserPassword())) {
                UserResponesDTO userResponseDTO = new UserResponesDTO();
                userResponseDTO.setUserId(user.getUserId());
                userResponseDTO.setUserName(user.getUserName());
                userResponseDTO.setUserEmail(user.getUserEmail());
                userResponseDTO.setUserImage(user.getUserImage());
                userResponseDTO.setUserPhone(user.getUserPhone());
                userResponseDTO.setUserPhone(user.getUserPhone());
                userResponseDTO.setUserRole(user.isUserRole());
                return userResponseDTO;
            }
        }
        return null;
    }
}
