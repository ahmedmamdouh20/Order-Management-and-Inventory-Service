package com.systems.orderService.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.systems.orderService.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
