package com.systems.orderService.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class InventoryItem {

	@Id
	private Long id;

	@NotBlank(message = "Item name cannot be empty")
	private String itemName;

	@Min(value = 0, message = "Stock must be at least 0")
	private int stock;


	

}
