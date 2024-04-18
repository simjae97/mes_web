import { useState } from "react";
import LayoutTest from "../layouttest/Layouttest";
import RmCount from "./RmCount";
import RmWrites from "./RmWirtes";
import RmWrite from "./RmWrite";
import LayoutTest2 from "../layouttest/Layouttest2";

export default function RmLayOut(props){

    return(
        <LayoutTest2 list={<RmCount/>} />
    )
}