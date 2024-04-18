package com.team1.model.entity;


import com.team1.model.dto.MaterialInputDto;
import com.team1.model.dto.MemberDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "materialinput")
@SuperBuilder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MaterialInputEntity extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mipno; // 원료 투입 식별번호

    @ManyToOne
    @JoinColumn(name = "sno2")
    private SurveyEntity surveyEntity; // 계량 테이블 보기

    @ManyToOne
    @JoinColumn(name = "pno")
    private ProductEntity productEntity; // 어떤 제품인지를 알기위해 제품테이블을 가져옴

    @ManyToOne
    @JoinColumn(name = "inputmno")
    private MemberEntity inputmemberEntity; // 사원번호 따오기 위해 회원테이블 가져옴

    @ManyToOne
    @JoinColumn(name = "checkmno")
    private MemberEntity checkmemberEntity; // 사원번호 따오기 위해 회원테이블 가져옴

    @ColumnDefault("0")
    private int mipstate; // 검사 상태

    @ManyToOne
    @JoinColumn(name = "wno2")
    private WorkPlanEntity workPlanEntity;

    public MaterialInputDto toDto(){
        MaterialInputDto materialInputDto = MaterialInputDto.builder()
                .productDto(this.productEntity.toDto())
                .surveyDto(this.surveyEntity.toDto())
                .mipno(this.mipno)
                .mipstate(this.mipstate)
                .inputmemberDto(this.inputmemberEntity.toDto())
                .checkmemberDto(this.checkmemberEntity != null ? this.checkmemberEntity.toDto() : null )
                .workPlanDto(this.workPlanEntity.toDto())
                .build();
        materialInputDto.setCdate(this.cdate);
        materialInputDto.setUdate(this.udate);
        return materialInputDto;
    }
}

/*
    워크플랜 참조해야함(제품명,제품수량)
*/
