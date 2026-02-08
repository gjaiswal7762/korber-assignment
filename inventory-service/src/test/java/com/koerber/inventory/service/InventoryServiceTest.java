package com.koerber.inventory.service;

import com.koerber.inventory.dto.BatchDTO;
import com.koerber.inventory.dto.InventoryResponseDTO;
import com.koerber.inventory.dto.InventoryUpdateDTO;
import com.koerber.inventory.entity.InventoryBatch;
import com.koerber.inventory.handler.InventoryHandler;
import com.koerber.inventory.handler.InventoryHandlerFactoryProvider;
import com.koerber.inventory.handler.StandardInventoryHandler;
import com.koerber.inventory.repository.InventoryBatchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private InventoryBatchRepository inventoryBatchRepository;

    @Mock
    private InventoryHandlerFactoryProvider handlerFactoryProvider;

    @InjectMocks
    private InventoryService inventoryService;

    private InventoryHandler standardInventoryHandler;

    @BeforeEach
    void setUp() {
        standardInventoryHandler = new StandardInventoryHandler(inventoryBatchRepository);
        when(handlerFactoryProvider.getDefaultHandler()).thenReturn(standardInventoryHandler);
    }

    @Test
    void testGetInventoryByProduct() {
        Long productId = 1001L;
        InventoryBatch batch1 = InventoryBatch.builder()
                .batchId(1L)
                .productId(productId)
                .productName("Laptop")
                .quantity(50L)
                .expiryDate(LocalDate.of(2026, 6, 25))
                .build();

        when(inventoryBatchRepository.findByProductIdOrderByExpiryDateAsc(productId))
                .thenReturn(Arrays.asList(batch1));

        InventoryResponseDTO response = inventoryService.getInventoryByProduct(productId);

        assertNotNull(response);
        assertEquals(productId, response.getProductId());
        assertEquals("Laptop", response.getProductName());
        assertEquals(1, response.getBatches().size());
        assertEquals(50L, response.getBatches().get(0).getQuantity());

        verify(inventoryBatchRepository).findByProductIdOrderByExpiryDateAsc(productId);
    }

    @Test
    void testUpdateInventory() {
        InventoryUpdateDTO updateDTO = InventoryUpdateDTO.builder()
                .productId(1001L)
                .batchIds(Arrays.asList(1L))
                .quantities(Arrays.asList(10L))
                .build();

        InventoryBatch batch = InventoryBatch.builder()
                .batchId(1L)
                .productId(1001L)
                .quantity(50L)
                .build();

        when(inventoryBatchRepository.findByBatchId(1L)).thenReturn(batch);

        inventoryService.updateInventory(updateDTO);

        verify(inventoryBatchRepository).findByBatchId(1L);
        verify(inventoryBatchRepository).save(batch);
        assertEquals(40L, batch.getQuantity());
    }

    @Test
    void testGetAllBatchesForProduct() {
        Long productId = 1002L;
        InventoryBatch batch1 = InventoryBatch.builder()
                .batchId(9L)
                .productId(productId)
                .quantity(29L)
                .expiryDate(LocalDate.of(2026, 5, 31))
                .build();
        InventoryBatch batch2 = InventoryBatch.builder()
                .batchId(10L)
                .productId(productId)
                .quantity(83L)
                .expiryDate(LocalDate.of(2026, 11, 15))
                .build();

        when(inventoryBatchRepository.findByProductIdOrderByExpiryDateAsc(productId))
                .thenReturn(Arrays.asList(batch1, batch2));

        List<InventoryBatch> batches = inventoryService.getAllBatchesForProduct(productId);

        assertEquals(2, batches.size());
        assertEquals(LocalDate.of(2026, 5, 31), batches.get(0).getExpiryDate());
        assertEquals(LocalDate.of(2026, 11, 15), batches.get(1).getExpiryDate());
    }
}
