import axios from "axios";
import React, { useContext, useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import { LoginInfoContext } from "../../Index";
import { RenderContext } from "./SubDivision";



export default function SubDivisionWriteBox(props){
    // 재 랜더링용
    // - provider 컴포넌트의 value 호출
    const { render ,setRender } = useContext(RenderContext);

    // 1. 컨텍스트 가져오기 (로그인 정보)
    const { logininfo, setLogin } = useContext(LoginInfoContext);
    //console.log(logininfo); 


    // 쿼리스트링 값 가져오기 mfno
    let [query, setQuery] = useSearchParams();

    // 벌크 객체
    const [ manufacturingInfo , setManufacturingInfo] = useState({
        wcount:0,
        wendtime:"2024-03-30T10:00:12.123456"
    });
            
    const manufacturing = ()=>{
        axios.get('/subdivision/manufacturing/one/get.do', { params :{ mfno :query.get("mfno") }} )
        .then((r)=>{
            console.log(r);
            setManufacturingInfo(r.data);
        }).catch( (e) => {console.log(e)})
    }

    useEffect (()=>{
        if(query.get("mfno")){
            manufacturing();
        }
    },[query.get("mfno"),render])

    const onClickBtn = ()=>{
        let subdivisionForm = document.querySelector('.subdivisionForm');
        let subdivisionFormData = new FormData(subdivisionForm);
        subdivisionFormData.append('mfno',query.get('mfno'));
        console.log(subdivisionFormData);
        axios.post("/subdivision/input/post.do?mfno="+query.get('mfno'),subdivisionFormData)
        .then( (r) => {
            console.log(r.data);
            // 소분 보고서 작성
            // 반환 0 = 실패 / 1 이상 = 성공
            // 반환 -1 = 로그인정보 없음  / -2 = 해당담당자아님
            if(r.data>0){// 
                let data = {
                    wno : manufacturingInfo.materialInputDto.workPlanDto.wno,
                    wstate : 7
                }
                axios.put('/wp/changestate/put.do',data)
                .then(r=>{
                    console.log(r);
                    alert("안내) 등록에 성공하였습니다..");
                    setRender(render+1);
                })
            }else if(r.data==-1){alert("안내) 로그인된 정보가 없습니다.")}
            else if(r.data==-2){alert("안내) 해당 공정 담당자가 아닙니다.")}
            else{alert("안내) 등록에 실패하였습니다..")}
        })
        .catch( (e) => {console.log(e)})
    }

    if(logininfo != null && manufacturingInfo.materialInputDto && manufacturingInfo.materialInputDto.workPlanDto){
    return(<>
        {manufacturingInfo.materialInputDto.workPlanDto.wcount!=""?
            <div id="surveyCssBox">
                <form className="subdivisionForm">
                    <h3>
                        <span>생산제품 : {manufacturingInfo.materialInputDto.workPlanDto.pname}</span>
                        <span>생산수량 : {manufacturingInfo.materialInputDto.workPlanDto.wcount} EA</span>
                        <span>생산기한 : {manufacturingInfo.materialInputDto.workPlanDto.wendtime.split('T')[0]} 까지</span>
                    </h3>
                    <div>
                        <p>벌크완료량 : {manufacturingInfo.mfcount.toLocaleString()}</p>
                        {/* {console.log(manufacturingInfo.materialInputDto.productDto.standard)}
                        {console.log(manufacturingInfo.mfcount)}
                        {console.log(((manufacturingInfo.materialInputDto.productDto.standard) / (1)))} */}
                        <p>만들수 있는 제품 수량 : {parseInt((manufacturingInfo.mfcount) / (manufacturingInfo.materialInputDto.productDto.standard)).toLocaleString()}</p>
                        <p>소분완료량 : <input type="text" name="successcount"/></p>
                        <p>불량품량 : <input type="text" name="failcount"/></p>
                        <button className="btn-3d green" type="button" onClick={onClickBtn}>버튼</button>
                    </div>
                </form>
            </div>
            :""}
    </>);
    }
}
/*
{query.map((r,index)=>
                {
                    return (<>
                        <li>투입재료 : {r.rmname} 계량된 값 = {r.sbcount}g</li>
                        <div>입력된 양 : <input type="text"  /></div>
                    </>)
                }
            )}
*/