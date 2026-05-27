package com.sowmya.bloodbank.service;

import com.sowmya.bloodbank.dto.InventoryUpdateRequest;
import com.sowmya.bloodbank.entity.BloodGroup;
import com.sowmya.bloodbank.entity.BloodInventory;
import com.sowmya.bloodbank.exception.InsufficientInventoryException;
import com.sowmya.bloodbank.exception.ResourceNotFoundException;
import com.sowmya.bloodbank.repository.BloodInventoryRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {

    private final BloodInventoryRepository inventoryRepository;

    public InventoryService(BloodInventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<BloodInventory> findAll() {
        return inventoryRepository.findAll();
    }

    @Transactional
    public BloodInventory addUnits(InventoryUpdateRequest request) {
        BloodInventory inventory = inventoryRepository.findByBloodGroup(request.bloodGroup())
                .orElseGet(() -> createEmptyInventory(request.bloodGroup()));
        inventory.setAvailableUnits(inventory.getAvailableUnits() + request.units());
        return inventoryRepository.save(inventory);
    }

    @Transactional
    public void reduceUnits(BloodGroup bloodGroup, int units) {
        BloodInventory inventory = inventoryRepository.findByBloodGroup(bloodGroup)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for " + bloodGroup));
        if (inventory.getAvailableUnits() < units) {
            throw new InsufficientInventoryException("Only " + inventory.getAvailableUnits()
                    + " units available for " + bloodGroup.getLabel());
        }
        inventory.setAvailableUnits(inventory.getAvailableUnits() - units);
        inventoryRepository.save(inventory);
    }

    private BloodInventory createEmptyInventory(BloodGroup bloodGroup) {
        BloodInventory inventory = new BloodInventory();
        inventory.setBloodGroup(bloodGroup);
        inventory.setAvailableUnits(0);
        return inventory;
    }
}
