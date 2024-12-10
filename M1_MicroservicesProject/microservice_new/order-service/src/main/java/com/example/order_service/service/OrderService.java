package com.example.order_service.service;

import com.example.order_service.dto.OrderLineItemsDto;
import com.example.order_service.dto.OrderRequest;
import com.example.order_service.model.OrderLineItems;
import com.example.order_service.model.Orders;
import com.example.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service

public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest){
        Orders orders=new Orders();
       List<OrderLineItems>orderLineItems= orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();
       orders.setOrderLineItems(orderLineItems);

       orderRepository.save(orders);

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems=new OrderLineItems();
        orderLineItems.setId(orderLineItemsDto.getId());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
