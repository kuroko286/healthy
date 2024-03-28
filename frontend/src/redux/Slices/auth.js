import { createSlice } from '@reduxjs/toolkit';
import {
  forgotPassword,
  getCurrentUser,
  signOut,
  signin,
  signup,
} from '../operations';
import { getCookieByName } from '../../utils/Cookie';

const initialState = {
  userId: getCookieByName('user_id'),
  isLoggedIn: getCookieByName('user_id') ? true : false,
  isLoading: false,
  error: null,
};

const handleFulfilled = (state, action) => {
  state.userId = action.payload;
  state.isLoggedIn = true;
  state.isLoading = false;
  state.error = null;
};

const handlePending = (state) => {
  state.isLoading = true;
};

const handleRejected = (state, action) => {
  state.userId = null;
  state.isLoading = false;
  state.error = action.payload;
};

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    updateUserId: (state, action) => {
      state.userId = action.payload;
    },
    reset: () => {
      return initialState;
    },
  },

  extraReducers: (builder) => {
    builder
      .addCase(signup.fulfilled, handleFulfilled)
      .addCase(signup.pending, handlePending)
      .addCase(signup.rejected, handleRejected)
      .addCase(signin.fulfilled, handleFulfilled)
      .addCase(signin.pending, handlePending)
      .addCase(signin.rejected, handleRejected)
      .addCase(signOut.fulfilled, (state) => {
        state.isLoggedIn = null;
        state.isLoading = false;
        state.error = null;
      })
      .addCase(signOut.pending, handlePending)
      .addCase(signOut.rejected, handleRejected)
      .addCase(forgotPassword.fulfilled, (state) => {
        state.isLoading = false;
        state.error = null;
      })
      .addCase(forgotPassword.pending, handlePending)
      .addCase(forgotPassword.rejected, handleRejected)
      .addCase(getCurrentUser.pending, handlePending)
      .addCase(getCurrentUser.fulfilled, (state) => {
        state.isLoggedIn = true;
        state.isLoading = false;
        state.error = null;
      })
      .addCase(getCurrentUser.rejected, (state, action) => {
        state.isLoggedIn = false;
        state.isLoading = false;
        state.error = action.payload;
      });
  },
});

export const authReducer = authSlice.reducer;

export const { updateUserId, reset } = authSlice.actions;
