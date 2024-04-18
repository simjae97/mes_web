package com.team1.model.repository;

import com.team1.model.entity.MaterialInputEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialInputEntityRepository extends JpaRepository<MaterialInputEntity , Integer > {


}
