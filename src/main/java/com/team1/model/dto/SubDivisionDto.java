package com.team1.model.dto;

import com.team1.model.dto.ManufacturingDto.ManufacturingDto;
import com.team1.model.entity.ManufacturingEntity;
import com.team1.model.entity.SubdivisionEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SubDivisionDto extends BaseTimeDto{
    private int sdno;
    private ManufacturingDto manufacturingDto;
    private MemberDto inputmemberDto;
    private MemberDto checkmemberDto;
    private int failcount;
    private int successcount;
    private int sdstate;

    public SubdivisionEntity toEntity(){
        return SubdivisionEntity.builder()
                .sdno(this.sdno)
                .manufacturingEntity(this.manufacturingDto.toEntity())
                .inputmemberEntity(this.inputmemberDto.toEntity())
                .checkmemberEntity(this.checkmemberDto.toEntity())
                .failCount(this.failcount)
                .successCount(this.successcount)
                .sdstate(this.sdstate)
                .build();
    }
}
