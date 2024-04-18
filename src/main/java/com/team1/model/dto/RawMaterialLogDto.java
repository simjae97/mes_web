package com.team1.model.dto;

import com.team1.model.entity.BaseTime;
import com.team1.model.entity.RawMaterialEntity;
import com.team1.model.entity.RawMaterialLogEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


@SuperBuilder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RawMaterialLogDto extends BaseTimeDto{

    private int rmlno; // 원자재로그 식별번호

    private int rmlcount; // 원자재 로그가 얼마나 들어갔는지

    private String rmname; //원자재 이름


    public RawMaterialLogEntity toEntity(){
        return RawMaterialLogEntity.builder()
                .rmlno(this.rmlno)
                .rmlcount(this.rmlcount)
                .build();

    }
}
