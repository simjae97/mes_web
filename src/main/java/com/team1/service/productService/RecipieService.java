package com.team1.service.productService;

import com.team1.model.dto.RecipeDto;
import com.team1.model.entity.ProductEntity;
import com.team1.model.entity.RawMaterialEntity;
import com.team1.model.entity.RecipeEntity;
import com.team1.model.repository.ProductRepository;
import com.team1.model.repository.RawMeterailRepository;
import com.team1.model.repository.RecipeREpositorty;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipieService {

    @Autowired
    RecipeREpositorty recipeREpositorty;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    RawMeterailRepository rawMeterailRepository;

    public List<RecipeDto> findRecipieListAll(){
        List< RecipeEntity> result  = recipeREpositorty.findAll();
        return result.stream().map(RecipeEntity::toDto).collect(Collectors.toList());
    }

    public List<RecipeDto> findRecipieList(int pno){
        List< RecipeEntity> result  = recipeREpositorty.findByPnoSQL(pno);
        result.forEach(e->{
            System.out.println(e);
        });
//        List<RecipeDto> result2 = new ArrayList<>();
//        for (RecipeEntity recipeEntity : result) {
//            RecipeDto recipeDto = recipeEntity.toDto();
//            result2.add(recipeDto);
//        }

        return result.stream().map(RecipeEntity::toDto).collect(Collectors.toList());
    }
    @Transactional
    public boolean insertRecipielist(List<RecipeDto> recipeDtos){
        for (RecipeDto i : recipeDtos) {

            RecipeEntity result = i.toEntity();
            ProductEntity productEntity =  productRepository.findById(i.getPno()).get();

            RawMaterialEntity rawMaterialEntity =  rawMeterailRepository.findById(i.getRmno()).get();
            RecipeEntity result2 = recipeREpositorty.save(result);
            if (result2 != null){
                result2.setRawMaterialEntity(rawMaterialEntity);
                result2.setProductEntity(productEntity);
//                result2.setProductEntity(); 제품fk 설정하기
//                result2.setRawMaterialEntity(); 원자재fk 설정하기
            }
        }
        
        return false;
    }
    @Transactional
    public boolean productPost(RecipeDto recipeDto){
        System.out.println("ddddddddd"+recipeDto.getPno());
        System.out.println(recipeDto.getRmno());
        RecipeEntity recipeEntity2 = recipeREpositorty.findByRmnoandPnoSQl(recipeDto.getRmno(),recipeDto.getPno());
        System.out.println(recipeEntity2);
        if(recipeEntity2 != null){
            recipeEntity2.setReamount(recipeEntity2.getReamount()+recipeDto.getReamount());
            return true;
        }
        else{
            RecipeEntity recipeEntity =recipeREpositorty.save(recipeDto.toEntity());
            if(recipeEntity.getReno() >= 1 ){
                System.out.println("안녕안녕");
                recipeEntity.setProductEntity(productRepository.findById(recipeDto.getPno()).get());
                recipeEntity.setRawMaterialEntity(rawMeterailRepository.findById(recipeDto.getRmno()).get());
                return true;
            }
        }

        return false;
    }
}
