package com.team1.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass //
@EntityListeners( AuditingEntityListener.class )
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BaseTime {
    // 1. 레코드/엔티티 등록날짜
    @CreatedDate // default now()
    public LocalDateTime cdate;
    // 2. 레코드/엔티티 수정날짜
    @LastModifiedDate // 마지막 수정 날짜
    public LocalDateTime udate;
}
/*
    상속 : 여러곳에서 공통적인 멤버들

 */