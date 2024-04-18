import axios from "axios"
import { useEffect } from "react"
import { useState } from "react"
import { useSearchParams } from "react-router-dom";

export default function ReportForMInput(props){
    const[query] = useSearchParams();

    const [report2 , setReport2] = useState([])
    const wstate = query.get("wstate")
    let a = 0;
    useEffect( () =>{
        const callbackeffect = async () =>{
            await axios.get("/wp/fidsno/get.do?wno="+props.wno).then(r=>{a= r.data}).catch(e=>{console.log(e)})
            await axios.get("/material/input/info/get.do?sno="+a).then(r=>{setReport2(r.data)})
        }
        callbackeffect();
    },[])

    

    console.log(report2)
    console.log(report2.checkmno)
    return report2[0] ? (
        <>  
            <h4>원료 투입 보고서 </h4>
            <table>
                <thead>
                    <th>원자재명</th><th>투입량</th>
                </thead>
                <tbody>              
                {report2.map( (e)=>{
                    return(<tr><td>{e.rmname}</td><td>{e.sbcount}</td></tr>)
                })}
                </tbody>
            </table>
            상태: {wstate > 3 ? <div>투입완료</div>:<div>투입확인전</div>}
            작성자: {report2[0].checkmno}
        </>
    ) : null;
}