package com.koerber.order.dto;

import java.util.List;

public class InventoryResponseDTO {
    private Long productId;
    private String productName;
    private List<BatchDTO> batches;

    public InventoryResponseDTO() {}

    public InventoryResponseDTO(Long productId, String productName, List<BatchDTO> batches) {
        this.productId = productId;
        this.productName = productName;
        this.batches = batches;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<BatchDTO> getBatches() {
        return batches;
    }

    public void setBatches(List<BatchDTO> batches) {
        this.batches = batches;
    }

    public static class BatchDTO {
        private Long batchId;
        private Long quantity;
        private String expiryDate;

        public BatchDTO() {}

        public BatchDTO(Long batchId, Long quantity, String expiryDate) {
            this.batchId = batchId;
            this.quantity = quantity;
            this.expiryDate = expiryDate;
        }

        public Long getBatchId() {
            return batchId;
        }

        public void setBatchId(Long batchId) {
            this.batchId = batchId;
        }

        public Long getQuantity() {
            return quantity;
        }

        public void setQuantity(Long quantity) {
            this.quantity = quantity;
        }

        public String getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(String expiryDate) {
            this.expiryDate = expiryDate;
        }
    }
}
