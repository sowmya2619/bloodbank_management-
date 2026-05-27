package com.sowmya.bloodbank.controller;

import com.sowmya.bloodbank.dto.BloodRequestCreateRequest;
import com.sowmya.bloodbank.entity.BloodRequest;
import com.sowmya.bloodbank.service.BloodRequestService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/requests")
public class BloodRequestController {

    private final BloodRequestService requestService;

    public BloodRequestController(BloodRequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping
    public List<BloodRequest> findAll() {
        return requestService.findAll();
    }

    @GetMapping("/pending")
    public List<BloodRequest> findPending() {
        return requestService.findPending();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BloodRequest create(@Valid @RequestBody BloodRequestCreateRequest request) {
        return requestService.create(request);
    }

    @PatchMapping("/{id}/approve")
    public BloodRequest approve(@PathVariable Long id) {
        return requestService.approve(id);
    }

    @PatchMapping("/{id}/reject")
    public BloodRequest reject(@PathVariable Long id) {
        return requestService.reject(id);
    }
}
