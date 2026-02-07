package com.koerber.inventory.service;

import com.koerber.inventory.dto.InventoryResponseDTO;
import com.koerber.inventory.dto.InventoryUpdateDTO;
import com.koerber.inventory.entity.InventoryBatch;
import com.koerber.inventory.handler.InventoryHandlerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InventoryService {

    private final InventoryHandlerFactory handlerFactory;

    public InventoryService(com.koerber.inventory.handler.InventoryHandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
    }

    public InventoryResponseDTO getInventoryByProduct(Long productId) {
        return handlerFactory.getDefaultHandler().getInventoryByProduct(productId);
    }

    public void updateInventory(InventoryUpdateDTO updateDTO) {
        handlerFactory.getDefaultHandler().updateInventory(updateDTO);
    }

    public List<InventoryBatch> getAllBatchesForProduct(Long productId) {
        return handlerFactory.getDefaultHandler().getAllBatchesForProduct(productId);
    }
}
