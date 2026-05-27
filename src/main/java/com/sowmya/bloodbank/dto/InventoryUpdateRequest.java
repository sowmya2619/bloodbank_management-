package com.sowmya.bloodbank.dto;

import com.sowmya.bloodbank.entity.BloodGroup;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record InventoryUpdateRequest(
        @NotNull BloodGroup bloodGroup,
        @Min(1) int units
) {
}
