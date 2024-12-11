package com.example.inventory_service.service;

import com.example.inventory_service.dto.InventoryResponse;
import com.example.inventory_service.entity.Inventory;
import com.example.inventory_service.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;

    @Transactional
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        log.info("Checking Inventory");
        List<Inventory> inventories = inventoryRepository.findBySkuCodeIn(skuCode);
        List<InventoryResponse> inventoryResponses = new ArrayList<>();
        for (Inventory inventory : inventories) {
            InventoryResponse response = new InventoryResponse();
            response.setSkuCode(inventory.getSkuCode());
            response.setInStock(inventory.getQuantity() > 0);
            inventoryResponses.add(response);
        }
        return inventoryResponses;
    }
}
