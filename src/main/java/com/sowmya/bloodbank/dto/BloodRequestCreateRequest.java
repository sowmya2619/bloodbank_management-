package com.sowmya.bloodbank.dto;

import com.sowmya.bloodbank.entity.BloodGroup;
import com.sowmya.bloodbank.entity.RequestPriority;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BloodRequestCreateRequest(
        @NotNull Long hospitalId,
        @NotBlank String patientName,
        @NotNull BloodGroup bloodGroup,
        @Min(1) int requiredUnits,
        @NotNull RequestPriority priority
) {
}
