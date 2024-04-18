import { useState } from "react";
import Chart from "./Chart";
import List from "./List";
import Listmonth from "./Listmonth";



export default function ChartList(props){


    const[period, setPeriod] =useState(1)


    return(
        <>
        <Chart />
        {period == 1? <List setPeriod={setPeriod}/> : period == 2 ? <Listmonth setPeriod={setPeriod}/>:""}
        </>
    )
}