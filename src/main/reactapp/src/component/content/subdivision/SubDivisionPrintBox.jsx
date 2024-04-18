import axios from "axios";
import { useContext, useEffect, useState } from "react";
import { RenderContext } from "./SubDivision";

export default function SubDivisionPrintBox(props){
    // 재 랜더링용
    // - provider 컴포넌트의 value 호출
    const { render ,setRender } = useContext(RenderContext);

    let [subdivision,setSubDivision] = useState([]);
    const [confirmstate , setConfirmState] = useState('0');
    let [confirmmembername , setConfirmMemberName] = useState('');

    // 출력
    useEffect(() =>{
        axios.get('/subdivision/allinfo/get.do')
        .then((r)=>{
            console.log(r);
            console.log(r.data);
        if(r.data != []){
            setSubDivision(r.data);
        }
    })
    } , [render])

    // 검사
    const confirmStateChange = (e)=>{
        setConfirmState(e.target.value);
        e.preventDefault();
    }

    let onMaterialConfirm = (index,wno)=>{
        const confirmForm = document.querySelector(`.confirmForm${index}`);

        const confirmFormData = new FormData(confirmForm);

        axios.put('/subdivision/confirm.do',confirmFormData)
        .then(r=>{
            console.log(r);
            // 반환 0 = 실패 / 1 이상 = 성공
            // 반환 -1 = 로그인정보 없음  / -2 = 해당담당자아님
            if(r.data>0){
                let data = {
                    wno : wno,
                    wstate : 8
                }
                axios.put('/wp/changestate/put.do',data)
                .then(r=>{
                    console.log(r);
                    alert("안내) 등록 성공.")
                    setRender(render+1);
                })
                // window.location.href='/subdivision';
            }else if(r.data==-1){alert("안내) 로그인 된 정보가 없습니다.");}
            else if(r.data==-2){alert("안내) 해당공정 담당자가 아닙니다.");}
            else{alert("안내) 등록실패.");}
        })
        .catch(e=>{
            console.log(e);
        })
    }

    let checkMemberNameInput = (e)=>{
        setConfirmMemberName(e.target.value);
    }
    
    return(
        <div className="AcontentBox">
            <h3>목록</h3>
            <table>
            <colgroup>
                {/* <col width="10%"/>
                <col width="20%"/>
                <col width="30%"/>
                <col width="15%"/>
                <col width="25%"/> */}
            </colgroup>
                <thead>
                    <tr className="tableTitle">
                        <th>
                            생산 계획 번호
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
                        subdivision.map((r,index)=>{
                            let cdate = r.cdate.split('T')[0];
                            let udate = r.udate.split('T')[0];

                            return(<>
                                    <tr className="tableTitle">
                                        <td>
                                            {r.manufacturingDto.materialInputDto.workPlanDto.wno}
                                        </td>
                                        <td>
                                            {r.inputmemberDto.mname}
                                        </td>
                                        <td>
                                            {udate}
                                        </td>
                                        <td>
                                            {
                                                r.sdstate == 0 ? '검사대기' : r.sdstate == 1 ? '검사불합격' : r.sdstate == 2 ? '검사합격' : '-'
                                            }
                                        </td>
                                        <td>
                                            <button onClick={()=>{document.querySelector('.modal'+r.sdno).style.display ='revert'}} type="button">상세보기</button>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colSpan={"5"} style={{display:'none'}} className={`modal${r.sdno} reportBox`}>
                                        <p className="workplanNumber">생산계획 번호 : {r.manufacturingDto.materialInputDto.workPlanDto.wno} 번</p>
                                        <div className="orderProduct">
                                            <p>제품명 : {r.manufacturingDto.materialInputDto.productDto.pname}</p>
                                            <p>수주량 : {r.manufacturingDto.materialInputDto.workPlanDto.wcount.toLocaleString()} 개</p>
                                        </div>
                                        <p>소분제품량 : {r.successcount.toLocaleString()} EA</p>
                                        <p>불량품량 : {r.failcount.toLocaleString()} EA</p>
                                        <p>날짜 : {cdate}</p>
                                        <p>담당자 : {r.inputmemberDto.mname}</p>
                                        <form className={"confirmForm"+index} >
                                            <input type="text" style={{display:'none'}} value={r.sdno} name="sdno"/>
                                            <div className="checkBox">
                                                검사자 : <input onChange={checkMemberNameInput} disabled={r.checkmemberDto != null ? true : false }  value={r.checkmemberDto != null ? r.checkmemberDto.mname : confirmmembername} className="checkMemberInput" type="text"/>
                                                검사상태
                                                <select name="sdstate" value={confirmstate} onChange={confirmStateChange}>
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
                                                <button className="btn-3d green" disabled={r.checkmemberDto != null ? true : false } type="button" onClick={()=>{onMaterialConfirm(index,r.manufacturingDto.materialInputDto.workPlanDto.wno)}}>검사 완료</button>
                                                <button className="btn-3d green" onClick={()=>{document.querySelector('.modal'+r.sdno).style.display = 'none'}} type="button">x</button>
                                            </div>
                                        </form>
                                        
                                        </td>
                                    </tr>
                                </>)
                        })
                    }
                </tbody>
            </table>
        </div>
    )
}