package com.ra.model.dto.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class UserRegisterDTO {
    @NotEmpty(message = "Không được để trống!")
    private String userName;
    @Pattern(regexp = "/^\\S+@\\S+\\.\\S+$/",message = "Nhập sai định dạng Gmail!")
    private String userEmail;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",message = "Tối thiểu tám ký tự, ít nhất một chữ cái và một số!")
    private String userPassword;
    private String userImage;
    @Pattern(regexp = "/(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})\\b/",message = "Nhập sai định dạng số điện thoại!")
    private String userPhone;
    @NotEmpty(message = "Không được để trống!")
    private String userAddress;
    private boolean userRole;


    public UserRegisterDTO() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }
    public boolean isUserRole() {
        return userRole;
    }

    public void setUserRole(boolean userRole) {
        this.userRole = userRole;
    }

}
