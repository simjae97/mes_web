import React, { useState, useEffect } from 'react';
import Button from '@mui/material/Button';
import Snackbar from '@mui/material/Snackbar';
import Alert from '@mui/material/Alert';

function ProgressAlarm(props) {
  // useState 대신 useEffect를 사용하여 props.message를 상태로 설정
  const [message, setMessage] = useState(props.message);

  // useEffect를 사용하여 props가 변경될 때마다 message 상태 업데이트
  useEffect(() => {
    setMessage(props.message);
    setOpen(true);
  }, [props.message]);

  const [open, setOpen] = useState(true);


  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }

    setOpen(false); // handleClose 시 스낵바를 닫도록 setOpen(false)로 수정
  };

  return (
    <div>
      <Snackbar open={open} autoHideDuration={5000} onClose={handleClose} anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }} >
        <Alert
          onClose={handleClose}
          severity="success"
          variant="filled"
          sx={{ width: '100%'}}
        >
          {message}
        </Alert>
      </Snackbar>
    </div>
  );
}

export default ProgressAlarm;
