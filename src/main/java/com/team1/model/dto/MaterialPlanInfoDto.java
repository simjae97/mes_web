package com.team1.model.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MaterialPlanInfoDto extends BaseTimeDto{
    // 출력용 DTO
    private WorkPlanDto workPlanDto;    // 작업내용
    private List<RecipeDto> recipeDto;        // 만들제품의 레시피

}
