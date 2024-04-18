import axios from "axios"
import { useEffect, useState } from "react"

export default function ReportForSurvey(props){
    const [report2 , setReport2] = useState({})
    console.log(props.wno)

    useEffect( () =>{
        axios.post("/survey/workplan/clilck.do?wno="+props.wno).then(r=>{setReport2(r.data)}).catch(e=>{console.log(e)})
    },[])

    console.log(report2)
    return report2.workPlanDto ? (
        <>
            <h2>보고서번호:{props.wno} 완성제품:{report2.workPlanDto.pname} 완성수량:{report2.workPlanDto.wcount}에 대한 전체 보고서 </h2>
            
            <h4>원료 계량 보고서 </h4>
            <table>
                <thead>
                    <th>원자재명</th><th>계량</th>
                </thead>
                <tbody>              
                {report2.recipeDto.map( (e)=>{
                    return(<tr><td>{e.rmname}</td><td>{e.reamount*report2.workPlanDto.wcount}</td></tr>)
                })}
                </tbody>
            </table>
            상태: {report2.workPlanDto.wstate > 1 ? <div>계량완료</div>:<div>확인전</div>}
        </>
    ) : null;
}