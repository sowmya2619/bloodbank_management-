package com.sowmya.bloodbank.controller;

import com.sowmya.bloodbank.entity.Hospital;
import com.sowmya.bloodbank.service.HospitalService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hospitals")
public class HospitalController {

    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping
    public List<Hospital> findAll() {
        return hospitalService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Hospital create(@Valid @RequestBody Hospital hospital) {
        return hospitalService.create(hospital);
    }
}
