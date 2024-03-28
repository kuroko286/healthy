import { useEffect, useState } from 'react';
import icons from './../../../assets/icons.svg';
import {
  GoBackToMainPageSvg,
  GoBackToMainPageWrapper,
  Header,
  HeaderWrapper,
  MonthPickerSvgInactive,
  MonthPickerSvgActive,
  MonthPickerWrapper,
  MonthList,
  MonthListItem,
  ChoosenMonth,
} from './TimeSelection.styled';
export const TimeSelection = ({ month, setMonth,year,setYear }) => {
  const [isArrowClicked, setClickOfArrow] = useState(null);

  const onClickOfArrow = () => {
    setClickOfArrow(!isArrowClicked);
  };

  const onChooseOfMonth = (date) => {
    setMonth(date.getMonth() + 1);
    setYear(date.getFullYear());
  };

  const GoBackToMainPage = () => (
    <GoBackToMainPageWrapper to="/main">
      <GoBackToMainPageSvg>
        <use href={`${icons}#icon-arrow-right`}></use>
      </GoBackToMainPageSvg>
    </GoBackToMainPageWrapper>
  );

  const MonthPicker = () => (
    <MonthPickerWrapper onClick={onClickOfArrow}>
      <Header>Months</Header>
      {!isArrowClicked ? (
        <MonthPickerSvgInactive>
          <use href={`${icons}#icon-arrow-down`}></use>
        </MonthPickerSvgInactive>
      ) : (
        <>
          <MonthPickerSvgActive>
            <use href={`${icons}#icon-arrow-down`}></use>
          </MonthPickerSvgActive>
          <ListOfMonthes onChooseOfMonth={onChooseOfMonth} />
        </>
      )}
    </MonthPickerWrapper>
  );

  const currentAndPreviousMonth = () => {
    const currentDate = new Date();
    const previousMonth = currentDate.getMonth() - 1;
    const currentMonth = currentDate.getMonth();
    const previousMonthName = new Date(
      currentDate.getFullYear(),
      previousMonth,
      1
    ).toLocaleString('en-US', { month: 'long' });
    const currentMonthName = new Date(
      currentDate.getFullYear(),
      currentMonth,
      1
    ).toLocaleString('en-US', { month: 'long' });
    return [previousMonthName, currentMonthName];
  };

  const initMonthAndYear = () => {
    const now = new Date();
    setMonth(now.getMonth() + 1);
    setYear(now.getFullYear());
  };

  useEffect(() => {
    if (!month || !year) {
      initMonthAndYear();
    }
  }, [month, year]);

  const ListOfMonthes = ({ onChooseOfMonth }) => (
    <MonthList>
      {currentAndPreviousMonth().map((month) => (
        <MonthListItem
          key={month}
          onClick={() =>
            onChooseOfMonth(new Date(`${month} 1, ${year}`))
          }
        >
          {month}
        </MonthListItem>
      ))}
    </MonthList>
  );

  return (
    <HeaderWrapper>
      <GoBackToMainPage />
      <MonthPicker />
      {month && (
        <ChoosenMonth>
          {new Date(year, month - 1, 1).toLocaleString('en-US', {
            month: 'long',
          })}
        </ChoosenMonth>
      )}
    </HeaderWrapper>
  );
};
