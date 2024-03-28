import { useState, useEffect } from 'react';
import { TimeSelection } from '../../components/DashboardComponents/TimeSelection/TimeSelection';
import { WaterGraph } from '../../components/DashboardComponents/WaterGraph/WaterGraph';
import { CaloriesGraph } from '../../components/DashboardComponents/CaloriesGraph/CaloriesGraph';
import { WeightGraph } from '../../components/DashboardComponents/WeightGraph/WeightGraph';
import {
  DashboardPageWrapper,
  GraphsWrapper,
  WrapperCenter,
} from './DashboardPage.styled';
import { useDispatch, useSelector } from 'react-redux';
import { getMonthlyStatistics } from '../../redux/operations';
import { selectUserId } from '../../redux/selectors';

export default function DashboardPage() {
  const dispatch = useDispatch();
  const [month, setMonth] = useState(null);
  const [year, setYear] = useState(null);
  // const [dateOfMonths, setDateOfMonths] = useState([]);
  const userId = useSelector(selectUserId);

  useEffect(() => {
    const fetchData = async () => {
      if (month !== null) {
        try {
          console.log(month);
          console.log(year);
          await dispatch(getMonthlyStatistics({ userId, month, year }));
        } catch (error) {
          console.error('Error fetching data:', error);
        }
      }
    };
    fetchData();
  }, [month, userId, dispatch]);

  return (
    <WrapperCenter>
      <DashboardPageWrapper>
        <TimeSelection
          month={month}
          setMonth={setMonth}
          year={year}
          setYear={setYear}
        />
        <GraphsWrapper>
          <CaloriesGraph month={month} year={year} />
          <WaterGraph month={month} year={year} />
        </GraphsWrapper>
        <WeightGraph month={month} year={year} />
      </DashboardPageWrapper>
    </WrapperCenter>
  );
}
