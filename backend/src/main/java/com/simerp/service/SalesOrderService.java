package com.simerp.service;

import com.simerp.dto.SalesOrderDTO;
import com.simerp.dto.SalesOrderItemDTO;
import com.simerp.entity.Customer;
import com.simerp.entity.Product;
import com.simerp.entity.SalesOrder;
import com.simerp.entity.SalesOrderItem;
import com.simerp.repository.CustomerRepository;
import com.simerp.repository.ProductRepository;
import com.simerp.repository.SalesOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalesOrderService {

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<SalesOrderDTO> getAllSalesOrders() {
        return salesOrderRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public SalesOrderDTO getSalesOrderById(Long id) {
        SalesOrder salesOrder = salesOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sales order not found"));
        return convertToDTO(salesOrder);
    }

    @Transactional
    public SalesOrderDTO createSalesOrder(SalesOrderDTO salesOrderDTO) {
        SalesOrder salesOrder = new SalesOrder();
        updateSalesOrderFromDTO(salesOrder, salesOrderDTO);
        salesOrder = salesOrderRepository.save(salesOrder);
        return convertToDTO(salesOrder);
    }

    @Transactional
    public SalesOrderDTO updateSalesOrder(Long id, SalesOrderDTO salesOrderDTO) {
        SalesOrder salesOrder = salesOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sales order not found"));
        updateSalesOrderFromDTO(salesOrder, salesOrderDTO);
        salesOrder = salesOrderRepository.save(salesOrder);
        return convertToDTO(salesOrder);
    }

    @Transactional
    public void deleteSalesOrder(Long id) {
        salesOrderRepository.deleteById(id);
    }

    private SalesOrderDTO convertToDTO(SalesOrder salesOrder) {
        SalesOrderDTO dto = new SalesOrderDTO();
        dto.setId(salesOrder.getId());
        dto.setOrderNumber(salesOrder.getOrderNumber());
        dto.setCustomerId(salesOrder.getCustomer().getId());
        dto.setCustomerName(salesOrder.getCustomer().getName());
        dto.setOrderDate(salesOrder.getOrderDate());
        dto.setTotalAmount(salesOrder.getTotalAmount());
        dto.setStatus(salesOrder.getStatus());
        dto.setItems(salesOrder.getItems().stream()
                .map(this::convertItemToDTO)
                .collect(Collectors.toList()));
        return dto;
    }

    private SalesOrderItemDTO convertItemToDTO(SalesOrderItem item) {
        SalesOrderItemDTO dto = new SalesOrderItemDTO();
        dto.setId(item.getId());
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        dto.setTotalPrice(item.getTotalPrice());
        return dto;
    }

    private void updateSalesOrderFromDTO(SalesOrder salesOrder, SalesOrderDTO dto) {
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        
        salesOrder.setOrderNumber(dto.getOrderNumber());
        salesOrder.setCustomer(customer);
        salesOrder.setOrderDate(dto.getOrderDate());
        salesOrder.setStatus(dto.getStatus());

        // Clear existing items
        salesOrder.getItems().clear();

        // Add new items
        dto.getItems().forEach(itemDTO -> {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            
            SalesOrderItem item = new SalesOrderItem();
            item.setSalesOrder(salesOrder);
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());
            item.setUnitPrice(itemDTO.getUnitPrice());
            item.setTotalPrice(itemDTO.getTotalPrice());
            salesOrder.getItems().add(item);
        });

        // Calculate total amount
        BigDecimal totalAmount = salesOrder.getItems().stream()
                .map(SalesOrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        salesOrder.setTotalAmount(totalAmount);
    }
} 