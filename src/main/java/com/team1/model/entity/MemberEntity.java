package com.team1.model.entity;


import com.team1.model.dto.MemberDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "member")
@SuperBuilder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberEntity extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mno; // 회원 식별키

    @Column(nullable = false,unique = true)
    private String mid; // 회원 아이디

    @Column(nullable = false)
    private String mpw; // 회원 비밀번호

    @Column(nullable = false)
    private String mname; // 회원 이름

    private int part; // 임시 파트번호 ( 어떤 파티에서 일하는지 )
    // -1 관리자 , 계량생산자 1 , 계량투입자 2 ,
    // 검사자 10

    public MemberDto toDto(){
        MemberDto memberDto = MemberDto.builder().
                mno(this.mno).
                mid(this.mid).
                mpw(this.mpw).
                part(this.part).
                mname(this.mname).build();
        memberDto.setCdate(this.cdate);
        memberDto.setUdate(this.udate);
        return memberDto;

    }
}
