package com.team1.model.dto.ManufacturingDto;

import com.team1.model.dto.ProductDto;
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
public class BulkDto extends BaseTime {
    private int bno;       // 식별번호 (벌크 테이블)

    private ProductDto productDto;

    //이 벌크 클래스는 벌크제품에 대한 리스트입니다.


}
