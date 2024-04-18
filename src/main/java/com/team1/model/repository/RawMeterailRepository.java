package com.team1.model.repository;

import com.team1.model.entity.RawMaterialEntity;
import com.team1.model.entity.RawMaterialLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RawMeterailRepository extends JpaRepository<RawMaterialEntity,Integer> {


    RawMaterialEntity findByRmname(String Rmname);


}
