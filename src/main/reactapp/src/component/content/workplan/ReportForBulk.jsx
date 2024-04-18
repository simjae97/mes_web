import axios from "axios"
import { useEffect } from "react"
import { useState } from "react"
import { useSearchParams } from "react-router-dom";

export default function ReportForBulk(props){
    const[query] = useSearchParams();

    const [report2 , setReport2] = useState([])
    const wstate = query.get("wstate")
    let a = 0;
    useEffect( () =>{
        const callbackeffect = async () =>{
            await axios.get("/wp/fidmipno/get.do?wno="+props.wno).then(r=>{a= r.data}).catch(e=>{console.log(e)})
            await axios.get("/wp/manu/get.do?mipno="+a).then(r=>{setReport2(r.data)})
        }
        callbackeffect();
    },[])

    

    console.log(report2)
    return report2.mfcount ? (
        <>
        <h4>벌크제조보고서</h4>
        <table>
            <thead>
                <tr>
                    <th>완성분량</th><th>담당자</th><th>결재자</th><th>작성일자</th><th>상태</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>
                        {report2.mfcount}
                    </td>
                    <td>
                        {report2.inputmemberDto.mname}
                    </td>
                    <td>
                        {report2.checkmemberDto.mname}
                    </td>
                    <td>
                        {report2.cdate.split("T")[0]}
                    </td>
                    <td>
                        {wstate > 5 ? <div>소분완료</div>:<div>소분전</div>}
                    </td>
                </tr>
            </tbody>
        </table>
        </>
    ) : null;
}