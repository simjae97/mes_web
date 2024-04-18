package com.team1.model.repository;

import com.team1.model.entity.MaterialInputEntity;
import com.team1.model.entity.SubdivisionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SubDivisionRepository extends JpaRepository<SubdivisionEntity,Integer> {

    @Query(value = "select s.* from subdivision as s join manufacturing as mf on s.mfno = mf.mfno join materialinput as mi on mi.mipno = mf.mipno join workplan as w on w.wno = mi.wno2 where wno = :wno",nativeQuery = true)
    SubdivisionEntity findByWno(int wno);
}
