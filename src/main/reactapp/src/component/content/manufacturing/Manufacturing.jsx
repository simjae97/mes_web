import axios from "axios";
import React, { useContext, useEffect, useState } from "react";
import { Await, useSearchParams } from "react-router-dom";
import { LoginInfoContext } from "../../Index";
import "./manufacturingCSS.css"

// 다른 컴포넌트
import TotalBox from "../layouts/TotalBox";
import MaterialinputList from "./MaterialinputList";
import ManufacturingCheckList from "./ManufacturingCheckList";
import ManufacturingTotalBox from "./ManufacturingTotalBox";


// 전역변수
export const RenderContext = React.createContext('');

export default function Manufacturing(props){
    // 1. 컨텍스트 가져오기 (로그인 정보)
    const { logininfo, setLogin } = useContext(LoginInfoContext);
    // 쿼리스트링 값 가져오기 mipno
    const [query , setQuery] = useSearchParams();

    //  MaterialInput 객체(전체)
    const [ materialInputInfo , setMaterialInputInfo ] = useState({
        mipno:0
    })
    
    // 워크플랜 객체
    const [ workPlanInfo , setWorkPlanInfo] = useState({
        wcount:0,
        wendtime:"2024-03-30T10:00:12.123456"
    });
    // 숙성날자(계산용)
    const [ fermentDate , setFermentDate ] =useState(new Date())

    // 레시피 리스트
    const [ recipeDtoList , setRecipeDtoList] = useState( [
        { pname : '' }// 최초 렌더링 할때 오류 발생 : 초기임의의 값을 넣어줌
    ] );
    // 재 랜더링용
    const [ render ,setRender]=useState(0);
    
    

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get("/manufacturing/MaterialInput/click.do", { params: { mipno: query.get("mipno") } });
                // console.log(response);
                setWorkPlanInfo(response.data.workPlanDto);
                setMaterialInputInfo(response.data);
                // 시간계산
                const newFermentDate = new Date(response.data.cdate.split('T')[0]);
                const newFermentedDate = new Date(newFermentDate.setDate(newFermentDate.getDate() + response.data.productDto.ferment));
                setFermentDate(newFermentedDate);


                // 투입했던 레시피 찾아오기
                const formData = new FormData();
                formData.append("wno",response.data.workPlanDto.wno);
                const response2 = await axios.post("/survey/workplan/clilck.do",formData);
                const result = response2.data.recipeDto.map( (re) =>{return re;})// 레시피 dto state 추가하기
                setRecipeDtoList(result);
                
                
            } catch (error) {
                console.log(error);
            }
        };
    
        fetchData();
    }, [query,render]);

    
// =============================================================
    function onClickEvent(){
        console.log("버튼눌림");

        // 등록 요청하기
        
        axios.post("/manufacturing/insert.do?mipno="+query.get("mipno"))
        .then((r)=>{// int 'mfno' 반환함 => r.data
            // 반환 = 1 이상은 성공
            // -1 = 로그인 정보없음
            // -2 투입공정 내용을 찾을 수 없음
            // -3 값이 이미 등록되어있음
            if(r.data>0){
                alert("안내) 제조 등록성공 하였습니다.");
                let data = {
                    wno : workPlanInfo.wno,
                    wstate : 5
                }
                axios.put('/wp/changestate/put.do',data)
                .then(r=>{
                    console.log(r);
                })
            }
            else if(r.data==-1){alert("안내) 로그인 정보가 없습니다.");}
            else if(r.data==-2){alert("안내) 이전 공정(투입)에대한 자료가 없습니다.");}
            else if(r.data==-3){alert("안내) 이미 등록 완료된 공정입니다.");}
            else if(r.data==-4){alert("안내) 검사단계가 진행되었습니다.(수정불가)");}

            setRender(render+1);

        })
        .catch((error)=>{console.log(error);})
    }
    



    if(logininfo!=null){ // 로그인 정보가 로딩되지 않았다면 return 안함
        return(<>
        <RenderContext.Provider value={{ render ,setRender }}>
            <div className="contentWrap">
                <ManufacturingTotalBox/>
                <MaterialinputList/>
                
                {materialInputInfo.mipno!=0?
                <div id="surveyCssBox">
                    
                    <form>
                        <h3>
                            <span>생산제품 : {workPlanInfo.pname}</span>
                            <span>생산수량 : {workPlanInfo.wcount.toLocaleString()} EA</span>
                            <span>생산기한 : {workPlanInfo.wendtime.split('T')[0]} 까지</span>
                            <div>숙성 소요 기한 : {materialInputInfo.productDto.ferment} 일</div>
                            <div>예상 완료 기한 : {fermentDate.getFullYear()}년{fermentDate.getMonth()+1}월{fermentDate.getDate()}일</div>
                        </h3>
                        <div>
                            <ul id="surveyUl">
                            {
                                recipeDtoList.map((r)=>{
                                    return(<>
                                        <li>투입재료 : <span className="inputCon">{r.rmname}</span> 투입되어있는 양 : <span className="inputCon">{(r.reamount*workPlanInfo.wcount).toLocaleString()}g</span></li>
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
                <ManufacturingCheckList />
            </div>
        </RenderContext.Provider>
        </>);
    }
}