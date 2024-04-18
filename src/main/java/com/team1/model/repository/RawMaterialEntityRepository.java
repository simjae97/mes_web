package com.team1.model.repository;

import com.team1.model.entity.RawMaterialEntity;
import com.team1.model.entity.RawMaterialLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RawMaterialEntityRepository extends JpaRepository<RawMaterialEntity , Integer> {

    // pno 로 원재료 뭐 들어가는지 가져오기
    @Query(value = "select rawmaterial.rmno , rmname , rawmaterial.cdate , rawmaterial.udate " +
            "from rawmaterial inner join recipe on recipe.rmno = rawmaterial.rmno " +
            "inner join workplan on recipe.pno = workplan.pno " +
            "where workplan.pno =:pno",nativeQuery = true)
    List<RawMaterialEntity> findByPnoSql(int pno);
}
