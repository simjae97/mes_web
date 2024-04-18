import axios from "axios";
import { useContext, useEffect, useState } from "react";
import { RenderContext } from "./Packaging";

export default function PackagingPrintBox(props){
    // 재 랜더링용
    // - provider 컴포넌트의 value 호출
    const { render ,setRender } = useContext(RenderContext);

    let [packaging,setPackaging] = useState([]);
    
    

    // 출력
    useEffect(() =>{
        axios.get('/packaging/info/get.do')
        .then((r)=>{
            console.log(r);
            console.log(r.data);
        if(r.data != []){
            setPackaging(r.data);
        }
    })
    } , [render])
    
    return(
        <div className="AcontentBox">
            <h3>목록</h3>
            <table>
            <colgroup>
                {/* <col width="10%"/>
                <col width="20%"/>
                <col width="30%"/>
                <col width="15%"/>
                <col width="25%"/> */}
            </colgroup>
                <thead>
                    <tr>
                        <th>
                            포장 계획 번호
                        </th>
                        <th>
                            담당자
                        </th>
                        <th>
                            날짜
                        </th>
                        <th>
                            상태
                        </th>
                        <th>
                            비고
                        </th>
                    </tr>
                </thead>
                <tbody>
                    {
                        packaging.map((r,index)=>{
                            let cdate = r.cdate.split('T')[0];
                            let udate = r.udate.split('T')[0];

                            return(
                                <>
                                    <tr>
                                        <td>
                                            {r.pgno}
                                        </td>
                                        <td>
                                            {r.memberDto.mname}
                                        </td>
                                        <td>
                                            {cdate}
                                        </td>
                                        <td>
                                            
                                                포장완료
                                            
                                        </td>
                                        <td>
                                        <button onClick={()=>{document.querySelector('.modal'+r.pgno).style.display = 'block'}} type="button">상세보기</button>
                                        </td>
                                    </tr>
                                    <div style={{display:'none'}} className={"modal"+r.pgno}>         
                                        <p>포장계획 : {}</p>                               
                                        <p>제품명 : {}</p>
                                        <p>수주량 : {}</p>
                                        <p>포장개수 : {}</p>
                                        <p>날짜 : {}</p>
                                        <p>담당자 : {}</p>                                        
                                        <button onClick={()=>{document.querySelector('.modal'+r.pgno).style.display = 'none'}} type="button">x</button>
                                    </div>
                                </>
                            )
                        })
                    }
                </tbody>
            </table>
        </div>
    )
}