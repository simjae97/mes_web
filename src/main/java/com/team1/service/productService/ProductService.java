package com.team1.service.productService;

import com.team1.model.dto.ProductDto;
import com.team1.model.entity.MemberEntity;
import com.team1.model.entity.ProductEntity;
import com.team1.model.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public boolean uploadProduct(ProductDto productDto){
        System.out.println(productDto);
        if(productRepository.findByPname(productDto.getPname()) != null){
            System.out.println("중복이있습니다");
            return false;
        }
        ProductEntity productEntity = productRepository.save(productDto.toEntity());
        if (productEntity.getPno() > 0){
            return true;
        }
        return false;
    }


    public List<ProductDto> productDtoList(){
        ProductEntity productEntity = productRepository.findById(1).orElse(null);
        System.out.println(productEntity);
        List<ProductEntity> memberEntityList =productRepository.findAll();
        List<ProductDto> result = memberEntityList.stream().map((e) -> {return e.toDto();}).collect(Collectors.toList());

        return result;
    }
}
