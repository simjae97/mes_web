import styles from "./main.css";
import Header from "./header/Header";
import Login from "./login/Login";
import Main from "./content/Main";
import { BrowserRouter, Link, Route, Routes } from "react-router-dom";
import React, { useContext, useEffect, useRef, useState } from "react";
import ProductWrite from "./content/product/ProductWrite";
import ProductList from "./content/product/Productlist";
import RmWrite from "./content/rawmaterial/RmWrite";
import RmCount from "./content/rawmaterial/RmCount";
import RmLogList from "./content/rawmaterial/RMLogList";
import MaterialInput from "./content/materialinput/MaterialInput";
import ProductRecipieList from "./content/recipie/ProductRecipieList";

import Survey from "./content/survey/Survey" // 승호
import WorkPlanList from "./content/survey/WorkPlanList"; // 승호
import SurveyList from "./content/materialinput/SurveyList";
import ProductLayOut from "./content/product/ProductLayOut";
import RmLayOut from "./content/rawmaterial/RmLayOut";
import LayoutTest from "./content/layouttest/Layouttest";
import RmWrites from "./content/rawmaterial/RmWirtes";
import axios from "axios";
import RmLLayOut from "./content/rawmaterial/RmLLayOut";
import AllWorkPlanList from "./content/workplan/AllWorkPlanList";
import ReportAll from "./content/workplan/ReportAll";
import WorkPlan from "./content/workplan/WorkPlan";



import ProductRLayOut from "./content/recipie/ProductRLayout";
import SubDivision from "./content/subdivision/SubDivision";
import Manufacturing from "./content/manufacturing/Manufacturing";//승호
import Packaging from "./content/packaging/Packaging";
import ExpirationLayOut from "./content/expiration/ExpirationLayOut";
import MeberList from "./member/MeberList"; // 시현 멤버 리스트 출력
import ChartLayOut from "./content/chart/ChartLayOut";


import ProgressAlarm from "./member/ProgressAlarm"; // 시현 소켓통신
import Progress from "./content/progress/Progress"; // 시현 진행상황
import ProductLogList from "./content/product/ProductLogList";
import LayoutTest2 from "./content/layouttest/Layouttest2";


export const LoginInfoContext = React.createContext("");

export default function Index(props){
    const[logininfo,setLogin] = useState(null);
    useEffect(()=>{
        axios.get("/member/login/info/get.do")
            .then( (r)=>{console.log(r)
                    setLogin(r.data)
            } )
            .catch( (e) => {console.log(e)})

    } ,[] )

        // 박시현 소켓 시작

        // 1. 해당 컴포넌트가 렌더링 될때 소켓은 재랜더링 방지. useRef 사용
        // useRef(초기값) : {current : 값}
        // - 컴포넌트가 렌더링시 참조값을 고정 할수 있다.
        let clientSocket = useRef(null);

        // - Provider 컴포넌트의 value 호출
        const {loginInfo , setLoginInfo} = useContext(LoginInfoContext)

        // 2. Ref 참조가 없으면
        if( !clientSocket.current ){

        // =============== (클라이언트 )웹 소켓 구현 =============== //
            // 1. let clientSocket = new WebSocket(서버 소켓 url); // 비동기 // 서버소켓에게 접속 요청
        clientSocket.current = new WebSocket('ws://localhost:80/');
            // 확인
        console.log(clientSocket);
        // onclose , onerror , onmessage , onopen : WebSocket 객체내 포함된 메소드들
            // 2. 각 메소드 정의
                // - 1. 클라이언트소켓이 close 되었을떄 콜백함수 정의
        clientSocket.current.onclose = (e)=>{console.log(e);console.log('서버소켓닫힘');}
                // - 2. 클라이언트소켓이 error 발생했을때 콜백함수 정의 (* error 이미 발생했고 다음행동 정의)
        clientSocket.current.onerror = (e)=>{console.log(e);console.log('서버소켓 에러');}
                // - 3 클라이언트 소켓이 message 받았을떄 콜백함수 정의
        clientSocket.current.onmessage = (e) =>{
            console.log(e);
            console.log(e.data);
            setMessageFromSocket(e.data);
        }
                // - 4 클라이언트 소켓이 open 발생 했을떄 콜백함수 정의
        clientSocket.current.onopen = (e) => {console.log(e); console.log('서버소켓연결성공');}

        }

        const [messageFromSocket, setMessageFromSocket] = useState(""); 

        // 소켓 끝


    return(
        <LoginInfoContext.Provider value={{logininfo,setLogin}}>
        <BrowserRouter>
                <div id="wrap">
                {logininfo && <Header/>}
                    <Routes>
                    <Route path="/" element={<Login/>}/>
                    <Route path="/c" element={<Main/>}/>
                    {/* <Route path="/product/write" element={<ProductWrite />}/> */}
                    <Route path="/product" element={<ProductLayOut />}/>
                    <Route path="/RM" element={<RmLayOut/>}/>
                    <Route path="/RM/log" element={<RmLLayOut/>}/>
                    <Route path="/material/input" element={ <MaterialInput/> }/>
                    <Route path="/subdivision" element={ <SubDivision/> }/>
                    <Route path="/survey/survey" element={<Survey/> } />
                    <Route path="/survey/plan" element={<WorkPlanList/> } />
                    <Route path="/product/recipie/get" element={<ProductRLayOut/>}/>
                    <Route path="/wp/list" element={<LayoutTest2 list={<AllWorkPlanList/>} />}/>
                    <Route path="/wp/write" element={<WorkPlan/>}/>
                    <Route path="/wp/report" element={<ReportAll/>}/>
                    <Route path="/manufacturing/info" element={<Manufacturing/>}/>
                    <Route path="/packaging" element={<Packaging />} />
                    <Route path="/expiration" element={<ExpirationLayOut/>}/>
                    <Route path="/member/List" element={<MeberList/>}/>
                    <Route path="/chart" element = {<ChartLayOut/>}/>
                    <Route path="/Progress/view" element = {<Progress/>}/>
                    <Route path="/product/log/list" element = {<LayoutTest list={<ProductLogList/>}/>}/>
                    </Routes>
                </div>
        </BrowserRouter>
        {messageFromSocket && <ProgressAlarm message={messageFromSocket} />} {/* 진행 사항마다 소켓 안내창 발생 컴포넌트 */}
        </LoginInfoContext.Provider>
    )
}


        // <div id="wrap">
        //     <Header/>
        //     <Mensuration/>
        // </div>