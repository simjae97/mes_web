package com.team1.controller.expirationController;


import com.team1.model.dto.ExpirationDTO;
import com.team1.model.entity.ProductEntity;
import com.team1.model.entity.ProductLogEntity;
import com.team1.service.expirationService.ExpirationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/expiration")
public class ExpirationController {

    @Autowired
    ExpirationService expirationService;

    @GetMapping("/list/get.do")
    public List<ExpirationDTO> Expirationfind(){
        return expirationService.Expirationfind();
    }

    @PostMapping("/log/post.do")
    public boolean insertlog() {
        return expirationService.insertlog();
    }
}
