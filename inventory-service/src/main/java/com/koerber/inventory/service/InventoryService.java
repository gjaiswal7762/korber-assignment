package com.koerber.inventory.service;

import com.koerber.inventory.dto.InventoryResponseDTO;
import com.koerber.inventory.dto.InventoryUpdateDTO;
import com.koerber.inventory.entity.InventoryBatch;
import com.koerber.inventory.handler.InventoryHandlerFactoryProvider;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InventoryService {

    private final InventoryHandlerFactoryProvider handlerFactoryProvider;

    public InventoryService(InventoryHandlerFactoryProvider handlerFactoryProvider) {
        this.handlerFactoryProvider = handlerFactoryProvider;
    }

    public InventoryResponseDTO getInventoryByProduct(Long productId) {
        return handlerFactoryProvider.getDefaultHandler().getInventoryByProduct(productId);
    }

    public void updateInventory(InventoryUpdateDTO updateDTO) {
        handlerFactoryProvider.getDefaultHandler().updateInventory(updateDTO);
    }

    public List<InventoryBatch> getAllBatchesForProduct(Long productId) {
        return handlerFactoryProvider.getDefaultHandler().getAllBatchesForProduct(productId);
    }
}
