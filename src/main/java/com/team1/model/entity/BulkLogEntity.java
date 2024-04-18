package com.team1.model.entity;

import com.team1.model.dto.ManufacturingDto.BulkDto;
import com.team1.model.dto.ManufacturingDto.BulkLogDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "bulklog")
@SuperBuilder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BulkLogEntity extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int blno;       // 식별번호 (벌크 테이블)

    @ManyToOne
    @JoinColumn(name = "mfno")
    private ManufacturingEntity manufacturingEntity;

     private int blcount; //벌크 그람수


    //이 벌크로그는 실제로 벌크가 입/출고된것을 체크하는 테이블입니다. 실제 벌크작업완료시 이곳에 추가해야합니다.
    public BulkLogDto toDTo(){
        return BulkLogDto.builder()
                .blno(this.blno)
                .manufacturingDto(this.manufacturingEntity.toDto())
                .blcount(this.blcount)
                .cdate(this.cdate)
                .udate(this.udate)
                .build();
    }

}
