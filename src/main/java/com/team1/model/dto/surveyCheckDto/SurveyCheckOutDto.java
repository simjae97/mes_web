package com.team1.model.dto.surveyCheckDto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@SuperBuilder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SurveyCheckOutDto { // 출력용 DTO // 박시현

    private int wno; //  워크플랜 번호
    private int sno; // 계량 번호
    private int sstate; // 상태    0 검사전   1 검사완료  2  검사불합격  sno
    private String inputmname;  // 계량한 직원    sno
    private String checkmname; // 담당자        sno
    private String pname; // 제품 이름  product 테이블
    private int wcount; // 제품 수량 워크플랜 테이블
    private LocalDateTime cdate; // 계량시작날짜    sno
    private LocalDateTime udate; // 계량수정날짜    sno

    private List<SurveyBCheckOutDto> SurveybList;    //

}
