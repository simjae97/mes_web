import axios from "axios"
import { useContext, useEffect, useState } from "react"
import { Link } from "react-router-dom";
import "./survey.css"
import { RenderContext } from "./Survey";

import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

export default function WorkPlanList(){

    // 재 랜더링용
    // - provider 컴포넌트의 value 호출
    const { render ,setRender } = useContext(RenderContext);
    
    // 워크플랜
    const [ workPlan , setWorkPlan] = useState([]);

    useEffect(()=>{
        axios.get("/survey/workplaninfo.do")
        .then((response)=>{
            console.log(response.data);
            const result = response.data.map((workPlan)=>{return workPlan;})
            setWorkPlan(result);

        })
    },[render])

    const settings = {
        dots: true,
        infinite: false,
        speed: 500,
        slidesToShow: workPlan.length > 0 ? 3 : 0,
        slidesToScroll: 1,
        arrows: workPlan.length > 0 ? true : false
      };
    
    return(<>
        <div className="slider-container previousWork">
            <Slider {...settings}>
                {workPlan.map((w)=>{
                    if(w.wstate < 2){
                        return(
                            <div className="previosList">
                                <Link to={`/survey/survey?wno=${w.wno}`}>
                                    <h4>작업계획 : {w.wno}</h4>
                                    <p>거래처 : {w.client}</p>
                                    <p>생산기한 : {w.wendtime.split('T')[0].split('-')[0]}년 {w.wendtime.split('T')[0].split('-')[1]}월 {w.wendtime.split('T')[0].split('-')[2]}일</p>
                                </Link>
                            </div>
                        )
                    }
                })}
            </Slider>
        </div>
    </>)
}
/*
{workPlan.map((w)=>{
            if(w.wstate < 2){
                return(
                    <div className="workplanCss">
                        <Link to={`/survey/survey?wno=${w.wno}`}>
                            <h4>작업계획{w.wno}</h4>
                            <div>PlanNumber : {w.wno}</div>
                            <div>등록일자 : {w.cdate.split('T')[0].split('-')[0]}년 {w.cdate.split('T')[0].split('-')[1]}월 {w.cdate.split('T')[0].split('-')[2]}일</div>
                        </Link>
                    </div>
                    )
            }
        })}
*/