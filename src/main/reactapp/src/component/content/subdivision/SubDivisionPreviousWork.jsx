import axios from "axios";
import { useContext, useEffect } from "react";
import { useState } from "react";
import { Link } from "react-router-dom";

import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import { RenderContext } from "./SubDivision";

export default function SubDivisionPreviousWork(props){
    // 재 랜더링용
    // - provider 컴포넌트의 value 호출
    const { render ,setRender } = useContext(RenderContext);

    // 계량
    const [ previousWork , setPreviousWork ] = useState([]);

    useEffect( ( ) => {
        axios.get("/subdivision/manufacturing/get.do")
        .then( (r) => {
            console.log(r);
            setPreviousWork(r.data);
        })
    },[render])

    const settings = {
        dots: true,
        infinite: false,
        speed: 500,
        slidesToShow: previousWork.length > 0 ? 3 : 0,
        slidesToScroll: 1,
        arrows: previousWork.length > 0 ? true : false
      };

    return(<> 
        <div className="slider-container previousWork">
            <Slider {...settings}>
                {previousWork.map((r) => {
                    if(r.materialInputEntity.workPlanEntity.wstate == 6 && r.mfstate == 2){
                        return(
                            <div className="previosList">
                                <Link to={`/subdivision?mfno=${r.mfno}`}>
                                    <h4>작업계획 {r.materialInputEntity.workPlanEntity.wno}</h4>
                                    <p>거래처 : {r.materialInputEntity.workPlanEntity.client}</p>
                                    <p>등록일자 : {r.materialInputEntity.workPlanEntity.wendtime.split('T')[0].split('-')[0]}년 {r.materialInputEntity.workPlanEntity.wendtime.split('T')[0].split('-')[1]}월 {r.materialInputEntity.workPlanEntity.wendtime.split('T')[0].split('-')[2]}일</p>
                                </Link>
                            </div>
                        )
                    }
                })}
            </Slider>
        </div>  
    </>)
}