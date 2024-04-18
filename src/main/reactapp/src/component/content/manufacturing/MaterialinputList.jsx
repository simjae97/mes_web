import axios from "axios"
import { useContext, useEffect, useState } from "react"
import { Link } from "react-router-dom";
import "./manufacturingCSS.css"
import { RenderContext } from "./Manufacturing";

import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

export default function MaterialinputList(){

    // 재 랜더링용
    // - provider 컴포넌트의 value 호출
    const { render ,setRender } = useContext(RenderContext);
    
    // 투입테이블
    const [ materialinput , setMaterialinput] = useState([]);

    useEffect(()=>{
        axios.get("/manufacturing/MaterialInput.do")
        .then((response)=>{
            setMaterialinput(response.data);
            console.log(materialinput);
        })
    },[render])

    const settings = {
        dots: true,
        infinite: false,
        speed: 500,
        slidesToShow: materialinput.length > 0 ? 3 : 0,
        slidesToScroll: 1,
        arrows: materialinput.length > 0 ? true : false
      };
    
    return(<>
        <div className="slider-container previousWork">
            <Slider {...settings}>
                {materialinput.map((mip) => {
                    if(mip.workPlanDto.wstate == 4 && mip.mipstate == 2){
                        return(
                            <div className="previosList">
                                <Link to={`/manufacturing/info?mipno=${mip.mipno}`}>
                                    <h4>작업계획 {mip.workPlanDto.wno}</h4>
                                    <p>거래처 : {mip.workPlanDto.client}</p>
                                    <p>등록일자 : {mip.workPlanDto.wendtime.split('T')[0].split('-')[0]}년 {mip.workPlanDto.wendtime.split('T')[0].split('-')[1]}월 {mip.workPlanDto.wendtime.split('T')[0].split('-')[2]}일</p>
                                </Link>
                            </div>
                        )
                    }
                })}
            </Slider>
        </div>
    </>)
}