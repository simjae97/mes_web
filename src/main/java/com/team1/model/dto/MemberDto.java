package com.team1.model.dto;


import com.team1.model.entity.BaseTime;
import com.team1.model.entity.MemberEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto extends BaseTimeDto {


    private int mno;

    private String mid;

    private String mpw;

    private String mname;

    private int part; // 임시 파트번호

    public MemberEntity toEntity(){
        return MemberEntity.builder()
                .mno(this.mno)
                .mid(this.mid)
                .mpw(this.mpw)
                .part(this.part)
                .mname(this.mname)
                .build();

    }
}
