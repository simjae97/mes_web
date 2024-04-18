package com.team1.service.progressService;

import com.team1.model.dto.ProgressDTO;
import com.team1.model.entity.WorkPlanEntity;
import com.team1.model.repository.WorkPlanEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProgressService {

    @Autowired
    WorkPlanEntityRepository workPlanEntityRepository;

    // 작업진행 상황 워크플랜 데이터 가져오기
    @GetMapping("/get.do")
    public List<ProgressDTO> progressGetPlan(){
        System.out.println("ProgressService.progressGetPlan");
        List<WorkPlanEntity> workPlanEntityList = workPlanEntityRepository.findAll();
        System.out.println("workPlanEntityList = " + workPlanEntityList);
        List<ProgressDTO> progressDTOList = new ArrayList<>();
        for (int i = 0 ; i < workPlanEntityList.size(); i++){
            progressDTOList.add(ProgressDTO.builder()
                            .wno(workPlanEntityList.get(i).getWno())
                            .wcount(workPlanEntityList.get(i).getWcount())
                            .wstate(workPlanEntityList.get(i).getWstate())
                            .wendtime(workPlanEntityList.get(i).getWendtime())
                            .pname(workPlanEntityList.get(i).getProductEntity().getPname())
                    .build());
        }
        System.out.println("progressDTOList(여기) = " + progressDTOList);
        return progressDTOList;
    }
}
