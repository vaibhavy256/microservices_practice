package com.example.productservice.product_service.controller;

import com.example.productservice.product_service.dto.ProductRequest;
import com.example.productservice.product_service.dto.ProductResponse;
import com.example.productservice.product_service.model.Product;
import com.example.productservice.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping("/add")
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Map<String,String>>  createProduct(@RequestBody ProductRequest productRequest){
        Product newProduct=productService.createProduct(productRequest);
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Product successfully created");

        //return ResponseEntity.ok(response,HttpStatus.CREATED);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProdcuts();
    }


}
