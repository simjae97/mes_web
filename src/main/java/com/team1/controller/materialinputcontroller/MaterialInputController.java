package com.team1.controller.materialinputcontroller;

import com.team1.model.dto.MaterialInputDto;
import com.team1.model.dto.SurveyBDto;
import com.team1.model.dto.SurveyDto;
import com.team1.model.entity.MaterialInputEntity;
import com.team1.service.materialinputservice.MaterialInputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/material")
public class MaterialInputController {

    @Autowired
    MaterialInputService materialInputService;

    // 0 실패
    // 1 이상 성공
    // -1 해당 업무 담당자 아님
    // -2 로그인 정보가 없음
    @PostMapping("/input/post.do")
    public int doInputPost(@RequestParam int sno){
        System.out.println("MaterialInputController.doInputPost");
        System.out.println("sno = " + sno);
        int result =materialInputService.doInputPost(sno);
        System.out.println("result**** = " + result);
        return result;
    }

    @GetMapping("/input/allinfo/get.do")
    public List<MaterialInputDto> doInputAllInfoGet(){
        System.out.println("MaterialInputController.doInputInfoGet");

        return materialInputService.doInputAllInfoGet();

    }

    @GetMapping("/input/info/get.do")
    public List<Map<Object,Object>> doInputInfoGet(@RequestParam int sno ){
        System.out.println("MaterialInputController.doInputInfoGet");
        System.out.println("sno = " + sno);
        return materialInputService.doInputInfoGet(sno);
    }

    // sno 정보 뽑아오기
    @GetMapping("/surveyinfo.do")
    public List<Object> surveyDtoList(){

        List<Object> result = materialInputService.surveyDtoList();
        System.out.println("result = " + result);

        return result;
    }








}
