package com.team1.model.repository;

import com.team1.model.entity.MaterialInputEntity;
import com.team1.model.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,Integer> {
    @Query(value = "select * from member where mno = :mno" , nativeQuery = true)
    Optional<MemberEntity> findByMemberInfo(int mno);

    boolean existsByMid(String mid);

    @Query(value = "select * from member where mid = :mid and mpw = :mpw",nativeQuery = true)
    MemberEntity findByLoginSql(String mid , String mpw);
}
