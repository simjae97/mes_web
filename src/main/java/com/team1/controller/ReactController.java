package com.team1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReactController {

    @GetMapping( value = {"/" , "/c" , "/product" , "/RM" , "/RM/log" , "/material/input" , "/subdivision" , "/survey/survey" , "/survey/plan" , "/product/recipie/get" , "/wp/list" , "/wp/write" , "/wp/report" , "/manufacturing/info" , "/packaging" , "/expiration" , "/member/List" , "/chart" , "/Progress/view" , "/product/log/list"})
    public String reactForward(){
        return "forward:/index.html";
    }


}
