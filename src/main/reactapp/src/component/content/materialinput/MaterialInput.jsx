import SurveyList from "./SurveyList";
import MaterialInputPrintBox from "./MaterialInputPrintBox";
import MaterialInput2 from "./MaterialInput2";
import MaterialInputTotalBox from "./MaterialInputTotalBox";
import SurveyTotalBox from "../survey/SurveyTotalBox";
import React, { useState } from "react";

// 전역변수
export const RenderContext = React.createContext('');

export default function MaterialInput(props){
    // 재 랜더링용
    const [ render ,setRender]=useState(0);

    return(<>
        <RenderContext.Provider value={{ render ,setRender }}>
        <div className="contentWrap">
            
            <MaterialInputTotalBox/>
            <div>
                <SurveyList/>
            </div>
            <div id="cssBox">
                <MaterialInput2/>
            </div>
            <MaterialInputPrintBox/>
        </div>
        </RenderContext.Provider>
    </>)
}