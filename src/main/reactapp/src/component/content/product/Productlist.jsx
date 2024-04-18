import axios from "axios";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import ProductWrite from "./ProductWrite";

export default function ProductList(props){
    const [infos, setInfos] = useState([]);
    const [reroad, setReroad] = useState(true);
    useEffect(
        ()=>{
            axios.get("/product/get.do").then( (r) => { 
                setInfos(r.data);
            }).catch((error) => {
                console.error("Error:", error);
            });

        },[reroad]
    )

    console.log(infos)

    return(<>
            <ProductWrite pno={props.pno} reroad={reroad} setReroad={setReroad}/>
        <table>
            <thead>
                <tr>
                    <th>
                    제품번호
                    </th>
                    <th>
                    제품명
                    </th>
                    <th>
                    레시피
                    </th>
                    <th>
                    제품 로그 확인
                    </th>
                </tr>
            </thead>
            <tbody>
                {/* {infos.map( (e)=>(
                    <tr key={e.pno}>
                        <td>{e.pno}</td><td>{e.pname}</td>
                    </tr>
                ))} */}
                {infos.map((product) => (
                    <tr key={product.pno}>
                        <td>{product.pno}</td>
                        <td>{product.pname}</td>
                        <td><Link to={"/product/recipie/get?pno="+product.pno}>이동</Link></td>
                        <td><Link to={"/product/log/list?pno="+product.pno}>이동</Link></td>
                    </tr>
                ))}
            </tbody>
    </table>
    </>)
}