package com.systems.orderService.service;

import java.util.List;


import com.systems.orderService.dto.OrderDTO;
import com.systems.orderService.entity.Order;


public interface OrderService {
	
	 Order placeOrder(OrderDTO orderRequest);
	 List<OrderDTO> getAllOrders();
	        
}
