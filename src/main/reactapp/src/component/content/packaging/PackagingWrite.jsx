import axios from "axios";
import { useContext, useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import { LoginInfoContext } from "../../Index";
import { RenderContext } from "./Packaging";

export default function PackagingWrite(props){    
    // 재 랜더링용
    // - provider 컴포넌트의 value 호출
    const { render ,setRender } = useContext(RenderContext);

    // 1. 컨텍스트 가져오기 (로그인 정보)
    const { logininfo, setLogin } = useContext(LoginInfoContext);
    //console.log(logininfo); 

    const [ packagingInfo , setPackagingInfo] = useState("");

    const [ render2 , setRender2 ] = useState(false);
    
    const [ 박스개수 , set박스개수 ] = useState("");

    // 쿼리스트링 값 가져오기 sdno
    let [query, setQuery] = useSearchParams();
    
    const packaging = ()=>{
        axios.get("/packaging/subdivision/info/get.do", { params :{ sdno :query.get("sdno") }} )
        .then((r)=>{
            console.log(r); 

            setRender2(true);
            setPackagingInfo(r.data);            
        }).catch( (e) => {console.log(e)})
    }   

    const 박스개수처리 = (event) => {
        console.log(event.target.value);
        console.log(packagingInfo.manufacturingDto.materialInputDto.productDto.packagingcount);
        console.log(parseInt((박스개수)/(packagingInfo.manufacturingDto.materialInputDto.productDto.packagingcount)));
        set박스개수(event.target.value);
    }
    
    
    
    const packaginPost = () => {
        let packagingForm = document.querySelector(".packagingForm");
        let packagingFormData = new FormData(packagingForm);
        
        console.log(packagingForm.pgcount.value);
        console.log(packagingFormData.pgcount);

        axios.post("/packaging/post.do?sdno="+query.get('sdno') , packagingFormData)
        .then((r)=>{
            // 반환 0 = 실패 / 1이상 = 성공 / -1 = 로그인정보가 없음  / -2 권한이 없는작업
            console.log(r.data);
            if(r.data>0){
                let data = {
                    wno : packagingInfo.manufacturingDto.materialInputDto.workPlanDto.wno,
                    wstate : 9
                }
                axios.put('/wp/changestate/put.do',data)
                .then(r1=>{
                    console.log(r1);
                    // console.log(packagingForm.pgcount.value);
                    // console.log(packagingInfo.manufacturingDto.materialInputDto.productDto.pno)

                    let packagingFormData2 = new FormData();
                    packagingFormData2.append('pno', packagingInfo.manufacturingDto.materialInputDto.productDto.pno);
                    packagingFormData2.append('pgcount', packagingForm.pgcount.value);

                    axios.post("/productlog/post.do" , packagingFormData2 )
                    .then( (r2) => {
                        console.log(r2);
                    }).catch((e) => {console.log(e)})
                    alert("안내) 등록 성공하였습니다.");
                    setRender(render+1);
                })
                
            }else if(r.data==-1){
                alert("안내) 로그인정보가 없습니다.")
            }else if(r.data==-2){
                alert("안내) 등록 권한이 없습니다.")
            }
        }).catch((e) => {console.log(e)})   
        
    }


    useEffect( () => { packaging(); },[query,render])

    if(logininfo != null && render2 ){
    return(<>
        <div className="AinputBox2">
        <form className="packagingForm">
        <h3>
            <span>포장제품 : {packagingInfo.manufacturingDto.materialInputDto.workPlanDto.pname}</span>
            <span>포장수량 : {packagingInfo.manufacturingDto.materialInputDto.workPlanDto.wcount} EA</span>
            <span>포장기한 : {packagingInfo.manufacturingDto.materialInputDto.workPlanDto.wendtime.split('T')[0]} 까지</span>
        </h3>
        <div>
            <div>
            소분완료량 : {packagingInfo.successcount}
            <input type="text" name="successcount" onChange={박스개수처리}/>
            </div>
            <div>
            박스개수 : <input type="text" name="pgcount" value={parseInt((박스개수)/(packagingInfo.manufacturingDto.materialInputDto.productDto.packagingcount))}/>
            </div>
        </div>
            <div>
            <button className="btn-3d green" type="button" onClick={packaginPost}>등록</button>
            </div>
            </form>
        </div>

    </>);
    }else{
       return(<></>) 
    }
}
