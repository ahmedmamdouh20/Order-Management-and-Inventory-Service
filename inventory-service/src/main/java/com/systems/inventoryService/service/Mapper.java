package com.systems.inventoryService.service;

import com.systems.inventoryService.dto.InventoryItemDTO;
import com.systems.inventoryService.entity.InventoryItem;

public class Mapper {

        public static InventoryItemDTO toDTO(InventoryItem entity) {
            if (entity == null) {
                return null;
            }

            InventoryItemDTO dto = new InventoryItemDTO();
            dto.setItemName(entity.getItemName());
            dto.setStock(entity.getStock());
            return dto;
        }

        public static InventoryItem toEntity(InventoryItemDTO dto) {
            if (dto == null) {
                return null;
            }

            InventoryItem entity = new InventoryItem();
            entity.setItemName(dto.getItemName());
            entity.setStock(dto.getStock());
            return entity;
        }
}
