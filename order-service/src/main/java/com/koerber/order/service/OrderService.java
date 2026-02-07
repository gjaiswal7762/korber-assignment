package com.koerber.order.service;

import com.koerber.order.dto.OrderRequestDTO;
import com.koerber.order.dto.OrderResponseDTO;
import com.koerber.order.dto.InventoryResponseDTO;
import com.koerber.order.dto.InventoryUpdateDTO;
import com.koerber.order.entity.Order;
import com.koerber.order.entity.OrderStatus;
import com.koerber.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    public OrderService(OrderRepository orderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }

    @Value("${inventory.service.url}")
    private String inventoryServiceUrl;

    public OrderResponseDTO placeOrder(OrderRequestDTO request) {
        try {
            String inventoryUrl = inventoryServiceUrl + "/" + request.getProductId();
            InventoryResponseDTO inventory = restTemplate.getForObject(inventoryUrl, InventoryResponseDTO.class);

            if (inventory == null || inventory.getBatches() == null || inventory.getBatches().isEmpty()) {
                return buildFailureResponse(request, "No inventory available for product");
            }

            long requiredQuantity = request.getQuantity();
            List<Long> reservedBatchIds = new ArrayList<>();
            List<Long> quantities = new ArrayList<>();
            long totalAvailable = 0;

            for (InventoryResponseDTO.BatchDTO batch : inventory.getBatches()) {
                if (requiredQuantity <= 0) break;

                totalAvailable += batch.getQuantity();
                long toReserve = Math.min(requiredQuantity, batch.getQuantity());
                
                reservedBatchIds.add(batch.getBatchId());
                quantities.add(toReserve);
                requiredQuantity -= toReserve;
            }

            if (requiredQuantity > 0) {
                return buildFailureResponse(request, "Insufficient inventory. Required: " + request.getQuantity() + ", Available: " + totalAvailable);
            }

            // Update inventory
            updateInventory(request.getProductId(), reservedBatchIds, quantities);

            Order order = Order.builder()
                    .productId(request.getProductId())
                    .productName(inventory.getProductName())
                    .quantity(request.getQuantity())
                    .status(OrderStatus.PLACED)
                    .orderDate(LocalDate.now())
                    .reservedBatchIds(reservedBatchIds)
                    .build();

            Order savedOrder = orderRepository.save(order);

            return OrderResponseDTO.builder()
                    .orderId(savedOrder.getOrderId())
                    .productId(savedOrder.getProductId())
                    .productName(savedOrder.getProductName())
                    .quantity(savedOrder.getQuantity())
                    .status(savedOrder.getStatus().toString())
                    .reservedFromBatchIds(reservedBatchIds)
                    .message("Order placed. Inventory reserved.")
                    .build();

        } catch (Exception e) {
            return buildFailureResponse(request, "Error communicating with inventory service: " + e.getMessage());
        }
    }

    private void updateInventory(Long productId, List<Long> batchIds, List<Long> quantities) {
        InventoryUpdateDTO updateRequest = new InventoryUpdateDTO(productId, batchIds, quantities);
        restTemplate.postForObject(inventoryServiceUrl + "/update", updateRequest, String.class);
    }

    private OrderResponseDTO buildFailureResponse(OrderRequestDTO request, String message) {
        return OrderResponseDTO.builder()
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .status(OrderStatus.FAILED.toString())
                .message(message)
                .build();
    }
}
