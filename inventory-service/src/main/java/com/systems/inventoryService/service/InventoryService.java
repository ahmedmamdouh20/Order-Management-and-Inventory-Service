package com.systems.inventoryService.service;

import java.util.Optional;

import com.systems.inventoryService.dto.InventoryItemDecreaseRequest;
import org.springframework.stereotype.Service;

import com.systems.inventoryService.dto.InventoryItemDTO;

@Service
public interface InventoryService {
	
	 Optional<InventoryItemDTO> getItem(String itemName);
	 void decreaseStock(InventoryItemDecreaseRequest inventoryItemDecreaseRequest);

}
