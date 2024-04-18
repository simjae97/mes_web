import { useState } from "react";
import LayoutTest from "../layouttest/Layouttest";
import Productinput from "../recipie/ProductInput";
import ProductRecipieList from "../recipie/ProductRecipieList";
import ProductWrite from "./ProductWrite";
import ProductList from "./Productlist";
import { useSearchParams } from "react-router-dom";
import LayoutTest2 from "../layouttest/Layouttest2";


export default function ProductLayOut(props){

    //쿼리스트링 받아오기
    let [query, setQuery] = useSearchParams();

    
    console.log(query.get("pno"))


    return(
        <LayoutTest2 list={<ProductList pno={query.get("pno")} />} />
    )
}