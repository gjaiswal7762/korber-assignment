package com.koerber.order.dto;

import java.util.List;

public class InventoryUpdateDTO {
    private Long productId;
    private List<Long> batchIds;
    private List<Long> quantities;

    public InventoryUpdateDTO() {}

    public InventoryUpdateDTO(Long productId, List<Long> batchIds, List<Long> quantities) {
        this.productId = productId;
        this.batchIds = batchIds;
        this.quantities = quantities;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public List<Long> getBatchIds() {
        return batchIds;
    }

    public void setBatchIds(List<Long> batchIds) {
        this.batchIds = batchIds;
    }

    public List<Long> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<Long> quantities) {
        this.quantities = quantities;
    }
}
