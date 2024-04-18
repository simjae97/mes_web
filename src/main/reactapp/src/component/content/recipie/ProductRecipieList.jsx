import axios from "axios";
import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import Productinput from "./ProductInput";

export default function ProductRecipieList(props){

    const [infos, setInfos] = useState([]);
    console.log(props.pno)

    useEffect(
        ()=>{
            axios.get(`/product/recipie/get.do?pno=${props.pno}`).then( (r) => { 
                setInfos(r.data);
            }).catch((error) => {
                console.error("Error:", error);
            });

        },[props.reroad]
    )

    console.log(infos)

    return(
    <>
    <table>
        <thead>
            <tr>
                <th>
                원자재명
                </th>
                <th>
                요구분량
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
                    <td>{rm.rmname}</td>
                    <td>{rm.reamount}</td>
                </tr>
            ))}
        </tbody>
    </table>
</>
);

}
