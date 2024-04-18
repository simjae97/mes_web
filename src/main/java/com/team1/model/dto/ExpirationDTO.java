package com.team1.model.dto;


import com.team1.model.entity.ExpirationEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@SuperBuilder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ExpirationDTO extends BaseTimeDto {

    private int epno;       // 식별번호 ()

    private String pname;

    int pno; //프로덕트 번호 ?  <<<<<<<<<<<<<<<이 테이블은 input을 스케쥴러로 할 건데 productEntity를 넣어야 하는가에 대해 고민

    int plcount; //분량

    public ExpirationEntity toEntity(){
        return ExpirationEntity.builder().epno(this.epno).plcount(this.plcount).cdate(this.cdate).udate(this.udate).build();
    }
}
