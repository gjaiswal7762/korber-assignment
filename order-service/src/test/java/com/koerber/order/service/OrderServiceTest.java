package com.koerber.order.service;

import com.koerber.order.dto.OrderRequestDTO;
import com.koerber.order.dto.OrderResponseDTO;
import com.koerber.order.dto.InventoryResponseDTO;
import com.koerber.order.dto.InventoryResponseDTO;
import com.koerber.order.entity.Order;
import com.koerber.order.entity.OrderStatus;
import com.koerber.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(orderService, "inventoryServiceUrl", "http://localhost:8081/inventory");
    }

    @Test
    void testPlaceOrderSuccess() {
        OrderRequestDTO request = OrderRequestDTO.builder()
                .productId(1001L)
                .quantity(10L)
                .build();

        InventoryResponseDTO inventoryResponse = createInventoryResponseDTO();
        when(restTemplate.getForObject("http://localhost:8081/inventory/1001", InventoryResponseDTO.class))
                .thenReturn(inventoryResponse);

        Order savedOrder = Order.builder()
                .orderId(11L)
                .productId(1001L)
                .productName("Laptop")
                .quantity(10L)
                .status(OrderStatus.PLACED)
                .orderDate(LocalDate.now())
                .reservedBatchIds(Arrays.asList(1L))
                .build();

        when(orderRepository.save(any(Order.class)))
                .thenReturn(savedOrder);

        OrderResponseDTO response = orderService.placeOrder(request);

        assertNotNull(response);
        assertEquals(11L, response.getOrderId());
        assertEquals("PLACED", response.getStatus());
        assertEquals("Order placed. Inventory reserved.", response.getMessage());
        assertEquals(Arrays.asList(1L), response.getReservedFromBatchIds());

        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void testPlaceOrderInsufficientInventory() {
        OrderRequestDTO request = OrderRequestDTO.builder()
                .productId(1001L)
                .quantity(1000L)
                .build();

        InventoryResponseDTO inventoryResponse = createInventoryResponseDTO();
        when(restTemplate.getForObject("http://localhost:8081/inventory/1001", InventoryResponseDTO.class))
                .thenReturn(inventoryResponse);

        OrderResponseDTO response = orderService.placeOrder(request);

        assertNotNull(response);
        assertEquals("FAILED", response.getStatus());
        assertTrue(response.getMessage().contains("Insufficient inventory"));
    }

    @Test
    void testPlaceOrderProductNotFound() {
        OrderRequestDTO request = OrderRequestDTO.builder()
                .productId(9999L)
                .quantity(10L)
                .build();

        when(restTemplate.getForObject("http://localhost:8081/inventory/9999", InventoryResponseDTO.class))
                .thenReturn(null);

        OrderResponseDTO response = orderService.placeOrder(request);

        assertNotNull(response);
        assertEquals("FAILED", response.getStatus());
        assertEquals("No inventory available for product", response.getMessage());
    }

    private InventoryResponseDTO createInventoryResponseDTO() {
        List<InventoryResponseDTO.BatchDTO> batches = new ArrayList<>();
        batches.add(new InventoryResponseDTO.BatchDTO(1L, 68L, "2026-06-25"));
        return new InventoryResponseDTO(1001L, "Laptop", batches);
    }
}
