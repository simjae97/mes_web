package com.team1.model.dto.survetDto;

import com.team1.model.dto.BaseTimeDto;
import com.team1.model.dto.MemberDto;
import com.team1.model.dto.SurveyBDto;
import com.team1.model.dto.WorkPlanDto;
import com.team1.model.entity.RawMaterialEntity;
import com.team1.model.entity.SurveyBEntity;
import com.team1.model.entity.SurveyEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SurveyInsertDto extends BaseTimeDto {

    // Survey
    private MemberDto inputmno;
    private MemberDto checkmno; // 담당자
    private int wno; // 워크플랜 식별번호

    private WorkPlanDto workPlanDto; // 전송용

    // SurveyB
    private List<SurveyBDto> surveyBDto;


    public SurveyEntity toSurveyEntity() {
        return SurveyEntity.builder() // 등록용
                .inputmemberEntity(this.inputmno.toEntity())
                .workPlanEntity(this.workPlanDto.toEntity())
                .build();
    }

//    public SurveyBEntity toSurveyBEntity(int i){
//        return SurveyBEntity.builder()
//                .sbcount(this.surveyBDto.get(i).getSbcount())
//                .rawMaterialEntity(this.surveyBDto.get(i).getRawMaterrialDto().toEntity())
//                .surveyEntity(this.)
//                .build();
//    }

}// class end
