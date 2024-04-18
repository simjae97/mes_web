package com.team1.model.repository;

import com.team1.model.dto.RecipeDto;
import com.team1.model.entity.MemberEntity;
import com.team1.model.entity.RawMaterialEntity;
import com.team1.model.entity.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RecipeREpositorty extends JpaRepository<RecipeEntity,Integer> {

    @Query(value = "select r.* from recipe as r inner join rawmaterial as rm on r.rmno = rm.rmno join product p on p.pno = r.pno where r.rmno = :rmno and p.pno = :pno "
            ,nativeQuery = true)
    RecipeEntity findByRmnoandPnoSQl(int rmno , int pno);

//    List<RecipeEntity> findByRmno(int rmno); //원자재 이름으로 들어가는 제품들 가져오기
    @Query(value = "select r.* from recipe as r inner join rawmaterial as rm on r.rmno = rm.rmno where rmname = :rmname;",nativeQuery = true)
    List<RecipeEntity> findByRmnameSQL(String rmname);
    //
//    List<RecipeEntity> findByPno(int pno); //제품이름으로 들어가는 원자재들 가져오기 sql로 변경
    @Query(value = "select r.* from recipe as r inner join product as p on r.pno = p.pno where pname =:pname",nativeQuery = true)
    List<RecipeEntity> findByPnameSQL(String pname);


    @Query(value = "select r.* from recipe as r inner join product as p on r.pno = p.pno where r.pno =:pno",nativeQuery = true)
    List<RecipeEntity> findByPnoSQL(int pno);

}
