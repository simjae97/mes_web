package com.team1.model.entity;

import com.team1.model.dto.ManufacturingDto.ManufacturingDto;
import com.team1.model.dto.MaterialInputDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "manufacturing")
@SuperBuilder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ManufacturingEntity extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mfno;       // 식별번호 (제조(벌크) 테이블)

    @ManyToOne
    @JoinColumn(name = "mipno")
    private MaterialInputEntity materialInputEntity;

    @ColumnDefault("0")
    private int mfcount; // 벌크수량

    @ColumnDefault("0")
    private int mfstate; // 검사 상태

    @ManyToOne
    @JoinColumn(name = "inputmno")
    private MemberEntity inputmemberEntity; // 등록자

    @ManyToOne
    @JoinColumn(name = "checkmno")
    private MemberEntity checkmemberEntity; // 검사자

    public ManufacturingDto toDto(){
        return ManufacturingDto.builder()
                .mfno(this.mfno)
                .materialInputDto(this.materialInputEntity.toDto())
                .mfcount(this.mfcount)
                .mfstate(this.mfstate)
                .inputmemberDto(this.inputmemberEntity.toDto())
                .checkmemberDto(this.checkmemberEntity != null ? this.checkmemberEntity.toDto() : null)
                .cdate(this.cdate)
                .udate(this.udate)
                .build();
    }
}



