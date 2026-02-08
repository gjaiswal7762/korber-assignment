package com.koerber.inventory.repository;

import com.koerber.inventory.entity.InventoryBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InventoryBatchRepository extends JpaRepository<InventoryBatch, Long> {
    List<InventoryBatch> findByProductIdOrderByExpiryDateAsc(Long productId);
    List<InventoryBatch> findByProductIdOrderByExpiryDateDesc(Long productId);
    InventoryBatch findByBatchId(Long batchId);
}
