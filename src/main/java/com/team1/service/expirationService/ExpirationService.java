package com.team1.service.expirationService;


import com.team1.model.dto.ExpirationDTO;
import com.team1.model.entity.ExpirationEntity;
import com.team1.model.entity.ProductLogEntity;
import com.team1.model.repository.ExpirationRepository;
import com.team1.model.repository.ProductLogRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ExpirationService {

    @Autowired
    ExpirationRepository expirationRepository;

    @Autowired
    ProductLogRepository productlogRepository;
    public List<ExpirationDTO> Expirationfind(){
        return expirationRepository.findAll().stream().map(ExpirationEntity::toDto).collect(Collectors.toList()); //pname 따오게 변경
    }


    @Transactional
    public boolean insertlog(){
        List<ProductLogEntity> result = expirationRepository.findAll().stream().map(e->{
              return ProductLogEntity.builder()
                      .plcount(e.getPlcount())
                      .productEntity(e.getProductEntity())
                      .build();}
        ).collect(Collectors.toList());
        expirationRepository.deleteAll();
        productlogRepository.saveAll(result);
        return true;
    }
}
