package com.team1.model.entity;

import com.team1.model.dto.RawMaterrialDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "rawmaterial")
@SuperBuilder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RawMaterialEntity extends BaseTime{
    @Id
    @GeneratedValue( strategy =  GenerationType.IDENTITY )
    private int rmno; // 원자재 식별번호
    @Column( length = 30 , nullable = false)
    private String rmname; // 원자재 이름


    // - 엔티티를 dto로 변환하는 메소드
    public RawMaterrialDto toDto() {
        RawMaterrialDto rawMaterrialDto = RawMaterrialDto.builder()
                .rmno(this.rmno)
                .rmname(this.rmname)
                .build();
        rawMaterrialDto.setCdate(this.cdate);
        rawMaterrialDto.setUdate(this.udate);
        return rawMaterrialDto;
    }


}

