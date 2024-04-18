package com.team1.model.entity;

import com.team1.model.dto.SurveyBDto;
import com.team1.model.dto.SurveyDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table( name = "surveyb")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class SurveyBEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sbno; // 고유번호

    private int sbcount; // 원료수량

    @ManyToOne
    @JoinColumn(name = "rmno")
    private RawMaterialEntity rawMaterialEntity; // 원료이름

    @ManyToOne
    @JoinColumn(name = "sno")
    private SurveyEntity surveyEntity;



    public SurveyBDto toDto(){ // R
        return SurveyBDto.builder()
                .sbno(this.sbno)
                .sbcount(this.sbcount)
                .rmname(this.rawMaterialEntity.getRmname())
                .sno(this.surveyEntity.getSno())
                .build();
    }





}
