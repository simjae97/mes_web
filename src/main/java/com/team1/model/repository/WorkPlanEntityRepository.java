package com.team1.model.repository;

import com.team1.model.entity.WorkPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WorkPlanEntityRepository extends JpaRepository<WorkPlanEntity , Integer> {
    // 필드값으로 1개 찾기
    WorkPlanEntity findBywno(int wno);


    //mipno찾기 , `manufacturing`테이블 참조용
    @Query(value = "select mipno from workplan as w join materialinput as mi on w.wno= mi.wno2 where wno= :wno",nativeQuery = true)
    Integer findMipnoByWno(int wno);


}
