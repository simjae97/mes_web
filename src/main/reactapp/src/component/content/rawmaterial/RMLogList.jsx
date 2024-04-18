import axios from "axios";
import { useEffect, useState } from "react";
import { useSearchParams } from 'react-router-dom';

export default function RmLogList(props){
    const [infos, setInfos] = useState([]);
    //쿼리스트링 받아오기
    let [query, setQuery] = useSearchParams();
    console.log(query.get("rmno"))
    useEffect(
        ()=>{
            axios.get(`/RM/log/get.do?rmno=${query.get("rmno")}`).then( (r) => { 
                setInfos(r.data);
            }).catch((error) => {
                console.error("Error:", error);
            });

        },[]
    )

    console.log(infos)

    return(<table>
            <thead>
                <tr>
                    <th>
                    원자재번호
                    </th>
                    <th>
                    원자재 이름
                    </th>
                    <th>
                    기입수량
                    </th>
                    <th>
                    날짜
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
                    <tr key={rm.rmlno}>
                        <td>{query.get("rmno")}</td>
                        <td>{rm.rmname}</td>
                        <td>{rm.rmlcount}</td>
                        <td>{rm.cdate}</td>
                    </tr>
                ))}
            </tbody>
    </table>)
}