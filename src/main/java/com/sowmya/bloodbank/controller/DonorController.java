package com.sowmya.bloodbank.controller;

import com.sowmya.bloodbank.entity.BloodGroup;
import com.sowmya.bloodbank.entity.Donor;
import com.sowmya.bloodbank.service.DonorService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/donors")
public class DonorController {

    private final DonorService donorService;

    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    @GetMapping
    public List<Donor> findAll() {
        return donorService.findAll();
    }

    @GetMapping("/eligible")
    public List<Donor> findEligible(@RequestParam BloodGroup bloodGroup) {
        return donorService.findEligibleByBloodGroup(bloodGroup);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Donor create(@Valid @RequestBody Donor donor) {
        return donorService.create(donor);
    }

    @PutMapping("/{id}")
    public Donor update(@PathVariable Long id, @Valid @RequestBody Donor donor) {
        return donorService.update(id, donor);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        donorService.delete(id);
    }
}
