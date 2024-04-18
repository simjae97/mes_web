package com.team1.model.entity;

import com.team1.model.dto.packagingdto.PackagingDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "packaging")
@SuperBuilder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PackagingEntity extends BaseTime{//class start
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pgno;       // 식별번호 (포장 테이블)

    @ManyToOne
    @JoinColumn(name = "sdno")
    private SubdivisionEntity subdivisionEntity;    // 소분 테이블 엔티티

    private int pgcount;      // 수량

    @ManyToOne
    @JoinColumn(name = "mno")
    private MemberEntity memberEntity; // 등록자

    public PackagingDto toDto(){
        return PackagingDto.builder()
                .pgno(this.pgno)
                .productDto(this.subdivisionEntity.getManufacturingEntity().getMaterialInputEntity().getProductEntity().toDto())
                .pgcount(this.pgcount)
                .memberDto(this.memberEntity.toDto())
                .cdate(this.cdate)
                .udate(this.udate)
                .build();
    }


}//class end
