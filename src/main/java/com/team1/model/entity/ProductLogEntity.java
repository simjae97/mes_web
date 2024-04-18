package com.team1.model.entity;

import com.team1.model.dto.ProductLogDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "productlog")
@SuperBuilder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductLogEntity extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int plno;       // 식별번호 (벌크 테이블)

    @ManyToOne
    @JoinColumn(name = "pno")
    private ProductEntity productEntity;

     private int plcount; //제품이 얼마나 입/출고 되었는지


    //이 제품로그는 실제로 제품이 입/출고된것을 체크하는 테이블입니다. 실제 포장작업완료시 이곳에 로그를 추가해야합니다.


    public ProductLogDto toDto(){
        return ProductLogDto.builder()
                .plno(this.plno)
                .productDto(this.productEntity.toDto())
                .plcount(this.plcount)
                .cdate(this.cdate)
                .udate(this.udate)
                .build();
    }

}
