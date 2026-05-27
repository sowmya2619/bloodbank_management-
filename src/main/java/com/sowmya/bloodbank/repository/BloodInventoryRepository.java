package com.sowmya.bloodbank.repository;

import com.sowmya.bloodbank.entity.BloodGroup;
import com.sowmya.bloodbank.entity.BloodInventory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodInventoryRepository extends JpaRepository<BloodInventory, Long> {
    Optional<BloodInventory> findByBloodGroup(BloodGroup bloodGroup);
}
