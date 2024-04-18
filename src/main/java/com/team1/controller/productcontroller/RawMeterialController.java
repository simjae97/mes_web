package com.team1.controller.productcontroller;

import com.team1.model.dto.RawMaterialLogDto;
import com.team1.model.dto.RawMaterrialDto;
import com.team1.service.productService.RawMeterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/RM")
public class RawMeterialController {
    @Autowired
    RawMeterialService rawMeterialService;

    @PostMapping("/post.do")
    public boolean doPostrm(RawMaterrialDto rawMaterrialDto){

        return rawMeterialService.doPostRawMeterial(rawMaterrialDto);
    }

    @GetMapping("/get.do")
    public List<RawMaterrialDto> doFindRawMeterialList(){
        return rawMeterialService.doFindRawMeterialList();
    }

    @PostMapping("/log/post.do")
    public boolean doPostRmLog(RawMaterialLogDto rawMaterialLogDto){
        System.out.println(rawMaterialLogDto);
        return rawMeterialService.doPostRmLog(rawMaterialLogDto);
    }

    @GetMapping("/log/get.do")
    public List<RawMaterialLogDto> doFindRmLog(int rmno){
        List<RawMaterialLogDto> result = rawMeterialService.doFindRmLog(rmno);
//        result.forEach((e)->{
//            System.out.println(e.cdate);
//        } );
        return result;
    }

    @GetMapping("/log/count/get.do")
    public List<Map<Object,Object>> doFindRmCount(){
        return rawMeterialService.doFindRmCount();
    }

}
