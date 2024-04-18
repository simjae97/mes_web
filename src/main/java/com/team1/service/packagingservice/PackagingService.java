package com.team1.service.packagingservice;

import com.team1.controller.AlertSocekt;
import com.team1.model.dto.MemberDto;
import com.team1.model.dto.SubDivisionDto;
import com.team1.model.dto.packagingdto.PackagingDto;
import com.team1.model.entity.MemberEntity;
import com.team1.model.entity.PackagingEntity;
import com.team1.model.entity.SubdivisionEntity;
import com.team1.model.entity.SurveyEntity;
import com.team1.model.repository.MemberRepository;
import com.team1.model.repository.PackagingRepository;
import com.team1.model.repository.SubDivisionRepository;
import com.team1.service.memberserivce.MemberService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.socket.TextMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PackagingService {
    @Autowired
    private PackagingRepository packagingRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private SubDivisionRepository subDivisionRepository;
    @Autowired
    AlertSocekt alertSocekt;

    public List<PackagingDto> doPackInfoGet() {

        List<PackagingDto> list = new ArrayList<>();

        List<PackagingEntity> packagingEntityList = packagingRepository.findAll();

        for (int i = 0 ; i < packagingEntityList.size() ; i++){
            list.add( packagingEntityList.get(i).toDto() );
        }

        return list;
    }

    public SubDivisionDto doSubdivisionInfoGet(int sdno){
        System.out.println("PackagingController.doSubdivisionInfoGet");
        System.out.println("sdno = " + sdno);
        SubDivisionDto subDivisionDto = subDivisionRepository.findById(sdno).get().toDto();
        return subDivisionDto;
    }

    @Transactional
    // 반환 0 = 실패 / 1이상 = 성공 / -1 = 로그인정보가 없음  / -2 권한이 없는작업
    public int doMemberPost(int sdno , int pgbox , int pgcount ){
//        System.out.println("PackagingService.doMemberPost");
//        System.out.println("★pgcount = " + pgcount);
        MemberDto loginDto = memberService.doLogininfo();
        if ( loginDto == null ) return -1;

        // 1. 로그인된 회원 엔티티 찾기
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById( loginDto.getMno() );
        // 2. 찾은 엔티티가 존재하지 않으면
        if( !optionalMemberEntity.isPresent() ) return -1;
        // 3. 엔티티 꺼내기
        MemberEntity memberEntity = optionalMemberEntity.get();

        // 만약 포장인원 또는 관리자 가 아니라면 등록 실패
        if (memberEntity.getPart() != 3 && memberEntity.getPart() != -1) {
            return -1;
        }

        SubdivisionEntity subdivisionEntity = subDivisionRepository.findById(sdno).get();


        PackagingEntity savePackaging = packagingRepository.save( PackagingEntity.builder().build() );

        if (savePackaging.getPgno() >= 1){
            savePackaging.setMemberEntity( memberEntity );
            savePackaging.setSubdivisionEntity( subdivisionEntity );
            savePackaging.setPgcount( pgcount );
            // 작업 다 끝난후 검사 완료 메세지 소켓 전송
            try {
                // 제품 이름 과 수량을 소켓으로 전달
                String workName = savePackaging.getSubdivisionEntity().getManufacturingEntity().getMaterialInputEntity().getWorkPlanEntity().getProductEntity().getPname();
                String workCount = String.valueOf(savePackaging.getSubdivisionEntity().getManufacturingEntity().getMaterialInputEntity().getWorkPlanEntity().getWcount());
                String workNo = String.valueOf(savePackaging.getSubdivisionEntity().getManufacturingEntity().getMaterialInputEntity().getWorkPlanEntity().getWno());
                alertSocekt.sendString(new TextMessage(" 생산계획 번호 : "+workNo +" 제품명 : "+workName+" 수량 : " +workCount+" EA "+ "   "+" 포장 완료!!"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return 1;
        }

        return 0;
    }

    public List<Object> subdivisionDtoList(){

        List<Object> subdivisionDtoList = new ArrayList<>();

        List<SubdivisionEntity> subdivisionEntityList = subDivisionRepository.findAll();

        for (int i = 0 ; i < subdivisionEntityList.size() ; i++){
            subdivisionDtoList.add( subdivisionEntityList.get(i).toDto() );
        }


        return subdivisionDtoList;
    }

}
