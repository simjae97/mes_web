package com.team1.model.dto;

import com.team1.model.entity.ProductEntity;
import com.team1.model.entity.ProductLogEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductLogDto extends BaseTimeDto{
    private int plno;

    private ProductDto productDto;

    private int plcount;

    public ProductLogEntity toEntity(){
        return ProductLogEntity.builder()
                .productEntity(this.toEntity().getProductEntity())
                .plcount(this.plcount)
                .cdate(this.cdate)
                .udate(this.udate)
                .build();
    }

}
