import {
  CategoryScale,
  Chart as ChartJS,
  Filler,
  Legend,
  LineElement,
  LinearScale,
  PointElement,
  Title,
  Tooltip,
} from 'chart.js';
import { Line } from 'react-chartjs-2';
import { useSelector } from 'react-redux';
import { selectUserStatistics } from '../../../redux/selectors';
import {
  HeaderData,
  Overflow,
  ScrollerWrapper,
  WaterAverageNumber,
  WaterAverageTitle,
  WaterGraphWrapper,
  WaterHeader,
  WaterHeadingWrapper,
  WaterSectionhWrapper,
} from './WaterGraph.styled';

export const WaterGraph = ({ month }) => {
  const dataOfUser = useSelector(selectUserStatistics);

  ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Filler,
    Legend
  );

  const numberOfDaysInTheMonth = (month) => {
    let monthNumberTested;

    if (month !== new Date().getMonth()) {
      monthNumberTested = new Date().getDate();
    } else {
      monthNumberTested = new Date(2023, month, 0).getDate();
    }
    const daysArray = Array.from({ length: monthNumberTested }, (_, index) =>
      (index + 1).toString()
    );
    return daysArray;
  };

  const dataCap = (numberOfDay) => {
    if (Object.keys(dataOfUser).length) {
      const foundItem = dataOfUser.waterPerDay.find(
        (el) => numberOfDay === el.day.toString()
      );
      if (foundItem) {
        return foundItem.ml;
      } else {
        return 0;
      }
    }
    return 0;
  };

  const labels = numberOfDaysInTheMonth(month);

  const arrayOfGoods = labels.map((el) => dataCap(el));

  const maxNumber = Math.max(...arrayOfGoods);

  const arrayOfGraphData = () => {
    return arrayOfGoods;
  };

  const maxOnGraph = () => {
    const defaultMinimum = 3000;
    if (maxNumber < defaultMinimum) {
      return defaultMinimum;
    }
    const roundedNumber = Math.ceil(maxNumber / 1000) * 1000;
    return roundedNumber;
  };

  const options = {
    responsive: true,
    maintainAspectRatio: false,
    scales: {
      y: {
        min: 0,
        max: maxOnGraph(),
        grid: {
          color: '#292928',
        },
        gridLines: {
          display: false,
          color: '#B6B6B6',
        },
        ticks: {
          stepSize: 1000,
          color: '#B6B6B6',
          callback: function (value) {
            if (String(value).length === 1) {
              return value;
            }
            return String(value / 1000) + `L`;
          },
        },
      },
      x: {
        grid: {
          color: '#292928',
        },
        ticks: {
          color: '#B6B6B6',
        },
        scales: {
          x: {
            min: 0,
            max: 100,
          },
        },
      },
    },
    plugins: {
      legend: {
        display: false,
      },
      tooltip: {
        cornerRadius: 8,
        caretSize: 0,
        padding: 10,
        borderColor: 'rgba(227, 255, 168, 0.1)',
        borderWidth: 3,
        backgroundColor: '#0f0f0f',
        titleFont: {
          weight: 'bold',
          size: 32,
          color: 'white',
        },
        displayColors: false,
        yAlign: 'bottom',
        xAlign: 'auto',
        bodyFont: {
          size: 32,
        },
        footerFont: {
          size: 16,
        },
        footerAlign: 'center',
        labelAlign: 'center',
        callbacks: {
          title: () => null,
          label: (context) => context.raw,
          footer: () => 'milliliters',
        },
      },
    },
  };

  const data = {
    labels,
    datasets: [
      {
        label: 'Water',
        fill: false,
        showLine: true,
        borderColor: '#e3ffa8',
        borderWidth: 1,
        tension: 0.4,
        pointRadius: 0,
        pointBorderColor: '#e3ffa8',
        pointHoverRadius: 3,
        pointHitRadius: 12,
        pointBackgroundColor: '#e3ffa8',
        data: arrayOfGraphData(),
      },
    ],
  };

  return (
    <WaterSectionhWrapper>
      <WaterHeadingWrapper>
        <WaterHeader>Water</WaterHeader>
        {dataOfUser.avgWater ? (
          <HeaderData>
            <WaterAverageTitle>Average value:</WaterAverageTitle>
            <WaterAverageNumber>
              {dataOfUser.avgWater.toFixed(0)}ml
            </WaterAverageNumber>
          </HeaderData>
        ) : (
          <HeaderData>
            <WaterAverageTitle>Average value:</WaterAverageTitle>
            <WaterAverageNumber>no added data yet</WaterAverageNumber>
          </HeaderData>
        )}
      </WaterHeadingWrapper>
      <ScrollerWrapper>
        <Overflow>
          <WaterGraphWrapper>
            <Line options={options} data={data}></Line>
          </WaterGraphWrapper>
        </Overflow>
      </ScrollerWrapper>
    </WaterSectionhWrapper>
  );
};
