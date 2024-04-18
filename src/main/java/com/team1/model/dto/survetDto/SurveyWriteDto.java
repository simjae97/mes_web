package com.team1.model.dto.survetDto;

import com.team1.model.dto.BaseTimeDto;
import com.team1.model.entity.SurveyEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SurveyWriteDto extends BaseTimeDto {

    private int sno; //  식별번호
    private int sstate; // 수량
    private int inputmno;
    private int checkmno; // 담당자

    private int wno; // 워크플랜 식별번호



    // - Dto를 엔티티로 변환하는 메소드 저장하기
    public SurveyEntity toEntity() {
        return SurveyEntity.builder()

                .sstate(this.sstate)
                .build();
    }


}

