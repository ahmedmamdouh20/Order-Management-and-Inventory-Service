package com.systems.inventoryService.service.impl;

import java.util.Optional;

import com.systems.inventoryService.dto.InventoryItemDecreaseRequest;
import com.systems.inventoryService.service.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systems.inventoryService.dto.InventoryItemDTO;
import com.systems.inventoryService.entity.InventoryItem;
import com.systems.inventoryService.repo.InventoryRepository;
import com.systems.inventoryService.service.InventoryService;

@Service
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	private InventoryRepository inventoryRepository;

	@Override
	public Optional<InventoryItemDTO> getItem(String itemName) {
		Optional<InventoryItem> inventoryItem = inventoryRepository.findByItemName(itemName);
		if(inventoryItem.isEmpty())  throw new RuntimeException("No item found = : " + itemName);
		return Optional.of(Mapper.toDTO(inventoryItem.get()));
	}
	
	public void decreaseStock(InventoryItemDecreaseRequest inventoryItemDecreaseRequest) {
        InventoryItem item = inventoryRepository.findByItemName(inventoryItemDecreaseRequest.getItemName())
            .orElseThrow(() -> new RuntimeException("Item not found"));
        
        if (item.getStock() < inventoryItemDecreaseRequest.getQuantity()) {
            throw new RuntimeException("Insufficient stock for item: " + inventoryItemDecreaseRequest.getItemName());
        }
        
        item.setStock(item.getStock() - inventoryItemDecreaseRequest.getQuantity());
        inventoryRepository.save(item); 
    }

}
