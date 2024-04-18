import axios from "axios"
import { useEffect } from "react"
import { useState } from "react"
import { useSearchParams } from "react-router-dom";

export default function ReportForSub(props){
    const[query] = useSearchParams();

    const [report2 , setReport2] = useState({})

    const wstate = query.get("wstate")
    useEffect( () =>{
        const callbackeffect = async () =>{
            await axios.get("/wp/sub/get.do?wno="+props.wno).then(r=>{

                console.log(r.data)
                setReport2(r.data)}
                ).catch(e=>{console.log(e)})}
            callbackeffect()
            },[])


    console.log(report2)

    return (report2.successcount?(
        <>  
            <h4>벌크 소분작업 보고서 </h4>
            <table>
                <thead>
                    <th>성공분량</th><th>실패분량</th><th>담당자</th><th>결재자</th><th>작성일자</th><th>상태</th>
                </thead>
                <tbody>              
                    <tr><td>{report2.successcount}</td><td>{report2.failcount}</td><td>{report2.inputmemberDto.mname}</td><td>{report2.checkmemberDto? report2.checkmemberDto.mname:""}</td><td>{report2.cdate.split("T")[0]}</td>
                    <td>{wstate > 7 ? <div>소분완료</div>:<div>소분전</div>}</td>
                    </tr>
                </tbody>
            </table>
            
        </>
    ):""
    )
}