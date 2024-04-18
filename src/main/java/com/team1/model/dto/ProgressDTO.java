package com.team1.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProgressDTO {
    // 진행사항 데이터 이동 객체


    private int wno; // 워크플랜 작업 번호

    private int wcount; // 작업수량

    private int wstate; // 작업상태 0 ~ 9

    private LocalDateTime wendtime; // 마감일
    
    private String pname; // 제품명

    
}
