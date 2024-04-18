import axios from "axios";
import { useCallback, useContext, useEffect, useRef, useState } from "react";
import { useSearchParams } from "react-router-dom";
import SurveyList from "./SurveyList";
import { LoginInfoContext } from "../../Index";
import { RenderContext } from "./MaterialInput";

export default function MaterialInput2(props){
    // 재 랜더링용
    // - provider 컴포넌트의 value 호출
    const { render ,setRender } = useContext(RenderContext);
    // 1. 컨텍스트 가져오기 (로그인 정보)
    const { logininfo, setLogin } = useContext(LoginInfoContext);
    //console.log(logininfo); 


    // 쿼리스트링 값 가져오기 sno
    let [query, setQuery] = useSearchParams();

    const [ surveyB , setSurveyB ] = useState([
        {
            "pname":"",
            "wcount":0,
            "wendtime":"2024T"
        }
    ]);
    console.log(query)
    console.log(query.get("sno"))
            
    const survey =
     () =>{
     axios.get('/material/input/info/get.do', { params :{ sno :query.get("sno") }} )
    .then((response)=>{
    console.log(response);
    setSurveyB(response.data);
    

    }).catch( (e) => {console.log(e)})
    }
    console.log(surveyB);

    useEffect (() => { 
        if(query.get("sno")){
            survey() 
        }
    } , [query.get("sno"),render])
    

    function onClickBtn(){
        console.log( query.get("sno"))
        axios.post("/material/input/post.do?sno="+query.get("sno"))
        .then( (r) => {
            // 0 실패
            // 1 이상 성공
            // -1 해당 업무 담당자 아님
            // -2 로그인 정보가 없음
            console.log(r.data);
            if(r.data>=1){
                alert("안내) 투입내용 등록 성공하였습니다.");
                // wstate 변경
                let data = {
                    wno : surveyB[0].wno,
                    wstate : 3
                }
                axios.put('/wp/changestate/put.do',data)
                .then(r=>{
                    console.log(r);
                })
                setRender(render+1);
                // window.location.href="/material/input"
            }else if(r.data==-1){
                alert("안내) 등록 권한이 없습니다.");
            }else if(r.data==-2){
                alert("안내) 로그인된 정보가 없습니다.");
            }else{
                alert("안내) 등록 실패 [관리자 문의].");
            }
            
            
        })
        .catch( (e) => {console.log(e)})
    }

    
    if(logininfo != null ){
    return(<>
        {surveyB[0].wcount!=""?
            <div>
            <h3>
                <span>생산제품 : {surveyB[0].pname}</span>
                <span>생산수량 : {surveyB[0].wcount.toLocaleString()} EA</span>
                <span>생산기한 : {surveyB[0].wendtime.split('T')[0]}까지</span>

            </h3>
            <ul>
                {surveyB.map((r,index)=>
                    {
                        return (<>
                            <li>투입재료 : {r.rmname} 계량된 값 = {r.sbcount}g</li>
                            <div>입력된 양 : <input type="text"  /></div>
                        </>)
                    }
                )}
            </ul>
            <button className="btn-3d green" type="button" onClick={onClickBtn}>등록</button>
            </div>
        :" "}
    </>);
    }
}




