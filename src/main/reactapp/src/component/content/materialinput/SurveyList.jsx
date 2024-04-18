import axios from "axios";
import { useContext, useEffect, useState } from "react"
import { Link } from "react-router-dom";
import { RenderContext } from "./MaterialInput";
/* global $ */

import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

export default function SurveyList(props){
    // 재 랜더링용
    // - provider 컴포넌트의 value 호출
    const { render ,setRender } = useContext(RenderContext);

    // 계량
    const [ survey , setSurvey ] = useState([]);

    useEffect( ( ) => {
        axios.get("/material/surveyinfo.do")
        .then( (r) => {
            console.log(r);
            const result = r.data.map((survey) => {return survey;})
            setSurvey(result)
        })
    },[render])

    
    const settings = {
        dots: true,
        infinite: false,
        speed: 500,
        slidesToShow: survey.length > 0 ? 3 : 0,
        slidesToScroll: 1,
        arrows: survey.length > 0 ? true : false
      };

    return(<>
        <div className="slider-container previousWork">
            <Slider {...settings}>

                {survey.map((s) => {
                    if(s.workPlanDto.wstate == 2 && s.sstate == 2){
                        return(
                            <div className="previosList">
                                <Link to={`/material/input?sno=${s.sno}`}>
                                    <h4>작업계획 {s.workPlanDto.wno}</h4>
                                    <p>거래처 : {s.workPlanDto.client}</p>
                                    <p>등록일자 : {s.workPlanDto.wendtime.split('T')[0].split('-')[0]}년 {s.workPlanDto.wendtime.split('T')[0].split('-')[1]}월 {s.workPlanDto.wendtime.split('T')[0].split('-')[2]}일</p>
                                </Link>
                            </div>
                        )
                    }
                })}
            </Slider>
        </div>
    </>)
}