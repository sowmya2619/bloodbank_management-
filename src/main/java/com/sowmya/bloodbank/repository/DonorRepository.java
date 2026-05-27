package com.sowmya.bloodbank.repository;

import com.sowmya.bloodbank.entity.BloodGroup;
import com.sowmya.bloodbank.entity.Donor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonorRepository extends JpaRepository<Donor, Long> {
    List<Donor> findByBloodGroupAndEligibleTrue(BloodGroup bloodGroup);
}
