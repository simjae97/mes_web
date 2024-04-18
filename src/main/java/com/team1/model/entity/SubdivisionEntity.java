package com.team1.model.entity;

import com.team1.model.dto.SubDivisionDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "subdivision")
@SuperBuilder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SubdivisionEntity extends BaseTime{//class start
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sdno;       // 식별번호 (소분 테이블)

    @ManyToOne
    @JoinColumn(name = "mfno")
    private ManufacturingEntity manufacturingEntity;

    @ManyToOne
    @JoinColumn(name = "inputmno")
    private MemberEntity inputmemberEntity; // 등록자

    @ManyToOne
    @JoinColumn(name = "checkmno")
    private MemberEntity checkmemberEntity; // 검사자

    @ColumnDefault("0")
    private int failCount;      // 불량품 개수

    @ColumnDefault("0")
    private int successCount;   // 성공수량

    @ColumnDefault("0")
    private int sdstate; // 검사상태

    public SubDivisionDto toDto(){
        SubDivisionDto subDivisionDto = SubDivisionDto.builder()
                .sdno(this.sdno)
                .manufacturingDto(this.manufacturingEntity.toDto())
                .inputmemberDto(this.inputmemberEntity.toDto())
                .checkmemberDto(this.checkmemberEntity != null ? this.checkmemberEntity.toDto() : null)
                .failcount(this.failCount)
                .successcount(this.successCount)
                .sdstate(this.sdstate)
                .build();
        subDivisionDto.setCdate(this.cdate);
        subDivisionDto.setUdate(this.udate);

        return subDivisionDto;
    }


}//class end
