package com.koerber.inventory.dto;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryUpdateDTO {
    private Long productId;
    private List<Long> batchIds;
    private List<Long> quantities;
}
