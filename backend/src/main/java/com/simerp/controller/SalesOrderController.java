package com.simerp.controller;

import com.simerp.dto.SalesOrderDTO;
import com.simerp.service.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
@CrossOrigin(origins = "http://localhost:5173")
public class SalesOrderController {

    @Autowired
    private SalesOrderService salesOrderService;

    @GetMapping
    public ResponseEntity<List<SalesOrderDTO>> getAllSalesOrders() {
        return ResponseEntity.ok(salesOrderService.getAllSalesOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesOrderDTO> getSalesOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(salesOrderService.getSalesOrderById(id));
    }

    @PostMapping
    public ResponseEntity<SalesOrderDTO> createSalesOrder(@RequestBody SalesOrderDTO salesOrderDTO) {
        return ResponseEntity.ok(salesOrderService.createSalesOrder(salesOrderDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalesOrderDTO> updateSalesOrder(
            @PathVariable Long id,
            @RequestBody SalesOrderDTO salesOrderDTO) {
        return ResponseEntity.ok(salesOrderService.updateSalesOrder(id, salesOrderDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalesOrder(@PathVariable Long id) {
        salesOrderService.deleteSalesOrder(id);
        return ResponseEntity.ok().build();
    }
} 