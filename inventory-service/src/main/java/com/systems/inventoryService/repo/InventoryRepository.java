package com.systems.inventoryService.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.systems.inventoryService.entity.InventoryItem;

public interface InventoryRepository extends JpaRepository<InventoryItem, Long> {
	
	 Optional<InventoryItem> findByItemName(String itemName);

}
