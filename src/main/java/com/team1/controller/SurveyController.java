package com.team1.controller;

import com.team1.model.dto.WorkPlanDto;
import com.team1.model.dto.survetDto.SurveyInsertDto;
import com.team1.model.dto.survetDto.SurveyPlanInfoDto;
import com.team1.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/survey")
public class SurveyController {
    @Autowired
    private SurveyService surveyService;

    // workplan 정보전부 가져오기
    @GetMapping("/workplaninfo.do")
    public List<WorkPlanDto> workPlanDtoList (){

        List<WorkPlanDto> result = surveyService.workPlanDtoList();

        return result;
    }

    // workplan 식별번호로 정보 가져오기
    @PostMapping("/workplanoneinfo.do")
    public WorkPlanDto workPlanDto (int wno){
        WorkPlanDto result = surveyService.workPlanDto(wno);

        return result;
    }
    // workPlan 클릭했을때 반환받는거
    // 1. WorkPlan 식별번호로 만들제품 파악
    // 2. 제품에 들어가는 원재료 를 파악
    // 3. 반환해야하는것 = 제품명 / 수량 / 원재료종류수량 / 각 원재료별 필요한수량(수량*원재료비율)=> 계산 뷰에서
    @PostMapping("/workplan/clilck.do")
    public SurveyPlanInfoDto surveyClilckDo(int wno){
        return surveyService.surveyClilckDo(wno);
    }

    // 계량 등록버튼을 눌럿을떄
    // C survey 와 surveyB 등록하기
    // 반환 정보 :
        // 0 이상 = sno 의 PK번호
        // -1 로그인 정보가 없음
        // -2 Survey 저장실패
        // -3 해당 원자재 레코드가 없음
        // -4 검사 단계 진행됨 (수정불가능).
    @PostMapping("/insert.do")
    public int surveyInsertDo(@RequestBody SurveyInsertDto surveyInsertDto){
        //System.out.println("surveyInsertDto.getSurveyBDto().get(0) = " + surveyInsertDto.getSurveyBDto().get(0));
        return surveyService.surveyInsertDo(surveyInsertDto);
    }

}//class end
