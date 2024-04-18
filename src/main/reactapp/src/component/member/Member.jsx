import { useContext, useEffect } from "react";
import { LoginInfoContext } from "../Index";
import styles from './member.css'; // CSS 모듈을 불러옵니다.
import { useState } from "react";
import axios from 'axios';


export default function Test(props){
    // 1. 컨텍스트 가져오기 (로그인 정보)
    const { logininfo, setLogin } = useContext(LoginInfoContext);

    const [part, setPart] = useState(""); // 유형 상태 초기화

    const handleUserTypeChange = (e) => {
        setPart(e.target.value); // select 요소의 값 업데이트
        console.log(part);
    }

    // // 1. 상태변수
    const [ mid , setMid ] = useState("");
    const [ mpw , setMpassword] = useState("")
    const [ mname , setMname ] = useState("")

    //  mid 수정함수
    const onChangeMid = (e) =>{
        setMid(e.target.value);
    }

    // 패스워드 키보드 입력마다 수정 Mpassword
    const onChangePw = (e) =>{
        setMpassword(e.target.value);
    }

    // 이름 키보드 입력 마다 수정 Mname
    const onChangeMname = (e)=>{
        setMname(e.target.value);
    }

    // 관리자 페이지 회원 유효성 검사 ( url 로 들어왔을시.. )
    function loginCheck(){
        // 회원값이 없거나 null 일경우(새로고침할떄) 로그인 페이지 이동
        if(logininfo === '' || logininfo == null){           
            window.location.href='/';
            // http://localhost:3000/member/test
        }
        // part 가 관리자가 아닐경우 메인페이지 이동
        else if(logininfo.part !== -1){
            window.location.href='/c';
        }
    }
    loginCheck();

    // 회원가입 기능
    // 3. 전송함수
    const onSignup= (e)=>{
        console.log(mid);
        console.log(mpw);
        console.log(mname);

        let info = { mid : mid , mpw : mpw , mname : mname , part : part}
        console.log(info); console.log('인포')
        axios.post("/member/signup/post.do" , info)
        .then(response => {
            console.log(response);
            if(response.data == 1){
                alert('사원 등록 성공');
                setMid('');
                setMname('');
                setMpassword('');
                setPart('');
                window.location.href='/member/List';
            }else if(response.data == -1){
                alert('아이디 중복');
            }else if(response.data == -2){
                alert('사원등록 실패! 관리자 문의');
            }else{
                alert('사원등록 실패! 관리자 문의');
            }
        })
        .catch(error => {console.log(error); })
        
    }

        

        

    

    return(<>
    <div id='memberWrap'>
        <div>
        <h2>사원등록</h2>
        <form >
            <div class="form-group">
                <label for="username">아이디:</label>
                <input type="text" value={mid} name="mid" onChange={onChangeMid} required/>
            </div>
            <div class="form-group">
                <label for="password">비밀번호:</label>
                <input type="password" value={mpw} name="mpw" onChange={onChangePw} required/>
            </div>
            <div class="form-group">
                <label for="fullname">이름:</label>
                <input type="text" value={mname} name="mname"  onChange={onChangeMname} required/>
            </div>
            <div class="form-group">
                <label for="userType">업무부서:</label>
                <select id="userType" name="part" value={part} onChange={handleUserTypeChange} required>
                    <option value="">업무부서를 선택하세요</option>
                    <option value="1">계량팀</option>
                    <option value="2">생산팀</option>
                    <option value="3">포장팀</option>
                    <option value="10">검사팀</option>
                </select>
                
            </div>
            <button type="button" onClick={onSignup}>사원등록</button>
        </form>
        </div>
    </div>
    </>)
}