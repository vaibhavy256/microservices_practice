package com.example.order_service.controller;

import com.example.order_service.dto.OrderRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/order")
public class OrderController {

    public String placeOrder(@RequestBody OrderRequest orderRequest){
        return "order placed successfully";
    }

}
