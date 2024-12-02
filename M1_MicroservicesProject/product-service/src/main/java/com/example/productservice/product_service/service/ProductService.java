package com.example.productservice.product_service.service;

import com.example.productservice.product_service.dto.ProductRequest;
import com.example.productservice.product_service.dto.ProductResponse;
import com.example.productservice.product_service.model.Product;
import com.example.productservice.product_service.reposioty.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public Product createProduct(ProductRequest productRequest){
            Product newProduct= productRepository.save(new Product
                (productRequest.getName(),productRequest.getDescription(),productRequest.getPrice()));
            log.info("Product {} is saved",productRequest.getName());
            return newProduct;
    }

    public List<ProductResponse> getAllProdcuts() {
        List<Product> products=productRepository.findAll();
        return products.stream().map(this::convertProductResponse).toList();
    }

    public ProductResponse convertProductResponse(Product product) {
        ProductResponse productResponse=new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        return productResponse;
    }
}
