package com.team1.service.memberserivce;

import com.team1.model.dto.MemberDto;
import com.team1.model.entity.MemberEntity;
import com.team1.model.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    MemberRepository memberRepository;

    //아이디 중복검사
    public boolean getfindMid(String mid){
        System.out.println("mid = " + mid);
        boolean result;
        result = memberRepository.existsByMid(mid);
        System.out.println("result = " + result);
        return result;
    };

    // 회원가입 기능
    @Transactional
    public int doSignupPost( MemberDto memberDto){
        System.out.println("memberDto = " + memberDto);
        boolean result = getfindMid(memberDto.getMid());
        // 아이디 중복이 있을시 true
        if(result){
            return -1;
        }
        // 중복이 없을 시 사원 등록 진행
        MemberEntity savedEntity = memberRepository.save(memberDto.toEntity());

        // 엔티티 생성이 되었는지 아닌지 확인 (PK)
        if(savedEntity.getMno()>0) {
            System.out.println("savedEntity.getMno() = " + savedEntity.getMno());
            System.out.println("savedEntity.getMname() = " + savedEntity.getMname());
            return 1;
        }
        // 아이디 중복이 없었지만 엔티티 생성은 실패 했을 경우.
        return -2;
    }

    // 모든 사원 가져오기
    @Transactional(readOnly = true) // 읽기전용
    public List<MemberDto> doAllReadMember(){
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDto> memberDtoList = new ArrayList<>();
        System.out.println("memberEntityList = " + memberEntityList);
        for (MemberEntity memberEntity : memberEntityList) {
            memberDtoList.add(memberEntity.toDto());
        }
        return memberDtoList;
    }

    //* 로그인 했다는 증거/기록
    @Autowired private HttpServletRequest request;

    // 로그인
    public boolean doLoginPost(MemberDto memberDto){ //로그인

        MemberEntity memberEntity = memberRepository.findByLoginSql(memberDto.getMid(), memberDto.getMpw());
        if(memberEntity == null){
            return false;
        }
        System.out.println(memberEntity);
        request.getSession().setAttribute("logindto",memberEntity.toDto());
        return true;
    }

    // 로그아웃
    public boolean doLogoutGet(){
        request.getSession().setAttribute("logindto",null);
        return true;
    }

    // 현재 로그인 회원정보 호출 (세션 값 반환/호출)
    public MemberDto doLogininfo(){
        Object object = request.getSession().getAttribute("logindto");
        if(object != null){
            return (MemberDto) object;
        }
        return null;
    }

    // 회원 탈퇴
    public boolean deleteAccount(int mno){
        System.out.println("MemberService.deleteAccount");
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberInfo(mno);
        System.out.println("memberEntity = " + optionalMemberEntity);
        if (optionalMemberEntity.isPresent()){
            // 리포지토리에서 멤버 엔티티 삭제
            memberRepository.deleteById(optionalMemberEntity.get().getMno());
            return true;
        }
        return false;
    }

}
/*
    Optional 클래스
        -해당 객체가 null일수도있고 아닐수도있다

 */