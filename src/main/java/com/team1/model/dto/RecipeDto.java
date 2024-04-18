package com.team1.model.dto;


import com.team1.model.entity.BaseTime;
import com.team1.model.entity.ProductEntity;
import com.team1.model.entity.RawMaterialEntity;
import com.team1.model.entity.RecipeEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;


@SuperBuilder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDto extends BaseTimeDto {

    private int reno;
    private int reamount;
    private int pno;
    private String pname; //제품이름
    private int rmno;
    private String rmname; //원자재 이름

    private ProductDto productDto;
    private List<RawMaterrialDto> rawMaterrialDto;

    public RecipeEntity toEntity(){
        return RecipeEntity.builder().reamount(this.reamount).build();
    }

}
