package com.team1.controller;

import com.team1.model.dto.ChartDTO;
import com.team1.model.dto.ProductLogDto;
import com.team1.service.ProductLogService;
import com.team1.service.productService.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/productlog")
public class ProductLogController {

    @Autowired
    private ProductLogService productLogService;

    @PostMapping("/post.do")
    public void productLogDtos( int pgcount, int pno){
        System.out.println("제품로그 컨트롤러");
        System.out.println("★★★pgcount = " + pgcount +"pno"+ pno);
        productLogService.productLogDtos(pgcount , pno);
    }

    @GetMapping("/chart/remaining")
    public List<Map<Object,Object>> remaining(){

        return  productLogService.remaining();
    }

    @GetMapping("/chart/log/week")
    public List<Map<Object,Object>> findlogWeek(){
        return productLogService.findlogWeek();
    }

    @GetMapping("/chart/log/month")
    public List<Map<Object,Object>> findlogMonth(){
        return productLogService.findlogMonth();
    }

    @GetMapping("/list/get.do")
    public List<ProductLogDto> findlog(int pno){
        return productLogService.findlog(pno);
    }
}
