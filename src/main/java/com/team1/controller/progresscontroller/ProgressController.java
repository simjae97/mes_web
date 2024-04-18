package com.team1.controller.progresscontroller;

import com.team1.model.dto.ProgressDTO;
import com.team1.service.progressService.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/progress")
public class ProgressController {

    @Autowired
    ProgressService progressService;

    // 작업진행 상황 워크플랜 데이터 가져오기
    @GetMapping("/get.do")
    public List<ProgressDTO> progressGetPlan(){
        System.out.println("ProgressController.progressGetPlan");
        return progressService.progressGetPlan();
    }


    

}
