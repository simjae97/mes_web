import { useState } from "react";
import Productinput from "./ProductInput";
import LayoutTest from "../layouttest/Layouttest";
import ProductRecipieList from "./ProductRecipieList";
import { useSearchParams } from "react-router-dom";


export default function ProductRLayOut(props){

    const [reroad, setReroad] = useState(true);
    const [query] = useSearchParams();
    const pno = query.get("pno")
    return(
        <LayoutTest insert={<Productinput pno = {pno} reroad={reroad} setReroad={setReroad}/>} list={<ProductRecipieList pno = {pno} reroad={reroad}/>} />
    )
}