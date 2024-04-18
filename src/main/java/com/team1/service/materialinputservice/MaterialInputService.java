package com.team1.service.materialinputservice;

import com.team1.controller.AlertSocekt;
import com.team1.model.dto.MaterialInputDto;
import com.team1.model.dto.MemberDto;
import com.team1.model.dto.SurveyBDto;
import com.team1.model.dto.SurveyDto;
import com.team1.model.entity.*;
import com.team1.model.repository.*;
import com.team1.service.memberserivce.MemberService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MaterialInputService {

    @Autowired
    SurveyRepository surveyRepository;
    @Autowired
    MaterialInputRepository materialInputRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    WorkPlanEntityRepository workPlanEntityRepository;
    @Autowired
    AlertSocekt alertSocekt;

    @Transactional
    // 0 실패
    // 1 이상 성공
    // -1 해당 업무 담당자 아님
    // -2 로그인 정보가 없음
    public int doInputPost(int sno){
        System.out.println("MaterialInputService.doInputPost");
        System.out.println("sno = " + sno);

        MemberDto loginDto = memberService.doLogininfo();
        if ( loginDto == null ) return -2;

        // 1. 로그인된 회원 엔티티 찾기
        Optional< MemberEntity > optionalMemberEntity = memberRepository.findById( loginDto.getMno() );
        // 2. 찾은 엔티티가 존재하지 않으면
        if( !optionalMemberEntity.isPresent() ) return -2;
        // 3. 엔티티 꺼내기
        MemberEntity memberEntity = optionalMemberEntity.get();

        // 만약 검사자 또는 관리자 가 아니라면 등록 실패
        if (memberEntity.getPart() != 2 && memberEntity.getPart() != -1) {
            return -1;
        }


        Optional<SurveyEntity> optionalSurveyEntity = surveyRepository.findById( sno );
        if (!optionalSurveyEntity.isPresent()) return 0;

        SurveyEntity surveyEntity = optionalSurveyEntity.get();
        System.out.println("surveyEntity = " + surveyEntity);
        System.out.println("optionalSurveyEntity = " + optionalSurveyEntity);
        ProductEntity optionalProductEntity = productRepository.findBySnoSQL( sno );
        System.out.println("방금찍은 : optionalProductEntity = " + optionalProductEntity);
        Optional<WorkPlanEntity> optionalWorkPlanEntity = workPlanEntityRepository.findById(sno);

            // insert
        MaterialInputEntity saveMaterialInput
                = materialInputRepository.save( MaterialInputEntity.builder().build() );

        if(saveMaterialInput.getMipno() >= 1){
            saveMaterialInput.setSurveyEntity( surveyEntity );
            saveMaterialInput.setProductEntity( optionalProductEntity );
            saveMaterialInput.setInputmemberEntity( memberEntity );
            saveMaterialInput.setWorkPlanEntity(optionalWorkPlanEntity.get());

        // 인풋넘버 넣는곳
            // 작업 다 끝난후 검사 완료 메세지 소켓 전송
            try {
                // 제품 이름 과 수량을 소켓으로 전달
                String workName = saveMaterialInput.getWorkPlanEntity().getProductEntity().getPname();
                String workCount = String.valueOf(saveMaterialInput.getWorkPlanEntity().getWcount());
                String workNo = String.valueOf(saveMaterialInput.getWorkPlanEntity().getWno());
                alertSocekt.sendString(new TextMessage(" 생산계획 번호 : "+workNo +" 제품명 : "+workName+" 수량 : " +workCount+" EA "+ "   "+" 투입 완료!!"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return 1;
        }
        return 0;
    }

    @Transactional
    public List<MaterialInputDto> doInputAllInfoGet(){
        List<MaterialInputEntity> materialInputMapList = materialInputRepository.findAll();
        List<MaterialInputDto> materialInputDtoList = new ArrayList<>();
        materialInputMapList.forEach((matrialInfo)->{
            materialInputDtoList.add(matrialInfo.toDto());
        });
        return materialInputDtoList;
    }



    @Transactional
    public List<Map<Object,Object>> doInputInfoGet(int sno){
//        System.out.println("MaterialInputController.doInputInfoGet");
//        List<MaterialInputEntity> result = materialInputRepository.findAll();
//
//        System.out.println("result = " + result);
//        // Entity를 Dto로 변환한다
//        List<MaterialInputDto> materialInputDtoList = new ArrayList<>();
//            // 1. 꺼내온 entity를 순회한다
//        for (int i = 0; i < result.size(); i++) {
//            // 2.하나씩 entity를 꺼낸다
//            MaterialInputEntity materialInputEntity = result.get(i);
//                result.get(i).getSurveyBEntity().getSbno();
//            System.out.println("방금막쓴sno = " + materialInputEntity);
//
//            // 3. 해당 entity를 dto로 변환한다
//            MaterialInputDto materialInputDto = materialInputEntity.toDto();
//            // 4. 변환된 dto를 리스트에 담는다
//            materialInputDtoList.add(materialInputDto);
//
//        }

        return materialInputRepository.findByHard(sno);
    }

    public List<Object> surveyDtoList(){

        List<Object> surveyDtoList = new ArrayList<>();

        List<SurveyEntity> surveyEntityList = surveyRepository.findAll();

        for (int i = 0; i < surveyEntityList.size() ; i++) {
            surveyDtoList.add( surveyEntityList.get(i).toDto() );
        }
        return surveyDtoList;
    }





}

//        Optional<MaterialInputEntity> test = materialInputRepository.findBySno(1);
//        Optional<MaterialInputEntity> test2 = materialInputRepository.findByHard(1);
//        System.out.println("테스트 : "+test);
//        System.out.println("테스트2"+test2);