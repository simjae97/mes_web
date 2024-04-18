package com.team1.model.entity;

import com.team1.model.dto.RawMaterrialDto;
import com.team1.model.dto.SurveyDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "survey")
@SuperBuilder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SurveyEntity extends BaseTime{
    @Id// 오토인크리먼트 사용 X
    private int sno; // 계량 식별 번호

    @Column( nullable = true)
    private int sstate; // 지시 수량

    @ManyToOne
    @JoinColumn(name = "inputmno")
    private MemberEntity inputmemberEntity; // 사원번호 따오기 위해 회원테이블 가져옴

    @ManyToOne
    @JoinColumn(name = "checkmno")
    private MemberEntity checkmemberEntity; // 사원번호 따오기 위해 회원테이블 가져옴

    @ManyToOne
    @JoinColumn(name = "wno")
    private WorkPlanEntity workPlanEntity; // 업무계획 테이블 가져옴 ( 제품명,지시 수량 , 그날에 어떤 제품을 만드는 것 )



    // - 엔티티를 dto로 변환하는 메소드
    public SurveyDto toDto() {
        SurveyDto surveyDto = SurveyDto.builder()
                .sno(this.sno)
                .sstate(this.sstate)
                .inputmno(this.inputmemberEntity.getMno())
                .workPlanDto(this.workPlanEntity.toDto())
                .build();

        surveyDto.setCdate(this.cdate);
        surveyDto.setUdate(this.udate);
        return surveyDto;
    }

    // 전승호 ===



}

