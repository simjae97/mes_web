import axios from "axios";
import { useContext, useEffect, useRef, useState } from "react";
import { RenderContext } from "./Manufacturing";

export default function ManufacturingCheckList(props){

    // 재 랜더링용
    // - provider 컴포넌트의 value 호출
    const { render ,setRender } = useContext(RenderContext);

    // select 박스 선택한거
    const [confirmstate , setConfirmState] = useState('0');

    let [confirmmembername , setConfirmMemberName] = useState('');
    // 검사 input BOX
    const confirmmembernameChange = (e)=>{
        setConfirmMemberName(e.target.value);
    }

    // 투입 리스트
    const [ manufacturing , setManufacturing] = useState([]);
    

    // 검사 sclect BOX
    const confirmStateChange = (e)=>{
        setConfirmState(e.target.value);
        e.preventDefault();
    }

    
    


    useEffect(() => {
        // 투입리스트 가져오기
        const fetchData = async () => {
            try {
                // 벌크제조 리스트 가져오기
                const response = await axios.get("/manufacturing/manufacturingInfo.do");
                setManufacturing(response.data);

            } catch (error) {console.log(error);}
        };
        fetchData();
    }, [render]);


    let timecalculator = (response) => {
        const newFermentDate = new Date(response.cdate.split('T')[0]);
        const newFermentedDate = new Date(newFermentDate.setDate(newFermentDate.getDate() + response.materialInputDto.productDto.ferment));
        return newFermentedDate;
        
    }

    

    // 검사 상태 등록버튼
    function completeBtn(r,wno){
        
        let state = document.querySelector(`.stateSelect${r.mfno}`).value;
        const formData = new FormData();
        
        formData.append("mfno",r.mfno);
        formData.append("state",state);
        

        axios.post(`/manufacturing/updateState.do`,formData)
        .then((response)=>{
            
            // 1이상 = 성공
            // 0 = 실패
            // -1 = 로그인정보가 없음
            // -2 = 숙성시간이 도달되지 못함
            // -3 = 권한이 없음
            if(response.data>0){
                alert("안내) 검사 내용 등록 성공");
                let data = {
                    wno : wno,
                    wstate : 6
                }
                axios.put('/wp/changestate/put.do',data)
                .then(r=>{
                    console.log(r);
                })
            }
            else if(response.data == -1){ alert("안내) 로그인정보가 없습니다.")}
            else if(response.data == -2){ alert("안내) 숙성이 완료되지않아 처리할 수 없습니다.")}
            else if(response.data == -3){ alert("안내) 해당 작업 담당자가 아닙니다.")}
            else{ alert("안내) 검사 내용 등록 실패")}
            
            setRender(render+1);
        })
        .catch((error)=>{console.log(error)})
    }
    


    
    return(<>
        {manufacturing==null?"":
        <div className="AcontentBox">
            <h3>목록</h3>
            <table>
                {/* <colgroup>
                    <col width="10%"/>
                    <col width="20%"/>
                    <col width="30%"/>
                    <col width="15%"/>
                    <col width="25%"/>
                </colgroup> */}
                <thead>
                    <tr>
                        <th>
                            벌크 제조 번호
                        </th>
                        <th>
                            담당자
                        </th>
                        <th>
                            날짜
                        </th>
                        <th>
                            상태
                        </th>
                        <th>
                            비고
                        </th>
                    </tr>
                </thead>
                <tbody>
                    {
                        manufacturing.map((r,index)=>{
                            return(
                                <>
                                    <tr className="tableTitle">
                                        <td>
                                            {r.mfno}
                                        </td>
                                        <td>
                                            {r.inputmemberDto.mname}
                                        </td>
                                        <td>
                                            {r.udate.split('T')[0]}
                                        </td>
                                        <td>
                                            {
                                                r.mfstate == 0 ? '검사대기' : r.mfstate == 1 ? '검사불합격' : r.mfstate == 2 ? '검사합격' : '-'
                                            }
                                        </td>
                                        <td>
                                            <button onClick={()=>{document.querySelector('.modal'+r.mfno).style.display='revert'}} type="button">상세보기</button>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colSpan={"5"} style={{display:'none'}} className={`modal${r.mfno} reportBox`}>
                                            <p className="workplanNumber">작업계획 번호 : {r.materialInputDto.workPlanDto.wno}</p>
                                            <p>벌크제조계획 번호 : {r.mfno}</p>
                                            <p>벌크명 : {r.materialInputDto.productDto.pname}벌크</p>
                                            <p>벌크수량 : {r.mfcount.toLocaleString()}</p>
                                            <p>벌크제조 완료 날짜 : {r.cdate.split('T')[0]}</p>
                                            <p>벌크숙성 완료 날짜 : { timecalculator(r).getFullYear() }년{ timecalculator(r).getMonth()+1}월{ timecalculator(r).getDate()}일</p>
                                            <p>담당자 : {r.inputmemberDto.mname}</p>
                                            <div className={`confirmForm"${index}`} >
                                                
                                                검사자 : <input onChange={confirmmembernameChange} disabled={r.checkmemberDto == null ? false : true }  value={r.checkmemberDto == null ? confirmmembername : r.checkmemberDto.mname } className="checkMemberInput" type="text"/> 
                                                검사상태
                                                <select name="state" className={"stateSelect"+r.mfno} value={confirmstate} onChange={confirmStateChange}>
                                                    <option>
                                                        검사대기
                                                    </option>
                                                    <option value="1">
                                                        검사불합격
                                                    </option>
                                                    <option value="2">
                                                        검사합격
                                                    </option>
                                                </select>
                                                <button className="btn-3d green" disabled={r.checkmemberDto == null ? false : r.mfstate==2?true:false } type="button" onClick={()=>{completeBtn(r,r.materialInputDto.workPlanDto.wno)}}>검사 완료</button>
                                                <button className="btn-3d green" onClick={()=>{document.querySelector('.modal'+r.mfno).style.display = 'none'}} type="button">x</button>
                                            
                                            </div>


                                        </td>
                                    </tr>
                                </>
                            )
                        })
                    }
                </tbody>
            </table>
        </div>
        }
    </>);
}