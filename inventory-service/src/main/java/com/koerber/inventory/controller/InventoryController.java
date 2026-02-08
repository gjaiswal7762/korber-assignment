package com.koerber.inventory.controller;

import com.koerber.inventory.dto.InventoryResponseDTO;
import com.koerber.inventory.dto.InventoryUpdateDTO;
import com.koerber.inventory.service.InventoryService;
import com.koerber.inventory.handler.InventoryHandlerFactoryProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;
    private final InventoryHandlerFactoryProvider handlerFactoryProvider;

    public InventoryController(InventoryService inventoryService, 
                             InventoryHandlerFactoryProvider handlerFactoryProvider) {
        this.inventoryService = inventoryService;
        this.handlerFactoryProvider = handlerFactoryProvider;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getInventory(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "STANDARD") String strategy) {
        
        try {
            InventoryResponseDTO response = handlerFactoryProvider
                    .getHandler(strategy)
                    .getInventoryByProduct(productId);
            
            if (response == null) {
                return ResponseEntity.status(404).body("Product not found in the inventory. Please try different product ID.");
            }
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid strategy");
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateInventory(@RequestBody InventoryUpdateDTO updateDTO) {
        inventoryService.updateInventory(updateDTO);
        return ResponseEntity.ok("Inventory updated successfully");
    }

    @GetMapping("/strategies")
    public ResponseEntity<?> getAvailableStrategies() {
        return ResponseEntity.ok(handlerFactoryProvider.getAvailableHandlerTypes());
    }
}
