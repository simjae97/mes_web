package com.team1.model.repository;

import com.team1.model.entity.RawMaterialEntity;
import com.team1.model.entity.RawMaterialLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
public interface RawMateriallogRepository extends JpaRepository<RawMaterialLogEntity,Integer> {


    @Query(value = "select * from rawmateriallog where rmno = :rmno",nativeQuery = true)
    List<RawMaterialLogEntity> findByPnoSql(int rmno);




    @Query(value ="select r.rmno,r.rmname, sum(coalesce(rmlcount,0)) as rmsum from rawmateriallog as rl right outer join rawmaterial as r on r.rmno = rl.rmno group by rmno;", nativeQuery = true)
    List<Map<Object,Object>> findsumlogsql(); //원자재 로그를 보고 원자재가 얼마나 쌓여있는지 체크하는 sql, 
    // right outer join한 이유: 원자재 테이블에는 존재하지만 원자재 로그가 없는 경우도 뽑아오기 위함 ,
    //coalesce함수: 괄호 안의 변수를 찾아서 가장 먼저 값이 존재하는 (null이 아니면) 매개변수 출력 ex)coalesce(null,null,50) => 50출력, coalesce(50, 0)=>50출력
    
}
