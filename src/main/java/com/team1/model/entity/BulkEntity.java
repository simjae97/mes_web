package com.team1.model.entity;

import com.team1.model.dto.ManufacturingDto.BulkDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "bulk")
@SuperBuilder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BulkEntity extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bno;       // 식별번호 (벌크 테이블)

    @ManyToOne
    @JoinColumn(name = "pno")
    private ProductEntity productEntity;

    //이 벌크 클래스는 벌크제품에 대한 리스트입니다.

    public BulkDto toDto(){
        return BulkDto.builder()
                .bno(this.bno)
                .productDto(this.productEntity.toDto())
                .cdate(this.cdate)
                .udate(this.udate)
                .build();
    }

}
