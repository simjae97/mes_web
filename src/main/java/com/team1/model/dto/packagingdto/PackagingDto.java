package com.team1.model.dto.packagingdto;

import com.team1.model.dto.BaseTimeDto;
import com.team1.model.dto.MemberDto;
import com.team1.model.dto.ProductDto;
import com.team1.model.entity.PackagingEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PackagingDto extends BaseTimeDto {

    private int pgno; // 식별번호

    private ProductDto productDto; // 제품 테이블 가져오기

    private int pgcount; // 수량

    private MemberDto memberDto; // 등록자

    public PackagingEntity toEntity(){
        return PackagingEntity.builder()
                .memberEntity(this.toEntity().getMemberEntity())
                .subdivisionEntity(this.toEntity().getSubdivisionEntity())
                .build();
    }
}
