import TodayWrap from '../../components/MainComponents/TodayWrap/TodayWrap';
import Diary from '../../components/MainComponents/Diary/Diary';
import RecommendedFood from '../../components/MainComponents/RecommendedFood/RecommendedFood';
import { StyledWrap } from './Main.styles';
import { useDispatch, useSelector } from 'react-redux';
import { useEffect } from 'react';
import { getCurrentUser } from '../../redux/operations';
import { selectUserId } from '../../redux/selectors';

export default function MainPage() {
  const dispatch = useDispatch();
  const userId = useSelector(selectUserId);
  console.log(userId);
  useEffect(() => {
    dispatch(getCurrentUser(userId));
  }, [dispatch, userId]);

  return (
    <>
      <TodayWrap />
      <StyledWrap>
        <Diary />
        <RecommendedFood />
      </StyledWrap>
    </>
  );
}
