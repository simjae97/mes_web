
import axios from "axios";
import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";

export default function ProductLogList(props){

    const [infos, setInfos] = useState([]);

    let [query, setQuery] = useSearchParams();

    
    const pno = query.get("pno")

    useEffect(
        ()=>{
            axios.get(`/productlog/list/get.do?pno=${pno}`).then( (r) => { 
                setInfos(r.data);
            }).catch((error) => {
                console.error("Error:", error);
            });

        },[props.reroad]
    )

    console.log(infos)

    const sum = infos.reduce((total, currentValue) => total + currentValue.plcount, 0);
    console.log(sum);
    return infos[0]?(
    <>
    <h2>{infos[0].productDto.pname}</h2>
    <table>
        <thead>
            <tr>
                <th>
                입출고량
                </th>
                <th>
                일자
                </th>
            </tr>
        </thead>
        <tbody>
            {/* {infos.map( (e)=>(
                <tr key={e.pno}>
                    <td>{e.pno}</td><td>{e.pname}</td>
                </tr>
            ))} */}
            {infos.map((rm) => (
                <tr >
                    <td>{rm.plcount}</td>
                    <td>{rm.cdate}</td>
                </tr>
            ))}
        </tbody>
        <div>
            남은 수량 : {sum}
        </div>
    </table>
</>
) : "";

}