import React from 'react';
import { useState, useEffect } from 'react';
import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend } from 'chart.js';
import axios from 'axios'
import { Line } from 'react-chartjs-2';


ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend);

;

export default function Listmonth(props){
  const [chartData, setChartData] = useState({
        labels:[1,2,3,4,5,6,7,8,9,10,11,12],
        datasets: [
          {
            label: 'Dataset 1',
            borderColor: 'rgb(255, 99, 132)',
            backgroundColor: 'rgba(255, 99, 132, 0.5)',
          },
          {
            label: 'Dataset 2',
            borderColor: 'rgb(53, 162, 235)',
            backgroundColor: 'rgba(53, 162, 235, 0.5)',
          },
        ]
  });

  useEffect(() => {
    axios.get('/productlog/chart/log/month')
      .then(response => {
        const data = response.data;
  
        // 데이터 그룹화
        const groupedData = {};
        data.forEach(item => {
          if (!groupedData[item.pno]) {
            groupedData[item.pno] = {
              label: item.pname,
              data:[],
              borderColor: getRandomColor(), // 랜덤 색상 생성
              backgroundColor: 'rgba(0, 0, 0, 0)', // 배경색은 투명하게
            };
          }
          groupedData[item.pno].data.push({ x: item.cdate, y: item.plcount });
          groupedData[item.pno].data.sort((a, b) => a.x - b.x);
        });
        console.log(groupedData);
        // 데이터셋 배열 생성
        const datasets = Object.values(groupedData);
  
        // 그래프 데이터 설정
        setChartData({
          labels:[1,2,3,4,5,6,7,8,9,10,11,12],
          datasets: datasets,
        });
      })
      .catch(error => {
        console.error('Error fetching data:', error);
      });
  }, []);
  
  // 랜덤 색상 생성 함수
  const getRandomColor = () => {
    const letters = '0123456789ABCDEF';
    let color = '#';
    for (let i = 0; i < 6; i++) {
      color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
  };

  return (
    <div>
      <select onChange={(e)=>{props.setPeriod(e.target.value)}}>
        <option value={1}>주간보기</option>
        <option value={2} selected>월간보기</option>
      </select>
      <h2>월간 상품 입출고 현황</h2>
      <div>
        <Line data={chartData} />
      </div>
    </div>
  );
};
