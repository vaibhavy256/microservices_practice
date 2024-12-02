package com.example.productservice.product_service.service;

import com.example.productservice.product_service.dto.ProductRequest;
import com.example.productservice.product_service.model.Product;
import com.example.productservice.product_service.reposioty.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public Product createProduct(ProductRequest productRequest){
            return productRepository.save(new Product
                (productRequest.getName(),productRequest.getDescription(),productRequest.getPrice()));

    }
}
