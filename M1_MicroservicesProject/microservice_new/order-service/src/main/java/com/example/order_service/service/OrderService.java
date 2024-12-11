package com.example.order_service.service;

import com.example.order_service.dto.InventoryResponse;
import com.example.order_service.dto.OrderLineItemsDto;
import com.example.order_service.dto.OrderRequest;
import com.example.order_service.model.OrderLineItems;
import com.example.order_service.model.Orders;
import com.example.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    RestTemplate restTemplate;

    public void placeOrder(OrderRequest orderRequest) {
        Orders orders = new Orders();
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();
        orders.setOrderLineItems(orderLineItems);

        //used to extract only the skuCode values from the list of OrderLineItems objects in the orders object.
        List<String> skuCodes = orders.getOrderLineItems().stream()
                .map(OrderLineItems::getSkuCode).toList();
        log.info("Checking Inventory");
        System.out.println("http://localhost:8084/api/inventory?skuCode="
                + String.join(",", skuCodes));
        InventoryResponse[] inventoryResponses = restTemplate
                .getForObject("http://localhost:8084/api/inventory/"
                        + String.join(",", skuCodes), InventoryResponse[].class);

        boolean allProductsInStock = Arrays.stream(inventoryResponses)
                .allMatch(InventoryResponse::isInStock);

        if (allProductsInStock) {
            orderRepository.save(orders);
        } else {
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }


    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setId(orderLineItemsDto.getId());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
