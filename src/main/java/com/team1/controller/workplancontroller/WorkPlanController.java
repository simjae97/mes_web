package com.team1.controller.workplancontroller;


import com.team1.model.dto.ManufacturingDto.ManufacturingDto;
import com.team1.model.dto.SubDivisionDto;
import com.team1.model.dto.WorkPlanDto;
import com.team1.model.dto.packagingdto.PackagingDto;
import com.team1.model.entity.WorkPlanEntity;
import com.team1.model.repository.SurveyRepository;
import com.team1.service.SurveyService;
import com.team1.service.manufacturingService.ManufacturingService;
import com.team1.service.workplanservice.WorkPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wp")
public class WorkPlanController {

    @Autowired
    WorkPlanService workPlanService;

    @Autowired
    ManufacturingService manufacturingService;
    @PostMapping("/write/post.do")
    public boolean postWPWriteDo(WorkPlanDto workPlanDto){
        System.out.println("컨트롤러workPlanDto = " + workPlanDto);
        return workPlanService.postWPWriteDo(workPlanDto);
    }


    @GetMapping("/list/get.do")
    public List<WorkPlanDto> findWPList(String orderby){
        System.out.println(orderby+"dddddddd");
        return workPlanService.findWPList(orderby);
    }

    // 박시현
    @GetMapping("/workList/get.do")
    public List<WorkPlanDto> findWorkListGet(){
        System.out.println("WorkPlanController.findWorkListGet");
        return workPlanService.findWorkListGet();
    }

    @GetMapping("/fidsno/get.do")
    public int findSno(int wno){
        System.out.println("임시테스트용"+wno);
        return workPlanService.findSno(wno);
    }

    @PutMapping("/changestate/put.do")
    public boolean putChangeStateDo(@RequestBody WorkPlanDto workPlanDto){
        int wno = workPlanDto.getWno();
        int wstate = workPlanDto.getWstate();
        return workPlanService.putChangeStateDo(wno , wstate);
    }
    @GetMapping("/fidmipno/get.do")
    public int findmipno(int wno){
        return workPlanService.findmipno(wno);
    }

    @GetMapping("/manu/get.do")
    public ManufacturingDto findmap(@RequestParam int mipno){
        System.out.println("\"\" = " + "안녕");
        return manufacturingService.findbymipno(mipno);
    }


    @GetMapping("/sub/get.do")
    public SubDivisionDto findsub(int wno){

        return workPlanService.findsub(wno);
    }


    @GetMapping("/pack/get.do")
    public PackagingDto findpack(int wno){

        return workPlanService.findpack(wno);
    }

}
