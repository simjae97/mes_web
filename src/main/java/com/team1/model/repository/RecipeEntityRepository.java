package com.team1.model.repository;

import com.team1.model.entity.ProductEntity;
import com.team1.model.entity.RawMaterialEntity;
import com.team1.model.entity.RawMaterialLogEntity;
import com.team1.model.entity.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeEntityRepository extends JpaRepository<RecipeEntity , Integer> {

    // pno 로 원재료 뭐 들어가는지 가져오기
    @Query(value = "select * from recipe where pno =:pno ",nativeQuery = true)
    List<RecipeEntity> findByPnoSql(int pno);




}
