import * as React from 'react';
import PropTypes from 'prop-types';
import { styled } from '@mui/material/styles';
import Stack from '@mui/material/Stack';
import Stepper from '@mui/material/Stepper';
import Step from '@mui/material/Step';
import StepLabel from '@mui/material/StepLabel';
import Check from '@mui/icons-material/Check';
import SettingsIcon from '@mui/icons-material/Settings';
import GroupAddIcon from '@mui/icons-material/GroupAdd';
import VideoLabelIcon from '@mui/icons-material/VideoLabel';
import StepConnector, { stepConnectorClasses } from '@mui/material/StepConnector';
import axios from 'axios';
import { useContext, useEffect, useRef, useState } from "react";
import styles from './Progress.css';

const QontoConnector = styled(StepConnector)(({ theme }) => ({
  [`&.${stepConnectorClasses.alternativeLabel}`]: {
    top: 10,
    left: 'calc(-50% + 16px)',
    right: 'calc(50% + 16px)',
  },
  [`&.${stepConnectorClasses.active}`]: {
    [`& .${stepConnectorClasses.line}`]: {
      borderColor: '#784af4',
    },
  },
  [`&.${stepConnectorClasses.completed}`]: {
    [`& .${stepConnectorClasses.line}`]: {
      borderColor: '#784af4',
    },
  },
  [`& .${stepConnectorClasses.line}`]: {
    borderColor: theme.palette.mode === 'dark' ? theme.palette.grey[800] : '#eaeaf0',
    borderTopWidth: 3,
    borderRadius: 1,
  },
}));

const QontoStepIconRoot = styled('div')(({ theme, ownerState }) => ({
  color: theme.palette.mode === 'dark' ? theme.palette.grey[700] : '#eaeaf0',
  display: 'flex',
  height: 22,
  alignItems: 'center',
  ...(ownerState.active && {
    color: '#784af4',
  }),
  '& .QontoStepIcon-completedIcon': {
    color: '#784af4',
    zIndex: 1,
    fontSize: 18,
  },
  '& .QontoStepIcon-circle': {
    width: 10,
    height: 10,
    borderRadius: '50%',
    backgroundColor: 'currentColor',
  },
}));

function QontoStepIcon(props) {
  const { active, completed, className } = props;

  return (
    <QontoStepIconRoot ownerState={{ active }} className={className}>
      {completed ? (
        <Check className="QontoStepIcon-completedIcon" />
      ) : (
        <div className="QontoStepIcon-circle" />
      )}
    </QontoStepIconRoot>
  );
}

QontoStepIcon.propTypes = {
  /**
   * Whether this step is active.
   * @default false
   */
  active: PropTypes.bool,
  className: PropTypes.string,
  /**
   * Mark the step as completed. Is passed to child components.
   * @default false
   */
  completed: PropTypes.bool,
};

const steps = ['계량등록', '계량검사완료', '원료투입' , '투입검사완료' , '벌크제조' , '벌크검사완료', '소분작업','소분검사완료' , '포장완료'];



export default function Progress() {

    // axios 작업 시작
    let [progress,setProgress] = useState([]);

    // 출력
    useEffect(() =>{
        axios.get('/progress/get.do')
        .then((response)=>{
            // console.log(response); console.log('여기!!!!!!!!')
            if(response.data != []){
                setProgress(response.data);
            }
    })
    } , []);


  return (<>
        <div id='progressWrap'>
            <h2>작업 진행 상황</h2>
            {
                progress.map((r,index)=>{
                    console.log(r)
                    let wendtime = r.wendtime?.split('T')[0];
                    return(<>
                    {/* 워크플랜의 wstate 가 9(제품 완성) 면 화면 표시 none  */}
                    <div id='progressDiv' key={r.wstate} style={{ display: r.wstate === 9 ? 'none' : 'block', marginTop: index === 0 ? '50px' : '20px' }}>
                            <div className='progressDataWrap'>
                                <span className='progressData'>작업 번호 : {r.wno}</span>
                                <span className='progressData'>제품 명 : {r.pname}</span>
                                <span className='progressData'>제품 수량 : {r.wcount}</span>
                                <span className='progressData'>제품 마감일 : {wendtime}</span><br/>
                            </div>
                            <div className='progressView'>
                            <Stack sx={{ width: '100%' }} spacing={4}>
                                <Stepper alternativeLabel activeStep={r.wstate} connector={<QontoConnector />}>
                                    {steps.map((label) => (
                                    <Step key={label}>
                                        <StepLabel StepIconComponent={QontoStepIcon}>{label}</StepLabel>
                                    </Step>
                                    ))}
                                </Stepper>
                            </Stack>
                            </div>
                        </div>
                    </>)
                })
            }
        </div>
  </>);
}