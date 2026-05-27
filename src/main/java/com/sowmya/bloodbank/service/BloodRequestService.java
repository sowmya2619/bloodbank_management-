package com.sowmya.bloodbank.service;

import com.sowmya.bloodbank.dto.BloodRequestCreateRequest;
import com.sowmya.bloodbank.entity.BloodRequest;
import com.sowmya.bloodbank.entity.Hospital;
import com.sowmya.bloodbank.entity.RequestStatus;
import com.sowmya.bloodbank.exception.ResourceNotFoundException;
import com.sowmya.bloodbank.repository.BloodRequestRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BloodRequestService {

    private final BloodRequestRepository requestRepository;
    private final HospitalService hospitalService;
    private final InventoryService inventoryService;

    public BloodRequestService(
            BloodRequestRepository requestRepository,
            HospitalService hospitalService,
            InventoryService inventoryService
    ) {
        this.requestRepository = requestRepository;
        this.hospitalService = hospitalService;
        this.inventoryService = inventoryService;
    }

    public List<BloodRequest> findAll() {
        return requestRepository.findAll();
    }

    public List<BloodRequest> findPending() {
        return requestRepository.findByStatus(RequestStatus.PENDING);
    }

    public BloodRequest create(BloodRequestCreateRequest request) {
        Hospital hospital = hospitalService.getById(request.hospitalId());
        BloodRequest bloodRequest = new BloodRequest();
        bloodRequest.setHospital(hospital);
        bloodRequest.setPatientName(request.patientName());
        bloodRequest.setBloodGroup(request.bloodGroup());
        bloodRequest.setRequiredUnits(request.requiredUnits());
        bloodRequest.setPriority(request.priority());
        bloodRequest.setStatus(RequestStatus.PENDING);
        return requestRepository.save(bloodRequest);
    }

    @Transactional
    public BloodRequest approve(Long id) {
        BloodRequest bloodRequest = requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blood request not found with id " + id));
        if (bloodRequest.getStatus() == RequestStatus.APPROVED) {
            return bloodRequest;
        }
        inventoryService.reduceUnits(bloodRequest.getBloodGroup(), bloodRequest.getRequiredUnits());
        bloodRequest.setStatus(RequestStatus.APPROVED);
        return requestRepository.save(bloodRequest);
    }

    public BloodRequest reject(Long id) {
        BloodRequest bloodRequest = requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blood request not found with id " + id));
        bloodRequest.setStatus(RequestStatus.REJECTED);
        return requestRepository.save(bloodRequest);
    }
}
