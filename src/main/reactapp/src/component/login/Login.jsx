import React, { useEffect, useState } from 'react';
import styles from './login.css'; // CSS 모듈을 불러옵니다.
import axios from 'axios'; // axios를 불러옵니다.



export default function Header(props) {
    // 상태 변수(info)를 정의하고 초기값으로 빈 객체를 설정
    const [info, setInfo] = useState({});

    
    // useEffect 사용하여 이미 로그인 했으면 로그인화면 말고 메인화면으로 보내기  // 시현 
    useEffect(() => {
        const fetchData = async () => {
            try {
                // axios를 사용하여 사용자 정보 가져오기
                const response = await axios.get("/member/login/info/get.do");
                console.log(response); 
                if (response.data !== "") {
                    window.location.href = "/Progress/view";
                }
            } catch (error) {
                console.log(error); 
            }
        };
        fetchData(); // fetchData 함수를 호출하여 데이터를 가져옵니다.
    }, []); // [] 처음 렌더링될 때 한 번만 실행
    

    // 사용자가 ID 입력 필드를 변경할 때마다 호출
    const handleMidChange = (e) => {
        setInfo(prevInfo => ({
            ...prevInfo,
            mid: e.target.value
        }));
    }

    // 사용자가 비밀번호 입력 필드를 변경할 때마다 호출
    const handleMpwChange = (e) => {
        setInfo(prevInfo => ({
            ...prevInfo,
            mpw: e.target.value
        }));
    }

    // 로그인 버튼을 클릭할 때 호출
    const doLogin = () => {
        const formData = new FormData();
        formData.append("mpw", info.mpw); // 비밀번호를 FormData에 추가합니다.
        formData.append("mid",info.mid) // ID를 FormData에 추가합니다.
        console.log(formData) 
        // axios를 사용하여 POST 요청을 보내고 응답을 받습니다.
        axios.post("/member/login/post.do",formData)
            .then( r => {
                if(r.data){ // 응답 데이터가 있는 경우
                    alert("로그인성공"); // 성공 메시지를 표시합니다.
                    window.location.href = "/Progress/view"; // 로그인 후 페이지를 이동합니다.
                }else{
                    alert("로그인 실패! 아이디 , 패스워드 확인 바랍니다.") // 실패 메세지 표시
                }
            })
            .catch(e => {
                console.log(e); 
            })
    }

    // 컴포넌트를 렌더링합니다.
    return (  
<div id='loginWrap'>
    <div class="loginHeader">
        <h2>로그인</h2>
    </div>
    <form>
        <div class="inputGroup , InputID">
            <span>ID &nbsp;&nbsp;</span>
            <input type='text' placeholder="아이디를 입력해주세요" onChange={handleMidChange} />
        </div>
        <div class="inputGroup , InputPw">
            <span>PW </span>
            <input type='password' placeholder="비밀번호를 입력해주세요" onChange={handleMpwChange} />
        </div>
    </form>
    <div class="inputButtonCenter">
    <button type='button' onClick={doLogin}>로그인</button>
    </div>
</div>
    );
}

// 로그인 백업
/*

        <div id='loginWrap'>
            <h2>로그인</h2>
            <form>
                <span >ID :</span> <input type='text' onChange={handleMidChange} /> 
                <span >PassWord :</span> <input type='password' onChange={handleMpwChange} /> 
            </form>
            <button type='button' onClick={doLogin}>로그인</button>
        </div>


*/
