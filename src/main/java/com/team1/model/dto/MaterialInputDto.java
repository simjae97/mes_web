package com.team1.model.dto;


import com.team1.model.entity.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MaterialInputDto extends BaseTimeDto {

    private int mipno;

    private int mipstate;

    private SurveyDto surveyDto;

    private ProductDto productDto;

    private MemberDto inputmemberDto;

    private MemberDto checkmemberDto;

    // private SurveyBDto surveyBDto;

    private WorkPlanDto workPlanDto;

    public MaterialInputEntity toEntity(){
        return MaterialInputEntity.builder()
                .productEntity(this.productDto.toEntity())
                .surveyEntity(this.surveyDto.toEntity())
                .mipno(this.mipno)
                .mipstate(this.mipstate)
                .inputmemberEntity(this.inputmemberDto.toEntity())
                .workPlanEntity(this.workPlanDto.toEntity())
                .build();

    }
}
