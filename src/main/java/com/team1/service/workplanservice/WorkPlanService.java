package com.team1.service.workplanservice;


import com.team1.model.dto.MemberDto;
import com.team1.model.dto.SubDivisionDto;
import com.team1.model.dto.WorkPlanDto;
import com.team1.model.dto.packagingdto.PackagingDto;
import com.team1.model.entity.MemberEntity;
import com.team1.model.entity.ProductEntity;
import com.team1.model.entity.WorkPlanEntity;
import com.team1.model.repository.*;
import com.team1.service.memberserivce.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkPlanService {

    @Autowired
    WorkPlanEntityRepository workPlanEntityRepository;
    @Autowired
    SurveyRepository surveyRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PackagingRepository packagingRepository;

    @Autowired
    private SubDivisionRepository subDivisionRepository;
    public boolean postWPWriteDo(WorkPlanDto workPlanDto){
        System.out.println("서비스workPlanDto = " + workPlanDto);
        // 멤버
        MemberDto loginDto = memberService.doLogininfo();
        if ( loginDto == null ) return false;

        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById( loginDto.getMno() );

        if( !optionalMemberEntity.isPresent() ) return false;

        if(optionalMemberEntity.get().getPart() != -1) return false;

        ProductEntity productEntity = productRepository.findById(workPlanDto.getPno()).get();

        WorkPlanEntity saveWorkPlan = WorkPlanEntity.builder()
                .wcount(workPlanDto.getWcount())
                .client(workPlanDto.getClient())
                .wendtime(workPlanDto.getWendtime()) // 이 부분 수정
                .productEntity(productEntity)
                .build();

        // 저장하기 전에 wno 값이 0보다 큰지 확인할 필요 없음
        WorkPlanEntity savedEntity = workPlanEntityRepository.save(saveWorkPlan);
        if (savedEntity != null) {
            return true;
        } else {
            return false;
        }
    }

    public List<WorkPlanDto> findWPList(String orderby) {
        Comparator<WorkPlanDto> comparator = Comparator.comparing(dto -> {
            try {
                // WorkPlanDto 클래스의 해당 필드를 리플렉션으로 동적으로 가져옴
                Field field = WorkPlanDto.class.getDeclaredField(orderby);
                field.setAccessible(true); // private 필드에 접근할 수 있도록 설정
                return (Comparable) field.get(dto);
            } catch (Exception e) {
                e.printStackTrace();
                // 필드를 가져오는 데 실패한 경우, 기본적으로 정렬을 수행하지 않음
                return null;
            }
        });

        return workPlanEntityRepository.findAll().stream()
                .map(WorkPlanEntity::toDto)
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    // 박시현
    public List<WorkPlanDto> findWorkListGet(){
        System.out.println("WorkPlanService.findWorkListGet");

        List<WorkPlanEntity> workPlanEntityList = workPlanEntityRepository.findAll();
        List<WorkPlanDto> workPlanDtoList = new ArrayList<>();

        if(workPlanEntityList != null){
            for (int i = 0 ; i < workPlanEntityList.size(); i++){
                workPlanDtoList.add(workPlanEntityList.get(i).toDto());
            }
        }else {
            // 빈값이면 null 리턴
            return null;
        }
        // 모든 워크플랜을 List 에 담아서 전달
        return workPlanDtoList;
    }


    public int findSno(int wno){
        Integer result = surveyRepository.findSnoBywno(wno);
        if (result != null){
            return result.intValue();
        }
        return 0;
    }

    public boolean putChangeStateDo(int wno , int wstate){
        System.out.println("wno = " + wno);
        System.out.println("wstate = " + wstate);

        Optional<WorkPlanEntity> optionalWorkPlanEntity = workPlanEntityRepository.findById(wno);

        WorkPlanEntity workPlan = optionalWorkPlanEntity.get();

        workPlan.setWstate(wstate);

        System.out.println("workPlan.getWstate() = " + workPlan.getWstate());

        if(workPlan.getWstate() != 0){
            workPlanEntityRepository.save(workPlan);
            return true;
        }
        return false;
    }
    public int findmipno(int wno){
        Integer result = workPlanEntityRepository.findMipnoByWno(wno);
        if (result != null){
            return result.intValue();
        }
        return 0;
    }

    public SubDivisionDto findsub(int wno){
        System.out.println(subDivisionRepository.findByWno(wno));
        return subDivisionRepository.findByWno(wno).toDto();
    }

    public PackagingDto findpack(int wno){

        return packagingRepository.findbywno(wno).toDto();
    }

}
