package com.koerber.inventory.handler;

import com.koerber.inventory.dto.InventoryResponseDTO;
import com.koerber.inventory.dto.InventoryUpdateDTO;
import com.koerber.inventory.entity.InventoryBatch;
import java.util.List;

public interface InventoryHandler {
    InventoryResponseDTO getInventoryByProduct(Long productId);
    void updateInventory(InventoryUpdateDTO updateDTO);
    List<InventoryBatch> getAllBatchesForProduct(Long productId);
}
