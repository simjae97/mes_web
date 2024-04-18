package com.team1.model.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class PageDto {//class start
    private int page;       // 현재 페이지
    private int count;      // 총 페이지 수
    private List<Object> data;  // 본문

}//class end1
