package com.systems.inventoryService.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.systems.inventoryService.dto.InventoryItemDecreaseRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.systems.inventoryService.dto.InventoryItemDTO;
import com.systems.inventoryService.entity.InventoryItem;
import com.systems.inventoryService.repo.InventoryRepository;
import com.systems.inventoryService.service.impl.InventoryServiceImpl;

public class InventoryServiceImplTest {

	@InjectMocks
	private InventoryServiceImpl inventoryService;

	@Mock
	private InventoryRepository inventoryRepository;

	private InventoryItem item;

	private InventoryItemDecreaseRequest inventoryItemDecreaseRequest;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		item = new InventoryItem();
		item.setId(1L);
		item.setItemName("Widget");
		item.setStock(10);

		inventoryItemDecreaseRequest=new InventoryItemDecreaseRequest();
		inventoryItemDecreaseRequest.setItemName("Widget");
		inventoryItemDecreaseRequest.setQuantity(5);
	}

	@Test
	public void testGetItem_ItemExists() {
		when(inventoryRepository.findByItemName("Widget")).thenReturn(Optional.of(item));

		Optional<InventoryItemDTO> result = inventoryService.getItem("Widget");

		assertTrue(result.isPresent());
		assertEquals(item.getItemName(), result.get().getItemName());
	}

	@Test
	public void testGetItem_ItemNotFound() {
		when(inventoryRepository.findByItemName("NonExistentItem")).thenReturn(Optional.empty());

		Exception exception = assertThrows(RuntimeException.class, () -> {
			inventoryService.getItem("NonExistentItem");
		});

		assertEquals("No item found = : NonExistentItem", exception.getMessage());
	}

	@Test
	public void testDecreaseStock_Success() {
		when(inventoryRepository.findByItemName("Widget")).thenReturn(Optional.of(item));

		inventoryService.decreaseStock(inventoryItemDecreaseRequest);

		assertEquals(5, item.getStock());
		verify(inventoryRepository).save(item);
	}

	@Test
	public void testDecreaseStock_ItemNotFound() {
		when(inventoryRepository.findByItemName("NonExistentItem")).thenReturn(Optional.empty());

		inventoryItemDecreaseRequest.setItemName("NonExistentItem");
		Exception exception = assertThrows(RuntimeException.class, () -> {
			inventoryService.decreaseStock(inventoryItemDecreaseRequest);
		});

		assertEquals("Item not found", exception.getMessage());
	}

	@Test
	public void testDecreaseStock_InsufficientStock() {
		item.setStock(2);
		when(inventoryRepository.findByItemName("Widget")).thenReturn(Optional.of(item));

		Exception exception = assertThrows(RuntimeException.class, () -> {
			inventoryService.decreaseStock(inventoryItemDecreaseRequest);
		});

		assertEquals("Insufficient stock for item: Widget", exception.getMessage());
		verify(inventoryRepository, never()).save(item);
	}

}
