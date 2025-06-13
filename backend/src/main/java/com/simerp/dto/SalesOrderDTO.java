package com.simerp.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class SalesOrderDTO {
    private Long id;
    private String orderNumber;
    private Long customerId;
    private String customerName;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private String status;
    private List<SalesOrderItemDTO> items;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SalesOrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<SalesOrderItemDTO> items) {
        this.items = items;
    }
} 