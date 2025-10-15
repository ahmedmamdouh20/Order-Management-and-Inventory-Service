package com.systems.orderService.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.systems.orderService.dto.OrderDTO;
import com.systems.orderService.entity.Order;
import com.systems.orderService.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@Validated
public class OrderController {
	@Autowired
	private OrderService orderService;

	@PostMapping
	public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderDTO orderRequest) {
		Order createdOrder = orderService.placeOrder(orderRequest);
		return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
	}

	@GetMapping
    public List<OrderDTO> getOrders() {
        return orderService.getAllOrders();
    }
}