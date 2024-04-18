package com.team1.service;

import com.team1.controller.AlertSocekt;
import com.team1.model.dto.*;
import com.team1.model.dto.survetDto.SurveyInsertDto;
import com.team1.model.dto.survetDto.SurveyPlanInfoDto;
import com.team1.model.entity.*;
import com.team1.model.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.socket.TextMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SurveyService {

    @Autowired
    private WorkPlanEntityRepository workPlanEntityRepository;
    @Autowired
    private RecipeEntityRepository recipeEntityRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private RawMaterialEntityRepository rawMaterialEntityRepository;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SurveyRepository surveyRepository;
    @Autowired
    private SurveyBRepository surveyBRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    AlertSocekt alertSocekt;

    // workplan 정보 가져오기
    public List<WorkPlanDto> workPlanDtoList (){
        // 반환할꺼 담을 DTO 리스트
        List<WorkPlanDto> workPlanDtoList = new ArrayList<>();

        // JPA에서 데이터 호출하기
        List<WorkPlanEntity> workPlanEntityList = workPlanEntityRepository.findAll();

        for (int i = 0; i < workPlanEntityList.size(); i++) {
            workPlanDtoList.add(workPlanEntityList.get(i).toDto());
        }
        return workPlanDtoList;
    }

    // workplan 식별번호로 정보 가져오기
    public WorkPlanDto workPlanDto (int wno){


        // JPA에서 데이터 호출하기
        WorkPlanEntity workPlanEntity = workPlanEntityRepository.findBywno(wno);

        return workPlanEntity.toDto();
    }

    // 1. WorkPlan 식별번호로 만들제품 파악
    // 2. 제품에 들어가는 원재료 를 파악
    // 3. 반환해야하는것 = 제품명 / 수량 / 원재료종류수량 / 각 원재료별 필요한수량(수량*원재료비율 이건 뷰에서 계산하는걸로)
    public SurveyPlanInfoDto surveyClilckDo(int wno){
        // 1. 반환할 객체 만들어두기
        SurveyPlanInfoDto surveyPlanInfoDto = new SurveyPlanInfoDto();

        // 2. 반환할거에 값 넣기
            // 2-1. 식별번호에 해당하는 workPlanDto 찾아서 넣기
                // 해당 workPlanDto 호출
        WorkPlanEntity workPlanEntity = workPlanEntityRepository.findBywno(wno);
        WorkPlanDto workPlanDto = workPlanEntity.toDto(); // DTO로 변환
//        System.out.println("workPlanDto = " + workPlanDto);
        surveyPlanInfoDto.setWorkPlanDto(workPlanDto); // 추가

            // 2-2. workPlan 에 해당하는 제품 레시피 찾아서 넣기
        // 레시피 찾아오기
        List<RecipeEntity> recipeEntityList = recipeEntityRepository.findByPnoSql(workPlanDto.getPno());
        List<RecipeDto> recipeDtoList = new ArrayList<>();
        // 찾은 엔티티가 존재하지 않으면 실패
        if(recipeEntityList == null)return null;
        for (int i = 0; i < recipeEntityList.size(); i++) {
            RecipeDto dto = recipeEntityList.get(i).toDto();
            recipeDtoList.add(dto);
            for (int j = 0; j < recipeEntityList.size(); j++) {
                // 제품 DTO 찾아오기
                Optional<ProductEntity> optionalProductEntity = productRepository.findById(workPlanDto.getPno());
                if(!optionalProductEntity.isPresent())return null;
                dto.setProductDto(optionalProductEntity.get().toDto());

                // 원자재 DTO 찾아오기
                List<RawMaterialEntity> rawMaterialEntityList = rawMaterialEntityRepository.findByPnoSql(workPlanDto.getPno());
                List<RawMaterrialDto> rawMaterrialDtoList = new ArrayList<>();
                for (int k = 0; k < rawMaterialEntityList.size(); k++) {
                    RawMaterrialDto rawMaterrialDto = rawMaterialEntityList.get(k).toDto();
                    rawMaterrialDtoList.add(rawMaterrialDto);
                }
                dto.setRawMaterrialDto(rawMaterrialDtoList);
            }
        }
        surveyPlanInfoDto.setRecipeDto(recipeDtoList);// 추가



        return surveyPlanInfoDto;
    }

    // 뷰가 해줘야하는거 1. 로그인 2. 워크플랜식별번호(wno) 넣기 3. 투입한거 넣기 *리스트(surveyBDto)
    // 계량 등록버튼을 눌럿을떄
    // C survey 와 surveyB 등록하기
    @Transactional
    public int surveyInsertDo(SurveyInsertDto surveyInsertDto){
//        // 테스트용
//        MemberDto memberDto12 = MemberDto.builder()
//                .mno(1)
//                .build();
//        request.getSession().setAttribute("logindto",memberDto12);
//        // 테스트용 end

        // 로그인한 회원정보를 가져온다
        Object object =request.getSession().getAttribute("logindto");
        if(object==null){return -1;} // 값없으면 실패 받환
        MemberDto memberDto = (MemberDto)object; // 형변환
        Optional<MemberEntity> memberEntity = memberRepository.findById(memberDto.getMno()); // 받아온 mno-> member 엔티티 객체 찾아오기
        if(!memberEntity.isPresent())return -1; // 찾은값이 없으면 실패 반환
        surveyInsertDto.setInputmno(memberEntity.get().toDto()); // 계량한사람 dto 저장

        // workPlan 식별번호로 찾아오기
        int wno = surveyInsertDto.getWno(); // 입력받은 워크플랜 식별번호
        WorkPlanEntity workPlanEntity = workPlanEntityRepository.findBywno(wno); // 엔티티 호출
//        WorkPlanDto workPlanDto = workPlanEntity.toDto(); // DTO로 변환
//        surveyInsertDto.setWorkPlanDto(workPlanDto); // 워크플랜 DTO 저장


        // workplan PK 으로 등록된 surveyEntity 찾아오기
        Optional<SurveyEntity> surveyEntity = surveyRepository.findById(surveyInsertDto.getWno());


        // 등록된 sno가 있는지 없는지 파악
        if(!surveyEntity.isPresent()){// 워크플랜 번로와 같은 sno가 없다면

            // Survey 저장
            SurveyEntity savedSurveyEntity = surveyRepository.save(SurveyEntity.builder()
                            .sno(wno)

                    .build());
            savedSurveyEntity.setInputmemberEntity(memberEntity.get());
            savedSurveyEntity.setWorkPlanEntity(workPlanEntity);

            if(savedSurveyEntity.getSno()<0)return -2; // 저장된 PK 값이없으면 실패 처리

            // SurveyB 저장 ( 원재료 수만큼 등록해야함 )
            for (int i = 0; i < surveyInsertDto.getSurveyBDto().size(); i++) {
                // 원재료 식별번호로 값 호출
                Optional<RawMaterialEntity> rawMaterialEntity = rawMaterialEntityRepository
                        .findById(surveyInsertDto.getSurveyBDto().get(i).getRmno());
                if(!rawMaterialEntity.isPresent())return -3;

                // SurveyB 저장
                SurveyBEntity savedSurveyBEntity = surveyBRepository.save(
                        SurveyBEntity.builder()
                                .surveyEntity(savedSurveyEntity)
                                .sbcount(surveyInsertDto.getSurveyBDto().get(i).getSbcount())
                                .build());
                savedSurveyBEntity.setRawMaterialEntity(rawMaterialEntity.get());
                savedSurveyBEntity.setSurveyEntity(savedSurveyEntity);
                

            }// SurveyB 저장 for End
            // 작업 다 끝난후 검사 완료 메세지 소켓 전송
            try {
                // 제품 이름 과 수량을 소켓으로 전달
                String workName = savedSurveyEntity.getWorkPlanEntity().getProductEntity().getPname();
                String workCount = String.valueOf(savedSurveyEntity.getWorkPlanEntity().getWcount());
                String workNo = String.valueOf(savedSurveyEntity.getWorkPlanEntity().getWno());
                alertSocekt.sendString(new TextMessage(" 생산계획 번호 : "+workNo +" 제품명 : "+workName+" 수량 : " +workCount+" EA "+"   "+" 계량 완료!!"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return savedSurveyEntity.getSno();

        }else {// 워크플랜 번로와 같은 sno가 있는경우(있는내용에서 업데이트 시켜야함)
            if(surveyEntity.get().getSstate()>0){return -4;}// 검사 완료됫으면 수정불가
            // Survey 업데이트 하기
            surveyEntity.get().setInputmemberEntity(memberEntity.get()); // 계량 등록한사람

            // SurveyB 업데이트 하기
            for (int i = 0; i < surveyInsertDto.getSurveyBDto().size(); i++) {
                // 원재료 식별번호로 값 호출
                Optional<RawMaterialEntity> rawMaterrialDto = rawMaterialEntityRepository
                        .findById(surveyInsertDto.getSurveyBDto().get(i).getRmno());
                if(!rawMaterrialDto.isPresent())return -3;

                // sno와 rmno 로 엔티티 찾아오기
                SurveyBEntity surveyBEntity = surveyBRepository.findByUpdate(surveyEntity.get().getSno(),rawMaterrialDto.get().getRmno());
                // 등록수량 수정입력
                surveyBEntity.setSbcount(surveyInsertDto.getSurveyBDto().get(i).getSbcount());
            }
            // 작업 다 끝난후 검사 완료 메세지 소켓 전송
            try {
                String workName = surveyEntity.get().getWorkPlanEntity().getProductEntity().getPname();
                String workCount = String.valueOf(surveyEntity.get().getWorkPlanEntity().getWcount());
                String workNo = String.valueOf(surveyEntity.get().getWorkPlanEntity().getWno());
                alertSocekt.sendString(new TextMessage(" 생산계획 번호 : "+workNo +" 제품명 : "+workName+" 수량 : " +workCount+" EA "+"    계량 수정 완료!!"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return surveyEntity.get().getSno();
        }


    }



}//class end