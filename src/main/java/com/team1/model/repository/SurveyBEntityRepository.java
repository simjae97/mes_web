package com.team1.model.repository;

import com.team1.model.entity.SurveyBEntity;
import com.team1.model.entity.SurveyEntity;
import com.team1.model.entity.WorkPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SurveyBEntityRepository extends JpaRepository<SurveyBEntity,Integer> {

}
