package com.koerber.inventory.controller;

import com.koerber.inventory.dto.InventoryResponseDTO;
import com.koerber.inventory.dto.InventoryUpdateDTO;
import com.koerber.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<InventoryResponseDTO> getInventory(@PathVariable Long productId) {
        InventoryResponseDTO response = inventoryService.getInventoryByProduct(productId);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateInventory(@RequestBody InventoryUpdateDTO updateDTO) {
        inventoryService.updateInventory(updateDTO);
        return ResponseEntity.ok("Inventory updated successfully");
    }
}
