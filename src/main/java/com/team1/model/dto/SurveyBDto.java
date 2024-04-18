package com.team1.model.dto;

import com.team1.model.entity.RawMaterialEntity;
import com.team1.model.entity.SurveyBEntity;
import com.team1.model.entity.SurveyEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
public class SurveyBDto extends BaseTimeDto {

    private int sbno;
    private int sbcount;
    private int rmno;
    private int sno;
    private String rmname;

    private SurveyDto surveyDto;
    private RawMaterrialDto rawMaterrialDto;



    public SurveyBEntity toEntity(){ // C
        return SurveyBEntity.builder()
                .sbcount(this.sbcount)
                .rawMaterialEntity(this.rawMaterrialDto.toEntity())
                .surveyEntity(this.surveyDto.toEntity())

                .build();
    }





}
