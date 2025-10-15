package com.systems.orderService.entity;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import com.systems.orderService.dto.OrderDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @NotBlank(message = "Item name cannot be empty")
    private String item;
	
    @NotBlank(message = "Customer name cannot be empty")
    private String customerName;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

}
