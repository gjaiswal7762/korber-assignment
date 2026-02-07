package com.koerber.inventory.handler;

import com.koerber.inventory.dto.BatchDTO;
import com.koerber.inventory.dto.InventoryResponseDTO;
import com.koerber.inventory.dto.InventoryUpdateDTO;
import com.koerber.inventory.entity.InventoryBatch;
import com.koerber.inventory.repository.InventoryBatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StandardInventoryHandler implements InventoryHandler {

    private final InventoryBatchRepository inventoryBatchRepository;

    public StandardInventoryHandler(InventoryBatchRepository inventoryBatchRepository) {
        this.inventoryBatchRepository = inventoryBatchRepository;
    }

    @Override
    public InventoryResponseDTO getInventoryByProduct(Long productId) {
        List<InventoryBatch> batches = inventoryBatchRepository.findByProductIdOrderByExpiryDateAsc(productId);
        
        if (batches.isEmpty()) {
            return null;
        }

        String productName = batches.get(0).getProductName();
        List<BatchDTO> batchDTOs = batches.stream()
                .map(b -> BatchDTO.builder()
                        .batchId(b.getBatchId())
                        .quantity(b.getQuantity())
                        .expiryDate(b.getExpiryDate())
                        .build())
                .collect(Collectors.toList());

        return InventoryResponseDTO.builder()
                .productId(productId)
                .productName(productName)
                .batches(batchDTOs)
                .build();
    }

    @Override
    public void updateInventory(InventoryUpdateDTO updateDTO) {
        for (int i = 0; i < updateDTO.getBatchIds().size(); i++) {
            Long batchId = updateDTO.getBatchIds().get(i);
            Long quantityToReduce = updateDTO.getQuantities().get(i);

            InventoryBatch batch = inventoryBatchRepository.findByBatchId(batchId);
            if (batch != null) {
                batch.setQuantity(batch.getQuantity() - quantityToReduce);
                inventoryBatchRepository.save(batch);
            }
        }
    }

    @Override
    public List<InventoryBatch> getAllBatchesForProduct(Long productId) {
        return inventoryBatchRepository.findByProductIdOrderByExpiryDateAsc(productId);
    }
}
