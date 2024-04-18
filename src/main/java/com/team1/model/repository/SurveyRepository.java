package com.team1.model.repository;

import com.team1.model.entity.SurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SurveyRepository extends JpaRepository<SurveyEntity,Integer> {

    // surveyb , rawmaterial , recipe 검색
    @Query(value = "select * from surveyb as s join rawmaterial as r on s.rmno = r.rmno join recipe as p on r.rmno = p.rmno where s.sno = :sno and p.pno = :pno" , nativeQuery = true)
    List<  Map<Object , Object >> findBySurveyCheckSQL(int sno , int pno);

    @Query(value = "select * from survey as s join workplan as w on s.wno = w.wno where s.sno = :sno" , nativeQuery = true)
    Map<Object , Object> findBySurveyCheckCountSQL(int sno);

    @Query(value = "select sno from survey where wno=:wno",nativeQuery = true)
    Integer findSnoBywno(int wno);
}
