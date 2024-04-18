package com.team1.model.dto.survetDto;


import com.team1.model.dto.BaseTimeDto;
import com.team1.model.dto.ProductDto;
import com.team1.model.dto.RawMaterrialDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;


@SuperBuilder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResponseRecipeDto extends BaseTimeDto {

    private int reno;
    private int reamount;
    private int pno;
    private int rmno;

    private ProductDto productDto;
    private List<RawMaterrialDto> rawMaterrialDto;



}
