export const selectIsLoggedIn = (state) => state.auth.isLoggedIn;
export const selectUserId = (state) => state.auth.userId;

export const selectUserStatistics = (state) => state.data.statistics;

export const selectIsLoadData = (state) => state.data.isLoading;
export const selectIsLoadAuth = (state) => state.auth.isLoading;

export const selectUserData = (state) => state.data.user;

export const selectUserMeals = (state) => state.data.user.consumedMealsByDay;

export const selectDailyNutrition = (state) =>
  state.data.user.info.dailyNutrition;

export const selectRecFood = (state) => state.data.recommendedFood;
