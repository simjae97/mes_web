import axios from "axios";
import { useContext, useEffect } from "react";
import { useState } from "react";
import { Link } from "react-router-dom";
import { RenderContext } from "./Packaging";

import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

export default function PackagingLayOut(props){
    // 재 랜더링용
    // - provider 컴포넌트의 value 호출
    const { render ,setRender } = useContext(RenderContext);

    // 계량
    const [ subdivision , setSubdivision ] = useState([]);    
    useEffect( ( ) => {
        axios.get("/packaging/subdivision.do")
        .then( (r) => {
            console.log(r);
            setSubdivision(r.data);
        })
    },[render])

    const settings = {
        dots: true,
        infinite: false,
        speed: 500,
        slidesToShow: subdivision.length > 0 ? 3 : 0,
        slidesToScroll: 1,
        arrows: subdivision.length > 0 ? true : false
      };

    return(<>
        <div className="slider-container previousWork">
            <Slider {...settings}>
                {subdivision.map((r) => {
                    if(r.manufacturingDto.materialInputDto.workPlanDto.wstate == 8 && r.sdstate == 2){
                        return(
                            <div className="previosList">
                                <Link to={`/packaging?sdno=${r.sdno}`}>
                                    <h4>작업계획 {r.manufacturingDto.materialInputDto.workPlanDto.wno}</h4>
                                    <p>거래처 : {r.manufacturingDto.materialInputDto.workPlanDto.client}</p>
                                    <p>등록일자 : {r.manufacturingDto.materialInputDto.workPlanDto.wendtime.split('T')[0].split('-')[0]}년 {r.manufacturingDto.materialInputDto.workPlanDto.wendtime.split('T')[0].split('-')[1]}월 {r.manufacturingDto.materialInputDto.workPlanDto.wendtime.split('T')[0].split('-')[2]}일</p>
                                </Link>
                            </div>
                        )
                    }
                })}
            </Slider>
        </div>
    </>)
}