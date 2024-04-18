package com.team1.service.subdivisionservice;

import com.team1.controller.AlertSocekt;
import com.team1.model.dto.ManufacturingDto.ManufacturingDto;
import com.team1.model.dto.MemberDto;
import com.team1.model.dto.SubDivisionDto;
import com.team1.model.entity.ManufacturingEntity;
import com.team1.model.entity.MaterialInputEntity;
import com.team1.model.entity.MemberEntity;
import com.team1.model.entity.SubdivisionEntity;
import com.team1.model.repository.ManufacturingEntityRepository;
import com.team1.model.repository.MemberRepository;
import com.team1.model.repository.SubDivisionRepository;
import com.team1.service.memberserivce.MemberService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.socket.TextMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubDivisionService {
    @Autowired
    private ManufacturingEntityRepository manufacturingEntityRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private SubDivisionRepository subDivisionRepository;
    
    @Autowired
    AlertSocekt alertSocekt;

    // 소분 보고서 작성
    // 반환 0 = 실패 / 1 이상 = 성공
    // 반환 -1 = 로그인정보 없음  / -2 = 해당담당자아님
    @Transactional
    public int doSubDivisionInputPost(int mfno , int failcount , int successcount){
        // 멤버
        MemberDto loginDto = memberService.doLogininfo();
        if ( loginDto == null ) return -1;

        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById( loginDto.getMno() );

        if( !optionalMemberEntity.isPresent() ) return -1;

        MemberEntity memberEntity = optionalMemberEntity.get();
        // 만약 소분담당 또는 관리자 가 아니라면 등록 실패
        if (memberEntity.getPart() != 3 && memberEntity.getPart() != -1) {
            return -1;
        }

        // 벌크
        Optional<ManufacturingEntity> optionalManufacturingEntity = manufacturingEntityRepository.findById(mfno);
        if (!optionalManufacturingEntity.isPresent()) return 0;

        ManufacturingEntity manufacturingEntity = optionalManufacturingEntity.get();

        // insert
        SubdivisionEntity saveSubDivision = subDivisionRepository.save(SubdivisionEntity.builder().build());


        if(saveSubDivision.getSdno() >= 1){
            saveSubDivision.setManufacturingEntity(manufacturingEntity);
            saveSubDivision.setInputmemberEntity(memberEntity);
            saveSubDivision.setFailCount(failcount);
            saveSubDivision.setSuccessCount(successcount);

            // 작업 다 끝난후 검사 완료 메세지 소켓 전송
            try {
                // 제품 이름 과 수량을 소켓으로 전달
                String workName = saveSubDivision.getManufacturingEntity().getMaterialInputEntity().getWorkPlanEntity().getProductEntity().getPname();
                String workCount = String.valueOf(saveSubDivision.getManufacturingEntity().getMaterialInputEntity().getWorkPlanEntity().getWcount());
                String workNo = String.valueOf(saveSubDivision.getManufacturingEntity().getMaterialInputEntity().getWorkPlanEntity().getWno());
                alertSocekt.sendString(new TextMessage(" 생산계획 번호 : "+workNo +" 제품명 : "+workName+" 수량 : " +workCount+ " EA "+"   "+" 소분 등록 완료!!"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            return 1;
        }

        return 0;
    }

    // 소분 모두 출력
    @Transactional
    public List<SubDivisionDto> doSubDivisionAllinfoGet(){
        List<SubdivisionEntity> subdivisionEntityList = subDivisionRepository.findAll();
        List<SubDivisionDto> subDivisionDtoList = new ArrayList<>();
        subdivisionEntityList.forEach((subdivisioninfo)->{
            subDivisionDtoList.add(subdivisioninfo.toDto());
        });
        return subDivisionDtoList;
    }

    // 벌크 모든 정보 출력
    @Transactional
    public List<Object> doManufacturingAllinfoGet(){
        List<Object> manufacturingList = new ArrayList<>();

        List<ManufacturingEntity> manufacturingEntityList = manufacturingEntityRepository.findAll();

        for(int i = 0; i < manufacturingEntityList.size(); i++){
            manufacturingList.add(manufacturingEntityList.get(i));
        }

        return manufacturingList;
    }

    // 벌크 1개 정보 출력
    @Transactional
    public ManufacturingDto doManufacturingOneInfoGet(int mfno){
        ManufacturingDto manufacturingDto = manufacturingEntityRepository.findById(mfno).get().toDto();
        return manufacturingDto;
    }

    // 품질 검사
    // 반환 0 = 실패 / 1 이상 = 성공
    // 반환 -1 = 로그인정보 없음  / -2 = 해당담당자아님
    @Transactional
    public int doSubDivisionConfirm(int mno , int sdno , int sdstate){
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberInfo(mno);
        if(!optionalMemberEntity.isPresent()){
            return -1;
        }
        // 만약 검사자 또는 관리자 가 아니라면 등록 실패
        if (optionalMemberEntity.get().getPart() != 10 && optionalMemberEntity.get().getPart() != -1) {
            return -1;
        }
//        System.out.println("optionalMemberEntity"+optionalMemberEntity);

        SubdivisionEntity subdivisionEntity = subDivisionRepository.findById(sdno).get();

        subdivisionEntity.setCheckmemberEntity(optionalMemberEntity.get());
        subdivisionEntity.setSdstate(sdstate);

        System.out.println("subdivisionEntity"+subdivisionEntity);

        if (subdivisionEntity.getCheckmemberEntity() != null){
            // 작업 다 끝난후 검사 완료 메세지 소켓 전송
            try {
                // 제품 이름 과 수량을 소켓으로 전달
                String workName = subdivisionEntity.getManufacturingEntity().getMaterialInputEntity().getWorkPlanEntity().getProductEntity().getPname();
                String workCount = String.valueOf(subdivisionEntity.getManufacturingEntity().getMaterialInputEntity().getWorkPlanEntity().getWcount());
                String workNo = String.valueOf(subdivisionEntity.getManufacturingEntity().getMaterialInputEntity().getWorkPlanEntity().getWno());
                alertSocekt.sendString(new TextMessage(" 생산계획 번호 : "+workNo +" 제품명 : "+workName+" 수량 : " +workCount+ " EA "+"   "+" 소분 검사 완료!!"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return 1;
        }
        return 0;
    }
}
