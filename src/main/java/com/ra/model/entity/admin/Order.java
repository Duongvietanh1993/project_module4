package com.ra.model.entity.admin;

import com.ra.model.dto.user.response.UserResponesDTO;

import java.sql.Date;

public class Order {
    private int orderId;
    private User user;
    private java.sql.Date orderDate;
    private float total;
    private String address;
    private String phone;
    private OrderStatus status;

    public Order() {
        this.status = OrderStatus.PENDING;
    }

    public Order(int orderId, User user, Date orderDate, float total, String address, String phone, OrderStatus status) {
        this.orderId = orderId;
        this.user = user;
        this.orderDate = orderDate;
        this.total = total;
        this.address = address;
        this.phone = phone;
        this.status = OrderStatus.PENDING;
    }

    public enum OrderStatus {
        PENDING,
        CONFIRM,
        COMPLETED,
        CANCELLED
    }


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
