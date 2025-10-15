package com.systems.inventoryService.controller;

import com.systems.inventoryService.dto.InventoryItemDecreaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.systems.inventoryService.dto.InventoryItemDTO;
import com.systems.inventoryService.service.InventoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/inventory")
@Validated
public class InventoryServiceController {
	
	@Autowired
    private InventoryService inventoryService;
	
	@GetMapping("/{itemName}")
    public ResponseEntity<InventoryItemDTO> getInventory(@PathVariable String itemName) {
        return inventoryService.getItem(itemName)
            .map(item -> new ResponseEntity<>(item, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
	
	@PostMapping("/decrease")
    public ResponseEntity<Void> decreaseStock(@Valid @RequestBody InventoryItemDecreaseRequest inventoryItemDecreaseRequest) {
        inventoryService.decreaseStock(inventoryItemDecreaseRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
