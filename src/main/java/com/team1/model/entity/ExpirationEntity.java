package com.team1.model.entity;

import com.team1.model.dto.ExpirationDTO;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Entity(name = "expiration")
@SuperBuilder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ExpirationEntity extends BaseTime {
    //폐기 대기 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int epno;       // 식별번호 ()

    @ManyToOne
    @JoinColumn(name = "pno")
    ProductEntity productEntity;


    int plcount; //분량

    public ExpirationDTO toDto(){
        return ExpirationDTO.builder().epno(this.epno).pno(this.productEntity.getPno()).pname(this.productEntity.getPname()).plcount(this.plcount).cdate(this.cdate).udate(this.udate).build();
    }
}
