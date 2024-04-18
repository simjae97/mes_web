import { useEffect, useState } from "react"
import axios from "axios";
import Member from "./Member"
export default function MeberList(props){


    let [memberList,setMemberList] = useState([]);

    // 모든 사원 데이터 가져오기
    useEffect( ()=>{
        axios.get('/member/all/get.do')
        .then((response)=>{
            // console.log(response); console.log('여기1')
            if(response.data != []){
                setMemberList(response.data);
                // console.log(response.data); console.log('여기2')
            }
        })
    } , [])

    console.log(memberList); console.log('여기3')

    const [member, setMember] = useState(false);

    const handleClick = () => {
      // 버튼 클릭 시 showComponent2의 값을 토글합니다.
      setMember(!member);
    };

    // 퇴사 처리 함수
    const completeResignation = (mno , mname) =>{
        
        if (window.confirm(`사원번호 : ${mno} 이름 : ${mname} 퇴사처리를 하시겠습니까?`)) {
            // 사용자가 확인을 눌렀을 때 수행할 작업
            axios.delete(`/member/Account/delete.do?mno=${mno}`)
            .then(() => {
                console.log('삭제 성공!');
                // 삭제 요청이 성공한 후에 memberList를 업데이트하여 재랜더링을 유발
                setMemberList(memberList.filter(member => member.mno !== mno));
            })
            .catch(error => {
                console.error('에러 발생 관리자에게 문의!:', error);
            });
        } else {
            // 사용자가 취소를 눌렀을 때 수행할 작업 없음.
        }
    };


    return(<>
        {/* 버튼 클릭 시 showComponent2 값에 따라 Component2를 렌더링합니다. */}
        {member && <Member/>}
        <div style={{display: member ? 'none' : 'block'}} className="AcontentBox">
            <span style={{marginLeft:600}}><button type="button" onClick={handleClick}>사원등록</button></span>
            <h3>사원 목록</h3> 
            <table>
            {/* <colgroup>
                <col width="10%"/>
                <col width="20%"/>
                <col width="30%"/>
                <col width="15%"/>
                <col width="25%"/>
            </colgroup> */}
                <thead>
                    <tr>
                        <th>
                            사원ID
                        </th>
                        <th>
                            사원 이름
                        </th>
                        <th>
                            파트
                        </th>
                        <th>
                            입사일
                        </th>
                        <th>
                            비고
                        </th>
                    </tr>
                </thead>
                <tbody>
                    {
                        memberList.map((r,index)=>{
                            let cdate = r.cdate?.split('T')[0];
                            return(
                                <>
                                    <tr>
                                        <td>
                                            {r.mid}
                                        </td>
                                        <td>
                                            {r.mname}
                                        </td>
                                        <td>
                                            {r.part == -1 ? '관리자' : r.part == 1 ? '계량팀' : r.part == 2 ? '생산팀' : r.part == 3 ? '포장팀' : r.part == 10 ? '검사팀' : '기타' }
                                        </td>
                                        <td>
                                            {cdate}
                                        </td>
                                        <td>
                                        <button onClick={() => completeResignation(r.mno , r.mname)} type="button">퇴사 처리</button>
                                        </td>
                                    </tr>
                                </>
                            )
                        })
                    }
                </tbody>
            </table>
        </div>
    </>)
}