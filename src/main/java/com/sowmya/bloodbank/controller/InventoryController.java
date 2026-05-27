package com.sowmya.bloodbank.controller;

import com.sowmya.bloodbank.dto.InventoryUpdateRequest;
import com.sowmya.bloodbank.entity.BloodInventory;
import com.sowmya.bloodbank.service.InventoryService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public List<BloodInventory> findAll() {
        return inventoryService.findAll();
    }

    @PatchMapping
    public BloodInventory addUnits(@Valid @RequestBody InventoryUpdateRequest request) {
        return inventoryService.addUnits(request);
    }
}
