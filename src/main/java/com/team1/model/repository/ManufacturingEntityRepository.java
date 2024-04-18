package com.team1.model.repository;

import com.team1.model.entity.ManufacturingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ManufacturingEntityRepository extends JpaRepository<ManufacturingEntity , Integer> {

    @Query(value = "select * from manufacturing where mipno = :mipno",nativeQuery = true)
    Optional<ManufacturingEntity> findByMipno(int mipno);


}
