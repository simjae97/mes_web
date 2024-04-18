package com.team1.model.repository;

import com.team1.model.entity.SurveyBEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SurveyBRepository extends JpaRepository<SurveyBEntity ,Integer> {

    // SurveyB 업데이트용도
    // sno 랑 rmno 해당하는 엔티티 찾기
    @Query(value = "select * from surveyb where sno =:sno and rmno = :rmno" , nativeQuery = true)
    SurveyBEntity findByUpdate(int sno ,int rmno);

    @Query(value = "select * from surveyb where sno =:sno" , nativeQuery = true)
    List<SurveyBEntity> findBySno(int sno);
}
