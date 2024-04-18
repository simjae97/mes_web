import axios from "axios";
import { useEffect, useState } from "react";

export default function WorkPlan(props){
    const [reRender , setReRender] = useState(false);
    const [productInfo , setProductInfo] = useState([]);

    const writeWorkPlan = ()=>{
        let workPlanForm = document.querySelector('.workPlanForm');
        let workPlanFormData = new FormData(workPlanForm);

        axios.post('/wp/write/post.do',workPlanFormData)
        .then(r=>{
            console.log(r);
            if(r){
                setReRender(!reRender);
            }
            alert("생산일정 등록완료")
        })
        .catch(e=>{
            console.log(e);
        })
    }

    useEffect(()=>{
        axios.get('/product/get.do')
        .then(r=>{
            console.log(r);
            setProductInfo(r.data);
        })
        .catch(e=>{
            console.log(e);
        })
    },[])

    return(
        <div className="contentWrap">
            <div id="workPlanmInputBoxWrapT">
                
                <form className="workPlanForm" id="workPlanmInputBoxWrap">                    
                    <h1>생산 일정</h1>
                    <div className="alsguddl">
                        <span>거래처</span>
                        <input type="text" name="client"/>
                    </div>

                    <div className="alsguddl">
                    <span>제품</span>                    
                    <select name="pno" style={{width:'300px'}}>
                        {productInfo.map((r)=>{
                            return(
                                <option value={r.pno}>{r.pname}</option>
                            )
                        })}
                    </select>
                    </div>
                    
                    <div className="alsguddl">
                    <span>수량</span>                     
                    <input type="text" name="wcount"/> 
                    </div>

                    <div className="alsguddl">
                    <span>납기일</span>
                    <input type="datetime-local" name="wendtime"/>
                    </div>

                    <div id="workPlanmInputBoxWrapBox">
                        <button type="button" onClick={writeWorkPlan}>등록</button>
                    </div>
                </form>
            </div>
        </div>
    )
}