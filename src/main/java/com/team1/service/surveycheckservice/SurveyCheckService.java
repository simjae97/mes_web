package com.team1.service.surveycheckservice;

import com.team1.controller.AlertSocekt;
import com.team1.model.dto.MemberDto;
import com.team1.model.dto.surveyCheckDto.SurveyBCheckOutDto;
import com.team1.model.dto.surveyCheckDto.SurveyCheckOutDto;
import com.team1.model.entity.*;
import com.team1.model.repository.*;
import com.team1.service.memberserivce.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.socket.TextMessage;

import javax.imageio.plugins.tiff.TIFFImageReadParam;
import java.util.*;

@Service
public class SurveyCheckService {

    @Autowired
    SurveyRepository surveyRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    WorkPlanEntityRepository workPlanEntityRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    RecipeEntityRepository recipeEntityRepository;
    @Autowired
    SurveyBEntityRepository surveyBEntityRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    RawMateriallogRepository rawMateriallogRepository;

    // 박시현 알림 소켓
    @Autowired
    AlertSocekt alertSocekt;


    // 1. 계량데이터 가져오기
    public Map< Object , Object >  surveyCheckView(){
        return null;
    }

    // state 로 계량 완료 된것만 가져오기
    public List<WorkPlanEntity> roadCheckWorkState(){
        List<WorkPlanEntity> workPlanEntityList = workPlanEntityRepository.findAll();
        List<WorkPlanEntity> workPlans = new ArrayList<>();
        for (int i = 0 ; i < workPlanEntityList.size() ; i++){
            if (workPlanEntityList.get(i).getWstate() == 1){
                workPlans.add(workPlanEntityList.get(i));

            }
        }
        return workPlans;
    }

    // Survey 이용 하여 sno번호 제품명 계량날짜 계량원 상태 가져오기
    public List<SurveyCheckOutDto> roadCheckSurvey(){
        List<SurveyEntity> surveyEntityList =  surveyRepository.findAll();
        List<SurveyCheckOutDto> surveyCheckOutDtoList = new ArrayList<>();
        if (!surveyEntityList.isEmpty()){
            for (int i = 0 ; i < surveyEntityList.size(); i++){
                System.out.println(surveyEntityList.get(i).getCheckmemberEntity());
                SurveyCheckOutDto surveyCheckOutDto = SurveyCheckOutDto.builder()
                        .wno(surveyEntityList.get(i).getWorkPlanEntity().getWno())
                        .sno(surveyEntityList.get(i).getSno())
                        .cdate(surveyEntityList.get(i).getCdate())
                        .udate(surveyEntityList.get(i).getUdate())
                        .sstate(surveyEntityList.get(i).getSstate())
                        .inputmname(surveyEntityList.get(i).getInputmemberEntity().getMname())
                        .pname(surveyEntityList.get(i).getWorkPlanEntity().getProductEntity().getPname())
                        .wcount(surveyEntityList.get(i).getWorkPlanEntity().getWcount())
                        .build();
                surveyCheckOutDto = 레시피(surveyCheckOutDto);
                if (surveyEntityList.get(i).getCheckmemberEntity() != null){
                    surveyCheckOutDto.setCheckmname(surveyEntityList.get(i).getCheckmemberEntity().getMname());
                }
                surveyCheckOutDtoList.add(surveyCheckOutDto);
            }
        }
        return surveyCheckOutDtoList;
    }

    // 테스트 레시피 가져오기
    public SurveyCheckOutDto 레시피( SurveyCheckOutDto surveyCheckOutDto){
        int wno = surveyCheckOutDto.getWno();
        WorkPlanEntity workPlan = workPlanEntityRepository.findBywno(wno);
        int pno = workPlan.getProductEntity().getPno();
        List<RecipeEntity> recipes = recipeEntityRepository.findByPnoSql(pno);

        List<SurveyBCheckOutDto> list = new ArrayList<>();
        for (int i = 0 ; i < recipes.size() ; i++){
            SurveyBCheckOutDto surveyBCheckOutDto = new SurveyBCheckOutDto();
            surveyBCheckOutDto.setRmname(recipes.get(i).getRawMaterialEntity().getRmname());
            surveyBCheckOutDto.setRmno(recipes.get(i).getRawMaterialEntity().getRmno());
            surveyBCheckOutDto.setReamount(recipes.get(i).getReamount() * workPlan.getWcount());
            list.add(surveyBCheckOutDto);
        }
        surveyCheckOutDto.setSurveybList(list);

        return 계량값(surveyCheckOutDto);
    }

    // 계량한 값 가져오기
    public SurveyCheckOutDto 계량값(SurveyCheckOutDto surveyCheckOutDto){
        int wno = surveyCheckOutDto.getWno();

        List<SurveyBEntity> surveyBEntityList = surveyBEntityRepository.findAll();

        for (int i = 0 ; i < surveyBEntityList.size() ; i++){
            for (int j = 0 ; j < surveyCheckOutDto.getSurveybList().size() ; j++){

                if (surveyBEntityList.get(i).getRawMaterialEntity().getRmno() == surveyCheckOutDto.getSurveybList().get(j).getRmno()){
                    surveyCheckOutDto.getSurveybList().get(j).setSbcount(surveyBEntityList.get(i).getSbcount());
                }
            }
        }
        return surveyCheckOutDto;
    }


    // 2. 검사 완료 체크 시 검사 완료자 데이터 저장
    // 반환 정보 :
    // 1 이상 = 성공
    // 0 = 실패
    // -1 로그인 정보가 없음
    // -2 해당담당자가 아님
    public int surveyCheck(int sno  , int state){
        MemberDto memberDto = memberService.doLogininfo();
        // 만약 검사자 또는 관리자 가 아니라면 등록 실패
        if (memberDto.getPart() != 10 && memberDto.getPart() != -1) {
            return -1;
        }
        SurveyEntity survey = surveyGetList(sno);
        WorkPlanEntity workPlan = workPlanGetList(survey.getWorkPlanEntity().getWno());
        List<RecipeEntity> recipeEntityList = recipeEntityList(workPlan.getProductEntity().getPno());
        List<SurveyBEntity> surveyBEntityList = surveyBEntityList(sno);
        boolean result = surveyChack(workPlan, recipeEntityList, surveyBEntityList);
        System.out.println("state = " + state);
        if(state==2) {
            System.out.println("insert messegssadsadsad***");
            if (result) {
                // 계량 체크가 유효성 검사가 모두 성공 했을 시 아래 문 실행.
                survey.setCheckmemberEntity(memberNameCheck(memberDto.getMno()));
                survey.setSstate(state);
                // 해당 워크플랜을 상태 변경
                workPlan.setWstate(2);
                surveyRepository.save(survey);
                // 상태변경한 워크플랜을 DB에 저장
                workPlanEntityRepository.save(workPlan);

//                 return true;
            } else {
                // 계량 검사 유효성 검사 실패시 false 리턴.
                return 0;
            }
            if (result) {
                for (int i = 0; i < surveyBEntityList.size(); i++) {
                    RawMaterialLogEntity rawMaterialLogEntity = RawMaterialLogEntity.builder()
                            .rawMaterialEntity(surveyBEntityList.get(i).getRawMaterialEntity())
                            .rmlcount(surveyBEntityList.get(i).getSbcount() * -1)
                            .build();
                    rawMateriallogRepository.save(rawMaterialLogEntity);
                }
                // 작업 다 끝난후 검사 완료 메세지 소켓 전송
                try {
                    // 제품 이름 과 수량을 소켓으로 전달
                    String workName = workPlan.getProductEntity().getPname();
                    String workCount = String.valueOf(workPlan.getWcount());
                    String workNo = String.valueOf(workPlan.getWno());
                    alertSocekt.sendString(new TextMessage(" 생산계획 번호 : "+workNo +" 제품명 : "+workName+" 수량 : " +workCount+" EA "+"   "+" 계량 검사 완료!!"));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return 1;
            } else {
                return 0;
            }
        }else {// 합격이 아닐경우 (복붙함 수정사항 = workplan state 1로 지정해둠)
            if (result) {
                // 계량 체크가 유효성 검사가 모두 성공 했을 시 아래 문 실행.
                survey.setCheckmemberEntity(memberNameCheck(memberDto.getMno()));
                survey.setSstate(state);
                // 해당 워크플랜을 상태 변경
                workPlan.setWstate(1);
                surveyRepository.save(survey);
                // 상태변경한 워크플랜을 DB에 저장
                workPlanEntityRepository.save(workPlan);

                // return true;
            } else {
                // 계량 검사 유효성 검사 실패시 false 리턴.
                return 0;
            }
            if (result) {
                for (int i = 0; i < surveyBEntityList.size(); i++) {
                    RawMaterialLogEntity rawMaterialLogEntity = RawMaterialLogEntity.builder()
                            .rawMaterialEntity(surveyBEntityList.get(i).getRawMaterialEntity())
                            .rmlcount(surveyBEntityList.get(i).getSbcount() * -1)
                            .build();
                    rawMateriallogRepository.save(rawMaterialLogEntity);
                }
                return 1;
            } else {
                return 0;
            }

        }
    }

    // mno 로 사원 엔티티 가져와서 리턴
    public MemberEntity memberNameCheck(int mno){
        // mno 로 리포지토리 접근하여 사원 Entity 가져오기
        Optional<MemberEntity> memberEntity = memberRepository.findById(mno);
        // 엔티티가 존재하는지 체크
        if (memberEntity.isPresent()){
            // 엔티티 존재시 가져온 엔티티 겟 
            MemberEntity member = memberEntity.get();
            // 사원 이름 리턴
            return member;
        }
        return null;
    }

    // survey 테이블 데이터 가져오기
    public SurveyEntity surveyGetList(int sno){
        List<SurveyEntity> surveyEntityList = surveyRepository.findAll();
        SurveyEntity survey = null;
        if (surveyEntityList != null){
            for (int i = 0 ; i < surveyEntityList.size() ; i++){
                if (surveyEntityList.get(i).getSno() == sno){
                    survey = surveyEntityList.get(i);
                }
            }
        }
        return survey;
    }

    // workplan 테이블 데이터 가져오기
    // workPlanEntity=WorkPlanEntity(wno=1, wcount=1000 wstate=0, productEntity=ProductEntity(pno=1, pname=수분크림))
    public WorkPlanEntity workPlanGetList(int wno){
        List<WorkPlanEntity> workPlanEntityList = workPlanEntityRepository.findAll();
        WorkPlanEntity workPlan = null;
        if (workPlanEntityList != null){
            for (int i = 0 ; i < workPlanEntityList.size() ; i++){
                if (workPlanEntityList.get(i).getWno() == wno){
                    workPlan = workPlanEntityList.get(i);
                }
            }
        }
        return workPlan;
    }

    // Product 테이블 데이터 가져오기
    // WorkPlanEntity(wno=1, wcount=1000, wstarttime=2024-03-20T10:00:12.123456, wendtime=2024-03-30T10:00:12.123456, wstate=0, productEntity=ProductEntity(pno=1, pname=수분크림))
    public ProductEntity productEntityList(int pno){
        List<ProductEntity> productEntityList = productRepository.findAll();
        ProductEntity product = null;
        if (productEntityList != null){
            for (int i = 0 ; i < productEntityList.size() ; i++){
                if (productEntityList.get(i).getPno() == pno){
                    product = productEntityList.get(i);
                }
            }
        }
        return product;
    }

    // recipe 테이블 데이터 가져오기
    public List<RecipeEntity> recipeEntityList(int pno){
        List<RecipeEntity> recipeEntityList = recipeEntityRepository.findAll();
        List<RecipeEntity> recipes = new ArrayList<>();
        if (recipeEntityList != null){
            for (int i = 0 ; i < recipeEntityList.size() ; i++){
                if (recipeEntityList.get(i).getProductEntity().getPno() == pno){
                    recipes.add(recipeEntityList.get(i));
                }
            }
        }
        return recipes;
    }

    // SurveyB 테이블 데이터 가져오기
    public List<SurveyBEntity> surveyBEntityList(int sno){
        List<SurveyBEntity> surveyBEntityList = surveyBEntityRepository.findAll();
        List<SurveyBEntity> surveyBs = new ArrayList<>();
        if (surveyBEntityList != null){
            for (int i = 0 ; i < surveyBEntityList.size() ; i++){
                if (surveyBEntityList.get(i).getSurveyEntity().getSno() == sno){
                    surveyBs.add(surveyBEntityList.get(i));
                }
            }
        }
        return surveyBs;
    }

    // 원자재 계량 비교
    public boolean surveyChack(WorkPlanEntity workPlan ,List<RecipeEntity> recipeEntityList , List<SurveyBEntity> surveyBEntityList ){
        List<Boolean> result = new ArrayList<>();
        int 수량 = workPlan.getWcount();

        Map<Integer , Double> map1= new HashMap<>();
        Map<Integer , Double> map2 = new HashMap<>();


        // 레시피 MAP 에 비교를 위해 키 값 저장
        for (int i = 0 ; i < recipeEntityList.size() ; i++){
            map1.put(recipeEntityList.get(i).getRawMaterialEntity().getRmno(), (double) recipeEntityList.get(i).getReamount() * 수량);
        }
        // 실제 계량한값 가져와 MAP 에 키 값 저장
        for (int i = 0 ; i < surveyBEntityList.size(); i++){
            // 계량한거 가져오기
            map2.put(surveyBEntityList.get(i).getRawMaterialEntity().getRmno() , (double) surveyBEntityList.get(i).getSbcount());
        }

        // 맵의 크기가 같은지 검증
        if (map1.size() != map2.size()) {
            return false;
        }

        // 레시피 MAP 에 +- 1% 적용 하여 비교
        // 맵의 모든 키-값 쌍에 대해 반복
        for (Map.Entry<Integer, Double> entry : map1.entrySet()) {
            double num = entry.getValue() * 0.01;
            Integer key = entry.getKey();
            Double value = entry.getValue();

            // 레시피와 계량 값 비교 후 Boolean 배열에 저장
            if (map2.get(key) >= entry.getValue() - num && map2.get(key) <= entry.getValue() + num ){
                result.add(true);
            }else {
                result.add(false);
            }
        }


        // 비교값을 Boolean 배열에 넣고 false 가 있는지 비교 있으면 검사 불합격 
        for (int i = 0 ; i < result.size() ; i++){
            if (!result.get(i)){
                return false;
            }
        }
        return true;
    }

}
