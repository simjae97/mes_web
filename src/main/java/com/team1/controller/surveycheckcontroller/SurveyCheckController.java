package com.team1.controller.surveycheckcontroller;

import com.team1.model.dto.SurveyBDto;
import com.team1.model.dto.SurveyDto;
import com.team1.model.dto.surveyCheckDto.SurveyCheckOutDto;
import com.team1.model.entity.WorkPlanEntity;
import com.team1.service.surveycheckservice.SurveyCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/survey")
public class SurveyCheckController {

    @Autowired
    SurveyCheckService surveyCheckService;


    // Survey 이용 하여 sno번호 제품명 계량날짜 계량원 상태 가져오기
    @GetMapping("/check/survey/get.do")
    public List<SurveyCheckOutDto> roadCheckSurvey(){
        return surveyCheckService.roadCheckSurvey();
    }



    // 2. 검사 완료 체크 시 검사 완료자 데이터 저장
    @PutMapping("/check/complete/put.do")
    // 매개변수 state = 뷰에서 selctBox에서 선택한 값입니다. 0=검사대기 / 1=불합격  / 2=합격
    public int surveyCheck(SurveyDto surveyDto , int state){
        int sno = surveyDto.getSno();
        int sstate = surveyDto.getSstate();
        System.out.println("state = " + state);
        return surveyCheckService.surveyCheck(sno  ,state);
    }





}
