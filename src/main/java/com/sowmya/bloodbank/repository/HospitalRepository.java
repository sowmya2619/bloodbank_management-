package com.sowmya.bloodbank.repository;

import com.sowmya.bloodbank.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
}
