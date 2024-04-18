import React, { useState } from "react";
import PackagingLayOut from "./PackagingLayOut";
import PackagingPrintBox from "./PackagingPrintBox";
import PackagingTotatBox from "./PackagingTotalBox";
import PackagingWrite from "./PackagingWrite";

// 전역변수
export const RenderContext = React.createContext('');

export default function Packaging(props){
    // 재 랜더링용
    const [ render ,setRender]=useState(0);

    return(<>
        <RenderContext.Provider value={{ render ,setRender }}>
            <div className="contentWrap">
                <PackagingTotatBox />
                <PackagingLayOut />
                <PackagingWrite />                
            </div>
        </RenderContext.Provider>
    </>)
}