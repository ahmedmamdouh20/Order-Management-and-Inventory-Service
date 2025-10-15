package com.systems.inventoryService.dto;

import lombok.Data;

@Data
public class InventoryItemDecreaseRequest {


    private String itemName;
    private int quantity;
}
