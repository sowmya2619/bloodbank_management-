package com.sowmya.bloodbank.service;

import com.sowmya.bloodbank.dto.DashboardResponse;
import com.sowmya.bloodbank.entity.RequestStatus;
import com.sowmya.bloodbank.repository.BloodInventoryRepository;
import com.sowmya.bloodbank.repository.BloodRequestRepository;
import com.sowmya.bloodbank.repository.DonorRepository;
import com.sowmya.bloodbank.repository.HospitalRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final DonorRepository donorRepository;
    private final HospitalRepository hospitalRepository;
    private final BloodRequestRepository requestRepository;
    private final BloodInventoryRepository inventoryRepository;

    public DashboardService(
            DonorRepository donorRepository,
            HospitalRepository hospitalRepository,
            BloodRequestRepository requestRepository,
            BloodInventoryRepository inventoryRepository
    ) {
        this.donorRepository = donorRepository;
        this.hospitalRepository = hospitalRepository;
        this.requestRepository = requestRepository;
        this.inventoryRepository = inventoryRepository;
    }

    public DashboardResponse getSummary() {
        int availableUnits = inventoryRepository.findAll().stream()
                .mapToInt(inventory -> inventory.getAvailableUnits())
                .sum();
        return new DashboardResponse(
                donorRepository.count(),
                hospitalRepository.count(),
                requestRepository.findByStatus(RequestStatus.PENDING).size(),
                availableUnits
        );
    }
}
