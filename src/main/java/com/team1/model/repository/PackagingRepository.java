package com.team1.model.repository;

import com.team1.model.entity.MaterialInputEntity;
import com.team1.model.entity.PackagingEntity;
import com.team1.model.entity.ProductLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackagingRepository extends JpaRepository<PackagingEntity , Integer > {
    @Query(value = "select packaging.* , pname , packagingcount , period from packaging inner join subdivision on packaging.sdno = subdivision.sdno inner join manufacturing on subdivision.mfno = manufacturing.mfno inner join materialinput on manufacturing.mipno = materialinput.mipno inner join product on materialinput.pno = product.pno where pgno=:pgno" , nativeQuery = true)
    List<PackagingEntity> findByPgno(int pgno);

    @Query(value = "select packaging.* , product.pname from packaging inner join subdivision on packaging.sdno = subdivision.sdno inner join manufacturing on subdivision.mfno = manufacturing.mfno inner join materialinput on manufacturing.mipno = materialinput.mipno inner join product on materialinput.pno = product.pno" , nativeQuery = true)
    List<PackagingEntity> findByAllProduct();

    @Query(value = "select p.* from packaging as p join subdivision as s on p.sdno = s.sdno join manufacturing as mf on s.mfno = mf.mfno join materialinput as mi on mi.mipno = mf.mipno join workplan as w on w.wno = mi.wno2 where wno = :wno" , nativeQuery = true)
    PackagingEntity findbywno(int wno);

}
