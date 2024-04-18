package com.team1.model.dto;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BaseTimeDto {
    // 1. 레코드/엔티티 등록날짜
    public LocalDateTime cdate;
    // 2. 레코드/엔티티 수정날짜

    public LocalDateTime udate;
}
/*
    상속 : 여러곳에서 공통적인 멤버들

 */