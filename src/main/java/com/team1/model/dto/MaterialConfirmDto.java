package com.team1.model.dto;

import com.team1.model.entity.MaterialInputEntity;
import lombok.*;

@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MaterialConfirmDto {
    private int micno;
    private String micname;
    private int micstate;
    private MaterialInputDto materialInputDto;

    public MaterialInputEntity toEntity(){
        return MaterialInputEntity.builder()

                .build();

    }
}
