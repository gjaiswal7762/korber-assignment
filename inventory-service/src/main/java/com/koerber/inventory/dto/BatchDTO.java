package com.koerber.inventory.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchDTO {
    private Long batchId;
    private Long quantity;
    private LocalDate expiryDate;
}
