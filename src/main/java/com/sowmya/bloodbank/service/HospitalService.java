package com.sowmya.bloodbank.service;

import com.sowmya.bloodbank.entity.Hospital;
import com.sowmya.bloodbank.exception.ResourceNotFoundException;
import com.sowmya.bloodbank.repository.HospitalRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    public HospitalService(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    public List<Hospital> findAll() {
        return hospitalRepository.findAll();
    }

    public Hospital create(Hospital hospital) {
        return hospitalRepository.save(hospital);
    }

    public Hospital getById(Long id) {
        return hospitalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hospital not found with id " + id));
    }
}
