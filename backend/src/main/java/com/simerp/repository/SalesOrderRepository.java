package com.simerp.repository;

import com.simerp.entity.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
    Optional<SalesOrder> findByOrderNumber(String orderNumber);
    boolean existsByOrderNumber(String orderNumber);
} 