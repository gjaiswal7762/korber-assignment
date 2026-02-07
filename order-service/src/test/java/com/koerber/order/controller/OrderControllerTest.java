package com.koerber.order.controller;

import com.koerber.order.dto.InventoryResponseDTO;
import com.koerber.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    void testPlaceOrderRequest() throws Exception {
        String orderJson = "{\"productId\": 1001, \"quantity\": 10}";

        // Mock inventory service response
        InventoryResponseDTO.BatchDTO batch = new InventoryResponseDTO.BatchDTO(1L, 68L, "2026-06-25");
        InventoryResponseDTO inventoryResponse = new InventoryResponseDTO(1001L, "Laptop", 
                java.util.Arrays.asList(batch));

        when(restTemplate.getForObject("http://localhost:8081/inventory/1001", InventoryResponseDTO.class))
                .thenReturn(inventoryResponse);
        when(restTemplate.postForObject(anyString(), any(), eq(String.class)))
                .thenReturn("OK");

        mockMvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderJson))
                .andExpect(status().isCreated());
    }
}
