package com.sowmya.bloodbank.repository;

import com.sowmya.bloodbank.entity.BloodRequest;
import com.sowmya.bloodbank.entity.RequestStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodRequestRepository extends JpaRepository<BloodRequest, Long> {
    List<BloodRequest> findByStatus(RequestStatus status);
}
