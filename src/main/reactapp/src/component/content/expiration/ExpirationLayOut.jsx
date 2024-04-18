import { useState } from "react";
import ExpirationList from "./ExpirationList";
import LayoutTest from "../layouttest/Layouttest";
import LayoutTest2 from "../layouttest/Layouttest2";

export default function ExpirationLayOut(props){
    return(
        <LayoutTest2 list={<ExpirationList />} />
    )
}