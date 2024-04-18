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
public class SurveyBCheckOutDto {

    // 원자재 이름
    private String rmname;
    // 필요 투입량 수량
    private int reamount;
    // 입력한 원자재 투입량 수량
    private int sbcount;
    // rmno
    private int rmno;




}
