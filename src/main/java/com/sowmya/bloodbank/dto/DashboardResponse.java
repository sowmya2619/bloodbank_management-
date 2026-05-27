package com.sowmya.bloodbank.dto;

public record DashboardResponse(
        long totalDonors,
        long totalHospitals,
        long pendingRequests,
        int totalAvailableUnits
) {
}
