package com.team1.service;

import com.team1.model.dto.ChartDTO;
import com.team1.model.dto.ProductLogDto;
import com.team1.model.entity.PackagingEntity;
import com.team1.model.entity.ProductEntity;
import com.team1.model.entity.ProductLogEntity;
import com.team1.model.repository.PackagingRepository;
import com.team1.model.repository.ProductLogRepository;
import com.team1.model.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductLogService {



    @Autowired
    private ProductLogRepository productLogRepository;

    @Autowired
    private PackagingRepository packagingRepository;

    @Autowired
    ProductRepository productRepository;

    @Transactional
    public void productLogDtos(int pgcount, int pno ){
        System.out.println("제품로그 서비스");
        List<PackagingEntity> packagingEntityList = packagingRepository.findByAllProduct();

//////            int pgcount = packagingEntityList.get(i).getPgcount();
//////            int pno = packagingEntityList.get(i).getSubdivisionEntity().getManufacturingEntity().getMaterialInputEntity().getProductEntity().getPno();
////            ProductEntity productentity = productRepository.findById(pno).get(); //pack안에 있는 프로덕트 entity가져오기
////            ProductLogEntity productlogEntity =ProductLogEntity.builder().productEntity(productentity).plcount(pgcount).build();
//            productLogRepository.save(productlogEntity);

//        ProductLogEntity productLogEntity = productLogRepository.save( pgcount , pno);
        ProductLogEntity productLogEntity = productLogRepository.save(ProductLogEntity.builder().build());
        productLogEntity.setPlcount(pgcount);
        productLogEntity.setProductEntity(productRepository.findById(pno).get());
//
    }

    public List<Map<Object,Object>> remaining(){
        return productLogRepository.findremaining();
    }

    public List<Map<Object,Object>> findlogWeek(){
        return productLogRepository.findlogWeek();
    }

    public List<Map<Object,Object>> findlogMonth(){
        return productLogRepository.findlogMonth();
    }

    public List<ProductLogDto> findlog(int pno){ return productLogRepository.findlog(pno).stream().map(ProductLogEntity::toDto).collect(Collectors.toList());}
}
