import { Button, Input, Stack } from "@mui/material";
import axios from "axios";
import { useState } from "react";
import * as React from 'react';


export default function RmWrite(props){

    const [rmname,setrmname] = useState("");

    const doPost = ()=>{
        const formData = new FormData();
        formData.append("rmname",rmname);
        axios.post("/RM/post.do",formData).then( r=> {
            if(r.data){
                alert("등록성공")
                props.setrerender(!props.rerender)
            }
        }).catch(e=>{console.log(e)})
    }
    return(<>
        <form>
        <input value={rmname} className="sinputinput" onChange={(e)=>{setrmname(e.target.value)}} />
        <button type="button" className="sinputbutton" onClick={doPost}>원자재 등록</button>
    </form>

    
    </>)
}
{/* <div id="RmWriteBox">
        <div className="RmWriteInput">
            <input value={rmname} onChange={(e)=>{setrmname(e.target.value)}}/>     
        </div>   
        <div className="RmWriteBtn">
            <button type="button" onClick={doPost}>제품 등록</button>
        </div>
    </div> */}