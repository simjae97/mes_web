package com.team1.model.entity;


import com.team1.model.dto.ProductDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "product")
@SuperBuilder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pno; // 제품 식별번호

    @Column(nullable = false)
    private String pname; // 제품 이름

    @Column(columnDefinition = "int default 0 ")
    private int ferment; //숙성기간(단위:일)
    @Column(columnDefinition = "int default 0 ")
    private int standard; //한제품에 들어가는 용량?(소분용)
    @Column(columnDefinition = "int default 0 ")
    private int packagingcount; //한빡스당 들어가는 제품 개수
    @Column(columnDefinition = "int default 0 ")
    private int period; //유통기한

    public ProductDto toDto(){
        ProductDto productDto = ProductDto.builder()
                .pno(this.pno)
                .pname(this.pname)
                .ferment(this.ferment)
                .standard(this.standard)
                .packagingcount(this.packagingcount)
                .period(this.period)
                .build();
        productDto.setCdate(this.cdate);
        productDto.setUdate(this.udate);
        return productDto;
    }

}
