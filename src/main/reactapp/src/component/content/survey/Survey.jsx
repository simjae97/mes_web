import axios from "axios";
import React, { useContext, useEffect, useState } from "react";
import { Await, useSearchParams } from "react-router-dom";
import WorkPlanList from "./WorkPlanList";
import { LoginInfoContext } from "../../Index";
import "./survey.css"

// 박시현 추가
import SurveyCheckList from "../surveyCheck/SurveyCheckList";
import SurveyTotalBox from "./SurveyTotalBox";

// 전역변수
export const RenderContext = React.createContext('');

export default function Survey(props){
    // 1. 컨텍스트 가져오기 (로그인 정보)
    const { logininfo, setLogin } = useContext(LoginInfoContext);
// console.log(logininfo);

    
    // 쿼리스트링 값 가져오기 wno
    const [query , setQuery] = useSearchParams();
    // console.log(query.get("wno"));

    // 워크플랜 객체
    const [ workPlanInfo , setWorkPlanInfo] = useState({
        wcount:0,
        wendtime:"2024-03-30T10:00:12.123456"
    });
    // 레시피리스트
    const [ recipeDtoList , setRecipeDtoList] = useState( [
        { pname : '' }// 최초 렌더링 할때 오류 발생 : 초기임의의 값을 넣어줌
    ] );
    
    // 재 랜더링용
    const [ render ,setRender]=useState(0);

    useEffect(  ()=>{
        const formData = new FormData();
        formData.append("wno",query.get("wno")); // 바꿔야함
        axios.post("/survey/workplan/clilck.do",formData)
        .then((response)=>{
            console.log(response);
            setWorkPlanInfo(response.data.workPlanDto);
            const result = response.data.recipeDto.map( (re) =>{return re;})// 레시피 dto state 추가하기
            setRecipeDtoList(result);

            
        })
        .catch(re =>{console.log(re)})
    },[query,render])
    
    // console.log(recipeDtoList);
// =============================================================
    let succeseInfo = [false];
    async function onClickEvent(){
        console.log("버튼눌림");
        console.log(succeseInfo)
        for(let i = 0; i<succeseInfo.length; i++){
            if(!succeseInfo[i]){alert("안내) 입력값을 확인해주세요"); return;}
        }

        // ul 요소 가져오기
        const ulElement = document.getElementById('surveyUl');
        // ul 요소 안의 li 요소 개수 파악(li개수 = 입력해야하는 레시피 수)
        const listItemCount = ulElement.getElementsByTagName('li').length;
        
        // 전송할 객체만들기
        let recipeInputList = [];
        for(let i = 0 ; i<listItemCount; i++){
            let recipeClass = document.querySelector(`.recipe${i}`).value; // 입력값
            let recipeId = document.querySelector(`.recipe${i}`).id // 이거 rmno로 쓰는중(객체 생성용)
            
            if(isNaN(recipeClass)){alert("계량) 숫자로 입력해주세요!"); return;}
            else if(recipeClass==""){alert("계량) 값을 입력해주세요!"); return;}
            
            // 객체생성해서 리스트에 저장
            let test = {"rmno":recipeId,
                        "sbcount":recipeClass
                        }
            recipeInputList.push(test);
        }// for END
        
        // 전송용 객체
        let form ={
            "wno":query.get("wno"),
            "surveyBDto":recipeInputList
        };
        console.log(form);

        // 등록 요청하기
        await axios.post("/survey/insert.do",form,{
            headers: {
              'Content-Type': 'application/json' // 예: JSON 데이터 전송
            }
        })
        .then((r)=>{// int 'sno' 반환함 => r.data
            // -1 로그인 정보가 없음
            // -2 Survey 저장실패
            // -3 해당 원자재 레코드가 없음
            // -4 검사 단계 진행됨 (수정불가능)
            console.log(r.data);
            if(r.data>0){
                alert("안내) 계량내용 등록성공 하였습니다.");
                // wstate 변경
                let data = {
                    wno : query.get("wno"),
                    wstate : 1
                }
                axios.put('/wp/changestate/put.do',data)
                .then(r=>{
                    console.log(r);
                })
                // window.location.href='/survey/survey';
            }
            else if(r.data==-1){alert("안내) 로그인 정보가 없습니다.");}
            else if(r.data==-2){alert("안내) 등록실패.");}
            else if(r.data==-3){alert("안내) 해당 원자제가 등록되어있지 않습니다..");}
            else if(r.data==-4){alert("안내) 검사단계가 진행되었습니다.(수정불가)");}
            setWorkPlanInfo({wcount:0})
            setQuery(0);
            setRender(render+1);
            
        })
        .catch((re)=>{console.log(re);})
    }
    
    function onChangeEvent(index,inputCount){
        
        let recipeClass = document.querySelector(`.recipe${index}`).value; // 입력값 가져오기
    
        if(parseInt(inputCount-inputCount*0.01)<=parseInt(recipeClass)&&parseInt(recipeClass)<=parseInt(inputCount+inputCount*0.01)){
            // 입력값이 투입해야하는 양보다 1% 이상 오차가 없다면 (성공)
            document.querySelector(`.validation${index}`).innerHTML="";
            document.querySelector(`.recipe${index}`).style.color = "black"; // 입력값 색상을 검정으로 변경
            document.querySelector(`.recipe${index}`).style.border="1px solid black"

            succeseInfo[0]= true;
        }else{
            document.querySelector(`.validation${index}`).innerHTML=`+-${(inputCount+inputCount*0.01).toLocaleString()} 이내로 투입해주세요`;
            document.querySelector(`.recipe${index}`).style.color = "red"; // 입력값 색상을 빨강으로 변경
            document.querySelector(`.validation${index}`).style.color = "red"; // 입력값 색상을 빨강으로
            document.querySelector(`.recipe${index}`).style.border="1px solid red"
            succeseInfo[0] = false;
        }
    }
    

    if(logininfo!=null){ // 로그인 정보가 로딩되지 않았다면 return 안함
        return(<>
        <RenderContext.Provider value={{ render ,setRender }}>
            <div className="contentWrap">
                <SurveyTotalBox/>
                <WorkPlanList/>

                {workPlanInfo.wcount!=""?
                <div id="surveyCssBox">
                    <form>
                        <h3>
                            <span>생산제품 : {recipeDtoList[0].pname}</span>
                            <span>생산수량 : {workPlanInfo.wcount.toLocaleString()} EA</span>
                            <span>생산기한 : {workPlanInfo.wendtime.split('T')[0]} 까지</span>
                        </h3>
                        <div>
                            <ul id="surveyUl">
                            {
                                recipeDtoList.map((r,index)=>{
                                    return(<>
                                        <li>투입재료 : {r.rmname} 투입 해야하는 양 = {(r.reamount*workPlanInfo.wcount).toLocaleString()}g</li>
                                        <div>
                                            입력된 양 : <input type="text" onChange={()=>{onChangeEvent(index , r.reamount*workPlanInfo.wcount)}} className={"recipe"+index} id={r.rmno} { ...(logininfo.part === 1 || logininfo.part === -1? { disabled: false }: { disabled: true })} />
                                            <span className={"validation"+index}></span>
                                        </div>
                                    </>
                                    );
                                })
                            }
                            </ul>
                            <button className="btn-3d green" id="surveyBtn" type="button" onClick={onClickEvent}>버튼</button>
                        </div>
                    </form>
                </div>
                :""}
                <SurveyCheckList/>
            </div>
        </RenderContext.Provider>
        </>);
    }
}