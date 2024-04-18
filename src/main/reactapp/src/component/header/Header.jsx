import { useContext, useEffect } from 'react';
import styles from './header.css';
import { LoginInfoContext } from '../Index';
import axios from 'axios';
import { Link } from 'react-router-dom';

export default function Header(props){
    const clickMenu = (event) => {
        const clickedItem = event.currentTarget;
        const subMenu = clickedItem.querySelector(".subMenu");
    
        // 모든 subMenu를 닫음
        const allSubMenus = document.querySelectorAll('.subMenu');
        allSubMenus.forEach((menu) => {
            if (menu !== subMenu && menu.classList.contains('active')) {
                menu.classList.remove('active');
            }
        });
    
        // 클릭된 subMenu만 토글
        if (subMenu) {
            subMenu.classList.toggle('active');
        }
    };


    // 로그인 정보 저장
    const { logininfo, setLogin } = useContext(LoginInfoContext);
    


    

    const logoutHandler = ()=>{
        axios.get("/member/logout/get.do").then( (r)=>{ if(r.data){
            alert("로그아웃 성공")
            window.location.href = "/"
        } } ).catch( e=>{})
    }




    const checklogin = logininfo ? <button type='button' onClick={logoutHandler} className='logoutBtn'><img src='/static/img/logoutbtn.png' width={'40px'}/></button>:<></>

    return (
        <div id='menuWrap'>
            <div id='logoBox'>
                <Link to="/Progress/view"><img src='/static/img/logo.png' width={'120px'}/></Link>                
            </div>
            <div id='sideNav'>
                <ul>
                    <li onClick={clickMenu}>                        
                        <div className='topMenu'>
                                자재관리
                        </div>                        
                        <div className='subMenu'>
                            <ul>
                                <li>
                                    <Link to="/RM">원자재</Link>
                                </li>
                                <li>
                                    <Link to ="/product">제품리스트</Link>
                                </li>

                                <li>
                                    <Link to ="/expiration">폐기 대기 </Link>
                                </li>
                            </ul>
                        </div>
                    </li>
                    <li onClick={clickMenu}>
                        <div className='topMenu'>
                            생산관리
                        </div>                       
                        <div className='subMenu'>
                            <ul>
                                <li>
                                    <Link to="/survey/survey">계량등록</Link>
                                </li>
                                <li>
                                    <Link to="/material/input">원료투입</Link>
                                </li>
                                <li>
                                    <Link to="/manufacturing/info">벌크제조</Link>
                                </li>
                                <li>
                                    <Link to="/subdivision">소분작업</Link>
                                </li>
                                <li>
                                    <Link to="/packaging">포장</Link>
                                </li>
                            </ul>
                        </div>
                    </li>

                    <li onClick={clickMenu}>
                        <div className='topMenu'>   
                        통계
                        </div> 
                        <div className='subMenu'>
                            <ul>
                                <li>
                                    <Link to="/Progress/view">작업 진행 상황</Link>
                                </li>
                                <li>
                                    <Link to ="/chart">제품통계</Link>
                                </li>
                                <li>
                                    <Link to="/wp/list">전체 보고서 출력</Link>
                                </li>
                            </ul>
                        </div>
                    </li>
                    {logininfo.part == -1 && (
                        <li onClick={clickMenu} >
                            <div className='topMenu'>
                                관리자페이지
                            </div>                        
                        <div className='subMenu'>
                            <ul>
                                <li>
                                <Link to="/member/List">사원조회</Link>
                                </li>
                                <li>
                                    <Link to="/wp/write">생산일정 등록</Link>
                                </li>

                            </ul>
                        </div>
                    </li>
                    )}
                </ul>
            </div>
            {checklogin}
        </div>
    )
}

/*

    <li>
        <a class="collapsible-header">Dropdown<i class="material-icons">arrow_drop_down</i></a>
        <div class="collapsible-body">
            <ul>
            <li><a href="#!">First</a></li>
            <li><a href="#!">Second</a></li>
            <li><a href="#!">Third</a></li>
            <li><a href="#!">Fourth</a></li>
            </ul>
        </div>
    </li>

      $('.button-collapse').sideNav({
      menuWidth: 300, // Default is 300
      edge: 'left', // Choose the horizontal origin
      closeOnClick: false, // Closes side-nav on <a> clicks, useful for Angular/Meteor
      draggable: true // Choose whether you can drag to open on touch screens,
    }
  ); 
*/