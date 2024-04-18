package com.team1.model.repository;

import com.team1.model.entity.ExpirationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpirationRepository extends JpaRepository<ExpirationEntity , Integer> {

}
