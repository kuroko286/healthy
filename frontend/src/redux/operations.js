import { createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';
import toast from 'react-hot-toast';
import { removeCookie } from '../utils/Cookie';

axios.defaults.baseURL = 'http://localhost:8080/v1';
axios.defaults.withCredentials = true;

// Authorization

export const signup = createAsyncThunk(
  'auth/signup',
  async (credentials, thunkAPI) => {
    try {
      const res = await axios.post('/auth/register', credentials);
      toast.success('Successfully sign up!');
      return res.data;
    } catch (error) {
      toast.error(error.message);
      return thunkAPI.rejectWithValue(error.message);
    }
  }
);

export const signin = createAsyncThunk(
  'auth/signin',
  async (credentials, thunkAPI) => {
    try {
      const res = await axios.post('/auth/login', credentials);
      toast.success('Successfully sign in!');
      return res.data;
    } catch (error) {
      toast.error(error.message);
      return thunkAPI.rejectWithValue(error.message);
    }
  }
);

export const signOut = createAsyncThunk('auth/signout', async (_, thunkAPI) => {
  try {
    const url = '/auth/signout';
    removeCookie('user_id');
    await axios.post(url);
    toast.success('Successfully sign out!');
  } catch (error) {
    toast.error(error.message);
    return thunkAPI.rejectWithValue(error.message);
  }
});

export const forgotPassword = createAsyncThunk(
  'auth/forgot-password',
  async (credentials, thunkAPI) => {
    try {
      const res = await axios.post('/auth/forgot-password', credentials);
      toast.success('A letter has been sent to your email!');
      return res.data;
    } catch (error) {
      toast.error(error.message);
      return thunkAPI.rejectWithValue(error.message);
    }
  }
);

// Recommended food

export const refreshRecommendedFood = createAsyncThunk(
  'auth/get-recommended-food',
  async (userId, thunkAPI) => {
    try {
      const res = await axios.get(`/users/${userId}/foods/recommended`);
      return res.data;
    } catch (error) {
      toast.error(error.message);
      return thunkAPI.rejectWithValue(error.message);
    }
  }
);

// User

export const getMonthlyStatistics = createAsyncThunk(
  'auth/getMonthlyStatistics',
  async ({ userId, month, year }, thunkAPI) => {
    try {
      console.log(month);
      const res = await axios.get(`/users/${userId}/statistics`, {
        params: { month, year },
      });
      return res.data;
    } catch (error) {
      toast.error(error.message);
      return thunkAPI.rejectWithValue(error.message);
    }
  }
);

export const getCurrentUser = createAsyncThunk(
  'auth/getCurrentUser',
  async (userId, thunkAPI) => {
    try {
      const res = await axios.get(`/users/${userId}`);
      return res.data;
    } catch (error) {
      toast.error(error.message);
      return thunkAPI.rejectWithValue(error.message);
    }
  }
);

export const updateUserInformation = createAsyncThunk(
  'auth/updateUserInformation',
  async ({ userId, updatedValues: userData }, thunkAPI) => {
    try {
      const res = await axios.put(`/users/${userId}`, userData);
      toast.success('User information updated');
      return res.data;
    } catch (error) {
      toast.error(error.message);
      return thunkAPI.rejectWithValue(error.message);
    }
  }
);

export const updateUserGoal = createAsyncThunk(
  'auth/updateUserGoal',
  async ({ userId, values: goalData }, thunkAPI) => {
    try {
      const res = await axios.put(`/users/${userId}/goal`, goalData);
      toast.success('Goal updated');
      return res.data;
    } catch (error) {
      toast.error(error.message);
      return thunkAPI.rejectWithValue(error.message);
    }
  }
);

export const addUserWeight = createAsyncThunk(
  'auth/addUserWeight',
  async ({ userId, values: weightData }, thunkAPI) => {
    try {
      const res = await axios.post(`/users/${userId}/weights`, weightData);
      toast.success('Weight updated');
      return res.data;
    } catch (error) {
      toast.error(error.message);
      return thunkAPI.rejectWithValue(error.message);
    }
  }
);

export const addFoodIntake = createAsyncThunk(
  'auth/addFoodIntake',
  async ({ userId, foodIntakeData }, thunkAPI) => {
    try {
      const res = await axios.post(`/users/${userId}/foods`, foodIntakeData);
      toast.success('Meal added');
      return res.data;
    } catch (error) {
      toast.error(error.message);
      return thunkAPI.rejectWithValue(error.message);
    }
  }
);

export const deleteFoodIntake = createAsyncThunk(
  'auth/deleteFoodIntake',
  async ({ userId, mealType }, thunkAPI) => {
    try {
      const res = await axios.delete(`/users/${userId}/meals?type=${mealType}`);
      toast.success('Meal deleted');
      return res.data;
    } catch (error) {
      toast.error(error.message);
      return thunkAPI.rejectWithValue(error.message);
    }
  }
);

export const addWaterIntake = createAsyncThunk(
  'auth/addWaterIntake',
  async ({ userId, waterIntakeData }, thunkAPI) => {
    try {
      const res = await axios.post(`users/${userId}/waters`, waterIntakeData);
      toast.success('Water added');
      return res.data;
    } catch (error) {
      toast.error(error.message);
      return thunkAPI.rejectWithValue(error.message);
    }
  }
);

export const deleteWaterIntake = createAsyncThunk(
  'auth/deleteWaterIntake',
  async (userId, thunkAPI) => {
    try {
      const res = await axios.delete(`/users/${userId}/waters`);
      toast.success('Water deleted');
      return res.data;
    } catch (error) {
      toast.error(error.message);
      return thunkAPI.rejectWithValue(error.message);
    }
  }
);

export const updateFoodIntake = createAsyncThunk(
  'auth/updateFoodIntake',
  async ({ userId, foodInfo }, thunkAPI) => {
    try {
      const res = await axios.put(
        `/users/${userId}/foods/${foodInfo.foodId}`,
        foodInfo.foodIntakeData
      );
      toast.success('Meal updated');
      return res.data;
    } catch (error) {
      toast.error(error.message);
      return thunkAPI.rejectWithValue(error.message);
    }
  }
);

export const addUserAvatar = createAsyncThunk(
  'auth/addUserAvatar',
  async ({ userId, avatar }, thunkAPI) => {
    try {
      const res = await axios.post(`/users/${userId}/avatar`, avatar, {
        headers: {
          'Content-Type': 'multipart/form-data',
          'x-rapidapi-host': 'file-upload8.p.rapidapi.com',
          'x-rapidapi-key': 'your-rapidapi-key-here',
        },
      });
      toast.success('Avatar updated');
      return res.data;
    } catch (error) {
      toast.error(error.message);
      return thunkAPI.rejectWithValue(error.message);
    }
  }
);
