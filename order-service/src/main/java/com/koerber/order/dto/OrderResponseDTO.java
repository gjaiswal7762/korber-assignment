package com.koerber.order.dto;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {
    private Long orderId;
    private Long productId;
    private String productName;
    private Long quantity;
    private String status;
    private List<Long> reservedFromBatchIds;
    private String message;
}
