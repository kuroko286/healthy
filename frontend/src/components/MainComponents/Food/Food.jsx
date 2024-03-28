import {
  Container,
  Heading,
  FullFrame,
  SmallCharts,
  Chart,
  Percentage,
  ChartInfo,
} from './Food.styled';
import BigChart from './BigChart/BigChart';
import DoughnutElement from './Doughnut/Doughnut.jsx';
import { useSelector } from 'react-redux';
import { selectUserMeals, selectUserData } from '../../../redux/selectors.js';

export default function Food() {
  const dailyMeals = useSelector(selectUserMeals);
  const userStats = useSelector(selectUserData);

  return (
    <Container>
      <Heading>Food</Heading>
      <FullFrame>
        <BigChart
          calories={userStats?.info?.dailyCalories}
          currentCalories={dailyMeals?.totalConsumedCaloriesPerDay || 0}
        />
        <SmallCharts>
          <Chart>
            <DoughnutElement
              heightpx={48}
              widthpx={48}
              color="#FFC4F7"
              percentage={
                Math.round(
                  (dailyMeals?.totalConsumedCarbohydratesPerDay * 100) /
                    userStats?.info?.dailyNutrition?.carbohydrates
                ) || 0
              }
            />
            <Percentage>
              {Math.round(
                (dailyMeals?.totalConsumedCarbohydratesPerDay * 100) /
                  userStats?.info?.dailyNutrition?.carbohydrates
              ) || 0}
              %
            </Percentage>
            <ChartInfo>
              <h3>Carbohidrates</h3>
              <div>
                <p>
                  Goal:{' '}
                  <span>{userStats?.info?.dailyNutrition?.carbohydrates}</span>
                </p>
                <p>
                  left:{' '}
                  <span>
                    {userStats?.info?.dailyNutrition?.carbohydrates -
                      dailyMeals?.totalConsumedCarbohydratesPerDay >=
                    0
                      ? userStats?.info?.dailyNutrition?.carbohydrates -
                        dailyMeals?.totalConsumedCarbohydratesPerDay
                      : 0}
                  </span>
                </p>
              </div>
            </ChartInfo>
          </Chart>
          <Chart>
            <DoughnutElement
              heightpx={48}
              widthpx={48}
              color="#FFF3B7"
              percentage={
                Math.round(
                  (dailyMeals?.totalConsumedProteinPerDay * 100) /
                    userStats?.info?.dailyNutrition?.protein
                ) || 0
              }
            />
            <Percentage>
              {Math.round(
                (dailyMeals?.totalConsumedProteinPerDay * 100) /
                  userStats?.info?.dailyNutrition?.protein
              ) || 0}
              %
            </Percentage>
            <ChartInfo>
              <h3>Protein</h3>
              <div>
                <p>
                  Goal: <span>{userStats?.info?.dailyNutrition?.protein}</span>
                </p>
                <p>
                  left:{' '}
                  <span>
                    {userStats?.info?.dailyNutrition?.protein -
                      dailyMeals?.totalConsumedProteinPerDay >=
                    0
                      ? userStats?.info?.dailyNutrition?.protein -
                        dailyMeals?.totalConsumedProteinPerDay
                      : 0}
                  </span>
                </p>
              </div>
            </ChartInfo>
          </Chart>
          <Chart>
            <DoughnutElement
              heightpx={48}
              widthpx={48}
              color="#B6B6B6"
              percentage={
                Math.round(
                  (dailyMeals?.totalConsumedFatPerDay * 100) /
                    userStats?.info?.dailyNutrition?.fat
                ) || 0
              }
            />
            <Percentage>
              {Math.round(
                (dailyMeals?.totalConsumedFatPerDay * 100) /
                  userStats?.info?.dailyNutrition?.fat
              ) || 0}
              %
            </Percentage>
            <ChartInfo>
              <h3>Fat</h3>
              <div>
                <p>
                  Goal: <span>{userStats?.info?.dailyNutrition?.fat}</span>
                </p>
                <p>
                  left:{' '}
                  <span>
                    {userStats?.info?.dailyNutrition?.fat -
                      dailyMeals?.totalConsumedFatPerDay >=
                    0
                      ? userStats?.info?.dailyNutrition?.fat -
                        dailyMeals?.totalConsumedFatPerDay
                      : 0}
                  </span>
                </p>
              </div>
            </ChartInfo>
          </Chart>
        </SmallCharts>
      </FullFrame>
    </Container>
  );
}
