package com.team1.model.dto.ManufacturingDto;

import com.team1.model.entity.BaseTime;
import com.team1.model.entity.BulkEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BulkLogDto extends BaseTime {

    private int blno;       // 식별번호 (벌크 테이블)

    private ManufacturingDto manufacturingDto;

    private int blcount; //벌크 그람수


    //이 벌크로그는 실제로 벌크가 입/출고된것을 체크하는 테이블입니다. 실제 벌크작업완료시 이곳에 추가해야합니다.

}
