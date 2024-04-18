import React, { useEffect, useRef, useState } from 'react';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { Doughnut } from 'react-chartjs-2';
import axios from 'axios';

ChartJS.register(ArcElement, Tooltip, Legend);




export default function Chart() {
  const [ps, setHello] = useState([]);

  const [chartData, setChartData] = useState({
    labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
    datasets: [
      {
        label: '남은분량',
        data: [12, 19, 3, 5, 2, 3],
        backgroundColor: [
          'rgba(255, 99, 132, 0.2)',
          'rgba(54, 162, 235, 0.2)',
          'rgba(255, 206, 86, 0.2)',
          'rgba(75, 192, 192, 0.2)',
          'rgba(153, 102, 255, 0.2)',
          'rgba(255, 159, 64, 0.2)',
        ],
        borderColor: [
          'rgba(255, 99, 132, 1)',
          'rgba(54, 162, 235, 1)',
          'rgba(255, 206, 86, 1)',
          'rgba(75, 192, 192, 1)',
          'rgba(153, 102, 255, 1)',
          'rgba(255, 159, 64, 1)',
        ],
        borderWidth: 1,
      },
    ],
  });

  useEffect(() => {
    axios.get("/productlog/chart/remaining")
      .then((response) => {
        console.log(response.data)
        const data = response.data;
        const labels = data.map(item => item.label);
        const values = data.map(item => item.value);

        setChartData(prevChartData => ({
          ...prevChartData,
          labels: labels,
          datasets: [
            {
              ...prevChartData.datasets[0],
              data: values,
            },
          ],
        }));
      })
      .catch(function (error) {
        console.log(error);
      });
  }, []);
  return <div style={{width:"400px"}}>
      <h2>현재 보관중인 제품 목록</h2>
        <Doughnut data={chartData} />
      </div>;
}
