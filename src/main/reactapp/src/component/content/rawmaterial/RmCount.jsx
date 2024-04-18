import axios from "axios";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import RmLogList from "./RMLogList";
import RmWrites from "./RmWirtes";

export default function RmCount(props){
    
    const [rerender , setrerender] = useState(false);
    const [infos, setInfos] = useState([]);
    useEffect(
        ()=>{
            axios.get("/RM/log/count/get.do").then( (r) => { 
                setInfos(r.data);
            }).catch((error) => {
                console.error("Error:", error);
            });

        },[rerender]
    )

    console.log(infos)

    return(<>
     <RmWrites rerender ={rerender} setrerender={setrerender} />
    <table>
            <thead>
                <tr>
                    <th>
                    원자재번호
                    </th>
                    <th>
                    원자재명
                    </th>
                    <th>
                    남은 잔고
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
                    <tr key={rm.rmno} >
                        <td>{rm.rmno}</td>
                        <td><Link to={`/RM/log?rmno=${rm.rmno}`}>{rm.rmname}</Link></td>                        
                        <td>{rm.rmsum}</td>
                    </tr>
                ))}
            </tbody>
    </table>
    </>)
}