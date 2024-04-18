package com.team1.model.repository;

import com.team1.model.entity.ProductEntity;
import com.team1.model.entity.ProductLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductLogRepository extends JpaRepository<ProductLogEntity , Integer> {

    @Query(value = "select sum(plcount) as value , pname as label from productlog as pl join product as p on p.pno = pl.pno  group by(p.pno)" , nativeQuery = true)
    List<Map<Object,Object>> findremaining( );

    @Query(value = "SELECT WEEK(pl.cdate) AS cdate, SUM(plcount) AS plcount, p.pno , pname FROM productlog as pl join product as p on p.pno = pl.pno\n" +
            "            WHERE MONTH(pl.cdate) = MONTH(CURRENT_DATE()) AND YEAR(pl.cdate) = YEAR(CURRENT_DATE()) GROUP BY pl.pno, WEEK(pl.cdate)", nativeQuery = true)
    List<Map<Object,Object>> findlogWeek();

    @Query(value = "SELECT Month(pl.cdate) AS cdate, SUM(plcount) AS plcount, p.pno, pname \n" +
            "FROM productlog as pl join product as p on p.pno = pl.pno \n" +
            "WHERE YEAR(pl.cdate) = YEAR(CURRENT_DATE())\n" +
            "GROUP BY pl.pno, Month(pl.cdate)",nativeQuery = true)
    List<Map<Object,Object>> findlogMonth();

    @Query(value = "select * from productlog where pno=:pno", nativeQuery = true)
    List<ProductLogEntity> findlog(int pno);
}


