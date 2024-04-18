import axios from "axios"
import { useEffect } from "react"
import { useState } from "react"
import { useSearchParams } from "react-router-dom";

export default function ReportForPack(props){
    const[query] = useSearchParams();

    const [report2 , setReport2] = useState({})

    const wstate = query.get("wstate")
    useEffect( () =>{
        const callbackeffect = async () =>{
            await axios.get("/wp/pack/get.do?wno="+props.wno).then(r=>{

                console.log(r.data)
                setReport2(r.data)}
                ).catch(e=>{console.log(e)})}
            callbackeffect()
            },[])


    console.log(report2)

    return (report2.pgcount?(
        <>  
            <h4>패키징 보고서 </h4>
            <table>
                <thead>
                <th>완성 제품량</th><th>담당자</th><th>작성일자</th>
                </thead>
                <tbody>              
                    <tr><td>{report2.pgcount}</td><td>{report2.memberDto.mname}</td><td>{report2.cdate.split("T")[0]}</td></tr>
                </tbody>
                
            </table>
            
        </>
    ):""
    )
}