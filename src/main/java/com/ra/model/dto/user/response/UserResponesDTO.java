package com.ra.model.dto.user.response;

import javax.validation.constraints.Pattern;

public class UserResponesDTO {
    private int userId;
    private String userName;
    @Pattern(regexp = "/^\\S+@\\S+\\.\\S+$/",message = "Nhập sai định dạng Gmail!")
    private String userEmail;
    private String userImage;
    private String userPhone;
    private String userAddress;
    private boolean userRole;

    public UserResponesDTO() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
