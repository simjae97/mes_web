package com.team1.model.dto;


import com.team1.model.entity.BaseTime;
import com.team1.model.entity.ProductEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto extends BaseTimeDto {

    private int pno;
    private String pname;

    private int ferment; //숙성기간(단위:일)

    private int standard; //한제품에 들어가는 용량?(소분용)

    private int packagingcount; //한빡스당 들어가는 제품 개수

    private int period; //유통기한

    public ProductEntity toEntity(){
        return ProductEntity.builder()
                .pno(this.pno)
                .pname(this.pname)
                .build();

    }

}
