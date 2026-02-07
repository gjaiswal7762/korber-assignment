package com.koerber.inventory.dto;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryResponseDTO {
    private Long productId;
    private String productName;
    private List<BatchDTO> batches;
}
