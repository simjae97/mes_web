package com.team1.controller.subdivisioncontroller;

import com.team1.model.dto.ManufacturingDto.ManufacturingDto;
import com.team1.model.dto.MaterialInputDto;
import com.team1.model.dto.SubDivisionDto;
import com.team1.service.memberserivce.MemberService;
import com.team1.service.subdivisionservice.SubDivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subdivision")
public class SubDivisionController {
    @Autowired
    private SubDivisionService subDivisionService;
    @Autowired
    private MemberService memberService;

    // 소분 보고서 작성
    // 소분 보고서 작성
    // 반환 0 = 실패 / 1 이상 = 성공
    // 반환 -1 = 로그인정보 없음  / -2 = 해당담당자아님
    @PostMapping("/input/post.do")
    public int doSubDivisionInputPost(@RequestParam int mfno , SubDivisionDto subDivisionDto){
        int successcount = subDivisionDto.getSuccesscount();
        int failcount = subDivisionDto.getFailcount();
        return subDivisionService.doSubDivisionInputPost(mfno,failcount,successcount);
    }

    // 소분 모두 출력
    @GetMapping("/allinfo/get.do")
    public List<SubDivisionDto> doSubDivisionAllinfoGet(){
        return subDivisionService.doSubDivisionAllinfoGet();
    }

    // 벌크 모든 정보 출력
    @GetMapping("/manufacturing/get.do")
    public List<Object> doManufacturingAllinfoGet(){
        List<Object> result = subDivisionService.doManufacturingAllinfoGet();
        System.out.println("result = " + result);
        return result;
    }

    // 벌크 1개 정보 출력
    @GetMapping("/manufacturing/one/get.do")
    public ManufacturingDto doManufacturingOneInfoGet(@RequestParam int mfno){
        ManufacturingDto manufacturingDto = subDivisionService.doManufacturingOneInfoGet(mfno);
        System.out.println("벌크 1개 manufacturingDto = " + manufacturingDto);
        return manufacturingDto;
    }

    // 품질 검사
    @PutMapping("/confirm.do")
    public int doSubDivisionConfirm(SubDivisionDto subDivisionDto){
        System.out.println("subDivisionDto = " + subDivisionDto);
        int mno = memberService.doLogininfo().getMno();
        int sdno = subDivisionDto.getSdno();
        int sdstate = subDivisionDto.getSdstate();
        return subDivisionService.doSubDivisionConfirm(mno , sdno , sdstate);
    }
}
