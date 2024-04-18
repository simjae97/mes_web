import axios from "axios"
import { useEffect, useState } from "react";

export default function MaterialInputLog(props){
    const [materialconfirmlog , setMaterialconfirmlog] = useState([]);
    useEffect(()=>{
        axios.get('/materialinput/confirm/log.do').then((response)=>{
            console.log(response); 
            setMaterialconfirmlog(response.data);
    
        }).catch(error=>{
            console.log(error);
        })
    },[])
    return(
        <div>
            <table>
                <thead>
                    <tr>
                        <th>
                            asd
                        </th>
                    </tr>
                </thead>
                <tbody className="materialInfo">
                    {
                        materialconfirmlog.map(r=>{
                            if(r.checkmembername == null){
                                return(
                                    <>
                                        <tr>
                                            <td>
                                                {r.mipno}
                                            </td>
                                            <td>
                                                {r.udate}
                                            </td>
                                        </tr>
                                    </>
                                )
                            }
                        })
                    }
                </tbody>
            </table>
        </div>
    )
}