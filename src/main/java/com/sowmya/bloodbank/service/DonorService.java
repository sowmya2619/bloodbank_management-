package com.sowmya.bloodbank.service;

import com.sowmya.bloodbank.entity.BloodGroup;
import com.sowmya.bloodbank.entity.Donor;
import com.sowmya.bloodbank.exception.ResourceNotFoundException;
import com.sowmya.bloodbank.repository.DonorRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DonorService {

    private final DonorRepository donorRepository;

    public DonorService(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    public List<Donor> findAll() {
        return donorRepository.findAll();
    }

    public List<Donor> findEligibleByBloodGroup(BloodGroup bloodGroup) {
        return donorRepository.findByBloodGroupAndEligibleTrue(bloodGroup);
    }

    public Donor create(Donor donor) {
        return donorRepository.save(donor);
    }

    public Donor update(Long id, Donor updatedDonor) {
        Donor donor = donorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donor not found with id " + id));
        donor.setFullName(updatedDonor.getFullName());
        donor.setAge(updatedDonor.getAge());
        donor.setBloodGroup(updatedDonor.getBloodGroup());
        donor.setPhone(updatedDonor.getPhone());
        donor.setEmail(updatedDonor.getEmail());
        donor.setCity(updatedDonor.getCity());
        donor.setLastDonationDate(updatedDonor.getLastDonationDate());
        donor.setEligible(updatedDonor.isEligible());
        return donorRepository.save(donor);
    }

    public void delete(Long id) {
        if (!donorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Donor not found with id " + id);
        }
        donorRepository.deleteById(id);
    }
}
