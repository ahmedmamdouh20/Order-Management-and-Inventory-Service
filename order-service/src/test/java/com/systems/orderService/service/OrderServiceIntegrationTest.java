package com.systems.orderService.service;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.systems.orderService.dto.OrderDTO;
import com.systems.orderService.entity.InventoryItem;
import com.systems.orderService.entity.Order;
import com.systems.orderService.repo.OrderRepository;
import com.systems.orderService.service.impl.OrderServiceImpl;
@SpringBootTest
public class OrderServiceIntegrationTest {
	
	@Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    private MockRestServiceServer mockServer;

    @Value("${inventory-service.url}")
    private String inventoryServiceUrl;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private OrderDTO orderDTO;
    private InventoryItem inventoryItem;

    @BeforeEach
    void setUp() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        mockServer = MockRestServiceServer.createServer(restTemplate);

        ReflectionTestUtils.setField(orderService, "restTemplate", restTemplate);

        orderDTO = new OrderDTO();
        orderDTO.setItem("Widget");
        orderDTO.setQuantity(5);

        inventoryItem = new InventoryItem();
        inventoryItem.setItemName("Widget");
        inventoryItem.setStock(10);

        orderRepository.deleteAll();
    }

    @Test
    void testPlaceOrder_Success() throws Exception {
        mockServer.expect(requestTo(inventoryServiceUrl + "Widget"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(
                        objectMapper.writeValueAsString(inventoryItem),
                        MediaType.APPLICATION_JSON));

        mockServer.expect(requestTo(inventoryServiceUrl + "decrease"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().json("{\"itemName\":\"Widget\",\"quantity\":5}"))
                .andRespond(withSuccess());

        Order savedOrder = orderService.placeOrder(orderDTO);

        mockServer.verify();
        assertNotNull(savedOrder.getId());
        assertEquals("Widget", savedOrder.getItem());
        assertEquals(1, orderRepository.count());
    }

    @Test
    void testPlaceOrder_InsufficientStock() throws Exception {
        inventoryItem.setStock(3);

        mockServer.expect(requestTo(inventoryServiceUrl + "Widget"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(
                        objectMapper.writeValueAsString(inventoryItem),
                        MediaType.APPLICATION_JSON));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> orderService.placeOrder(orderDTO));

        assertEquals("Insufficient stock for item: Widget", ex.getMessage());
        assertEquals(0, orderRepository.count());
    }

    @Test
    void testPlaceOrder_ItemNotFound() {
        mockServer.expect(requestTo(inventoryServiceUrl + "Widget"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> orderService.placeOrder(orderDTO));

        System.out.println(ex.getMessage());
        assertTrue(ex.getMessage().contains("Insufficient stock"));
        assertEquals(0, orderRepository.count());
    }

    @Test
    void testGetAllOrders() throws Exception {
        Order order = new Order();
        order.setItem("Widget");
        order.setQuantity(5);
        orderRepository.save(order);
        List<OrderDTO> results = orderService.getAllOrders();

        assertEquals(1, results.size());
        assertEquals("Widget", results.get(0).getItem());
    }

}
