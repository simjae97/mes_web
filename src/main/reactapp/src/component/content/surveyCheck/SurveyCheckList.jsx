import axios from "axios";
import { useContext, useEffect, useRef, useState } from "react";
import { RenderContext } from "../survey/Survey";

export default function SurveyCheckList(props){
    let [surveyb,setSurveyb] = useState([]);
    const [confirmstate , setConfirmState] = useState('0');
    let [confirmmembername , setConfirmMemberName] = useState('');

    // 재 랜더링용
    // - provider 컴포넌트의 value 호출
    const { render ,setRender } = useContext(RenderContext);

    // 출력
    useEffect(() =>{
        axios.get('/survey/check/survey/get.do')
        .then((response)=>{
            console.log(response);
        if(response.data != []){
            setSurveyb(response.data);
        }
    })
    } , [render])

    // 검사
    const confirmStateChange = (e)=>{
        setConfirmState(e.target.value);
        console.log('23번줄')
        console.log(e.target.value)
        e.preventDefault();
    }
    
    let onMaterialConfirm = (index,wno)=>{
        const confirmForm = document.querySelector(`.confirmForm${index}`);

        const selectValue = document.querySelector(`.selectValue${wno}`).value;
        // console.log(confirmForm);
        

        const confirmFormData = new FormData(confirmForm);
        confirmFormData.append("state",selectValue);
        console.log(confirmFormData)
        axios.put('/survey/check/complete/put.do',confirmFormData)
        .then(r=>{
            // console.log("35");
            // console.log(r);
            if(r.data){
                // wstate 변경
                let data = {
                    wno : wno,
                    wstate : 2
                }
                axios.put('/wp/changestate/put.do',data)
                .then(r=>{
                    console.log(r);
                })
                // window.location.href='/survey/survey';
                alert("안내) 검사 등록 성공");
                setRender(render+1)// 재 랜더링 용
                
            }else{alert("안내) 검사 등록 실패");}
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
                    <tr>
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
                        surveyb.map((r,index)=>{
                            let cdate = r.cdate?.split('T')[0];
                            let udate = r.udate?.split('T')[0];

                            return(<>
                                    {console.log(r)}
                                    <tr className="tableTitle">
                                        <td>
                                            {r.wno}
                                        </td>
                                        <td>
                                            {r.inputmname}
                                        </td>
                                        <td>
                                            {udate}
                                        </td>
                                        <td>
                                            {
                                                r.sstate == 0 ? '검사대기' : r.sstate == 1 ? '검사불합격' : r.sstate == 2 ? '검사합격' : '-'
                                            }
                                        </td>
                                        <td>
                                            <button onClick={()=>{document.querySelector('.modal'+r.sno).style.display = 'revert'}} type="button">상세보기</button>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colSpan={"6"} style={{display:'none'}} className={`modal${r.sno} reportBox`}>
                                            <p className="workplanNumber">생산계획 번호 : {r.wno}번</p>
                                            <div className="orderProduct">
                                                <p>제품명 : {r.pname}</p>
                                                <p>생산 예정 수량 : {r.wcount.toLocaleString()} 개</p>
                                            </div>
                                            <div className="inserContent">
                                                {
                                                    r.surveybList.map((surveybItem, index) => {
                                                        return (<>
                                                            <div>
                                                                <p>원자재명 : {surveybItem.rmname}<br/>필요량 : {surveybItem.reamount.toLocaleString()} <br/>투입량 : {surveybItem.sbcount.toLocaleString()}</p>
                                                            </div>
                                                        </>    
                                                        );
                                                    })
                                                }
                                            </div>
                                            {/* <p>{r.data.surveybList.rmname}필요량 : {r.data.surveybList.reamount} 투입량 : {r.data.surveybList.sbcount}</p> */}
                                            <p>작업날짜 : {cdate}</p>
                                            <p>작업자 : {r.inputmname}</p>
                                            <form className={"confirmForm"+index} >
                                                <input type="text" style={{display:'none'}} value={r.sno} name="sno"/>
                                                <div className="checkBox">
                                                    <span>검사자 : <input onChange={checkMemberNameInput} disabled={r.checkmname != null ? true : false }  value={r.checkmname != null ? r.checkmname : confirmmembername} className="checkMemberInput" type="text"/></span>
                                                    검사상태
                                                    <select name="sstate" className={'selectValue'+r.wno} value={confirmstate} onChange={confirmStateChange}>
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
                                                    <button className="btn-3d green" disabled={r.checkmname != null ? true : false } type="button" onClick={()=>{onMaterialConfirm(index,r.wno)}}>검사 완료</button>
                                                    <button className="btn-3d green" onClick={()=>{document.querySelector('.modal'+r.sno).style.display = 'none'}} type="button">x</button>
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