import RmLogWrite from "./RmLogWrite";
import RmWrite from "./RmWrite";

export default function RmWrites(props){
    
    return(<div style={{display:"flex" , justifyContent:"space-between"}}>
        <RmWrite setrerender ={props.setrerender} rerender={props.rerender}/>
        <RmLogWrite setrerender ={props.setrerender} rerender={props.rerender}/>
    </div>)
}