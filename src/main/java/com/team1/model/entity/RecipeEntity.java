package com.team1.model.entity;


import com.team1.model.dto.ProductDto;
import com.team1.model.dto.RecipeDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "recipe")
@SuperBuilder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RecipeEntity extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reno; // 레시피 식별번호

    @Column(nullable = false)
    private int reamount; // 레시피 분량

    @ManyToOne
    @JoinColumn(name = "pno")
    private ProductEntity productEntity; // 제품 테이블 가져옴 ( 제품 이름 )

    @ManyToOne
    @JoinColumn(name = "rmno")
    private RawMaterialEntity rawMaterialEntity; // 원자재 테이블 가져옴 ( 원자재 이름 )

    public RecipeDto toDto() {
        RecipeDto recipeDto = RecipeDto.builder()
                .pno(this.productEntity.getPno())
                .rmno(this.rawMaterialEntity.getRmno())
                .reamount(this.reamount)
                .reno(this.reno)
                .pname(this.productEntity.getPname())
                .rmname(this.rawMaterialEntity.getRmname())
                .build();
        recipeDto.setCdate(this.cdate);
        recipeDto.setUdate(this.udate);
        return recipeDto;
    }

}
