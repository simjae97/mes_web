package com.team1.model.dto.ManufacturingDto;

import com.team1.model.dto.BaseTimeDto;
import com.team1.model.dto.MaterialInputDto;
import com.team1.model.dto.MemberDto;
import com.team1.model.entity.BaseTime;
import com.team1.model.entity.ManufacturingEntity;
import com.team1.model.entity.MaterialInputEntity;
import com.team1.model.entity.MemberEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ManufacturingDto extends BaseTimeDto {
    private int mfno;       // 식별번호 (제조(벌크) 테이블)

    private MaterialInputDto materialInputDto; // 투입테이블

    private int mfcount; // 수량

    private int mfstate; // 검사 상태

    private MemberDto inputmemberDto; // 등록자

    private MemberDto checkmemberDto; // 검사자

    public ManufacturingEntity toEntity() {
        return ManufacturingEntity.builder()
                .mfno(this.mfno)
                .materialInputEntity(this.materialInputDto.toEntity())
                .build();
    }
}

