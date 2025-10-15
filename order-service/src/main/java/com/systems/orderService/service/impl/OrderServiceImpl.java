package com.systems.orderService.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.systems.orderService.service.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.systems.orderService.dto.OrderDTO;
import com.systems.orderService.entity.InventoryItem;
import com.systems.orderService.entity.Order;
import com.systems.orderService.repo.OrderRepository;
import com.systems.orderService.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
    private OrderRepository orderRepository;
	
	
    private RestTemplate restTemplate = new RestTemplate();

	 @Value("${inventory-service.url}")
    private String INVENTORY_SERVICE_URL;

    public Order placeOrder(OrderDTO orderRequest) {
    	Order order;
        InventoryItem inventoryItem = restTemplate.getForObject(INVENTORY_SERVICE_URL + orderRequest.getItem(), InventoryItem.class);
        if (inventoryItem == null || inventoryItem.getStock() < orderRequest.getQuantity()) {
            throw new RuntimeException("Insufficient stock for item: " + orderRequest.getItem());
        }

        restTemplate.postForObject(INVENTORY_SERVICE_URL + "decrease", Map.of("itemName",orderRequest.getItem(),"quantity",orderRequest.getQuantity()),Void.class );
        order= Mapper.toOrder(orderRequest);
        return orderRepository.save(order);
    }

	@Override
	public List<OrderDTO> getAllOrders() {
		List<Order> orders = orderRepository.findAll();
		return orders.stream().map(Mapper::toOrderDTO).toList();
	}

}
