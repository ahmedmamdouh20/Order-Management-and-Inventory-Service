package com.systems.orderService.dto;

import lombok.Data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
@Data
public class OrderDTO {
	
	@NotBlank(message = "Item name cannot be empty")
	private String item;
	
	@NotBlank(message = "Customer name cannot be empty")
	private String customerName;

	@Min(value = 1, message = "quantity must be at least 1")
	private int quantity;

}
