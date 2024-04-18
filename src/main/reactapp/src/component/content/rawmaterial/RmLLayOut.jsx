import LayoutTest from "../layouttest/Layouttest";
import LayoutTest2 from "../layouttest/Layouttest2";
import RmLogList from "./RMLogList";
import RmCount from "./RmCount";
import RmLogWrite from "./RmLogWrite";
import RmWrites from "./RmWirtes";
import RmWrite from "./RmWrite";

export default function RmLLayOut(props){
    return(
        <LayoutTest2 list={<RmLogList/>} />
    )
}