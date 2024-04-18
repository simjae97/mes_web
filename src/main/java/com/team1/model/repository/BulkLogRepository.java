package com.team1.model.repository;

import com.team1.model.entity.BulkLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BulkLogRepository extends JpaRepository<BulkLogEntity , Integer> {
}
