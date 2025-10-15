package com.systems.orderService.service;

import com.systems.orderService.dto.OrderDTO;
import com.systems.orderService.entity.Order;

public final class Mapper {

    public static OrderDTO toOrderDTO(Order order) {
        if (order == null) {
            return null;
        }

        OrderDTO dto = new OrderDTO();
        dto.setCustomerName(order.getCustomerName());
        dto.setItem(order.getItem());
        dto.setQuantity(order.getQuantity());
        return dto;
    }

    public static Order toOrder(OrderDTO orderDTO) {
        if (orderDTO == null) {
            return null;
        }

        Order order = new Order();
        order.setCustomerName(orderDTO.getCustomerName());
        order.setItem(orderDTO.getItem());
        order.setQuantity(orderDTO.getQuantity());
        return order;
    }

}
