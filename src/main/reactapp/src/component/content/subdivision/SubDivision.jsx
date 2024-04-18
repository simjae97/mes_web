import React, { useState } from "react";
import SubDivisionPreviousWork from "./SubDivisionPreviousWork";
import SubDivisionPrintBox from "./SubDivisionPrintBox";
import SubDivisionTotalBox from "./SubDivisionTotalBox";
import SubDivisionWriteBox from "./SubDivisionWriteBox";

// 전역변수
export const RenderContext = React.createContext('');

export default function SubDivision(props){

    // 재 랜더링용
    const [ render ,setRender]=useState(0);

    return(<>
        <RenderContext.Provider value={{ render ,setRender }}>
            <div className="contentWrap">
                <SubDivisionTotalBox/>
                <SubDivisionPreviousWork/>
                <SubDivisionWriteBox/>
                <SubDivisionPrintBox/>
            </div>
        </RenderContext.Provider>
    </>)
}