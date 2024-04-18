package com.team1.controller;

import com.team1.model.dto.MaterialInputDto;
import com.team1.model.entity.MaterialInputEntity;
import com.team1.service.MaterialInputConfirmService;
import com.team1.service.memberserivce.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/materialinput")
public class MaterialInputConfirmController {
    @Autowired
    private MaterialInputConfirmService materialInputConfirmService;
    @Autowired
    private MemberService memberService;

    @PutMapping("/confirm.do")
    public int putMaterialInputConfirm(MaterialInputDto materialInputDto){
        System.out.println("materialInputDto = " + materialInputDto);
        int mno = memberService.doLogininfo().getMno();
        int mipno = materialInputDto.getMipno();
        int mipstate = materialInputDto.getMipstate();
        return materialInputConfirmService.putMaterialInputConfirm(mno , mipno , mipstate);
    }


    @GetMapping("/confirm/log.do")
    public List<MaterialInputDto> getMaterialInputConfirmLog(){
        System.out.println("material 정보" + materialInputConfirmService.getMaterialInputConfirmLog());
        return materialInputConfirmService.getMaterialInputConfirmLog();
    }
}

/*
    1
    수주
    번호,거래처,제품명,제품수량,수주받은날짜,생산완료날짜

    생산계획
    번호,수주번호,제품명,제품수량,작업지시날짜,생산완료날짜,작업진행상태

    계량
    번호,생산계획번호,제품명,제품수량,원료A명,원료A수량,원료B명,원료B수량,계량완료날짜,담당자이름,검사상태 + 검사자이름,검사완료날짜,제품명,원료A수량,원료B수량

    투입
    번호,생산계획번호,계량번호,제품명,제품수량,원료A투입량,원료B투입량,투입완료날짜,담당자이름,검사상태 + 검사자이름,검사완료날짜,(생산계획)제품명,(계량)원료A투입량,(계량)원료B투입량
*/
